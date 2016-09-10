package com.avelov.Center.Scripts;

import com.avelov.Pair;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mateusz on 15.07.16.
 */
public class ScriptParser {
    static final String nameRegex = "([a-zA-Z_]\\w*)";
    static final String setOpRegex = "(=|\\+=|-=|\\*=|/=|%=)";
    static final String maybeWhitespaceRegex = "\\s*";
    static final String whitespaceRegex = "\\s+";
    static final String arithmeticExprRegex = "(.*)";
    static final String ifRegex = "if" +
            whitespaceRegex +
            arithmeticExprRegex +
            ":";
    static final String defaultValueRegex = "(-?(?:\\d*)?\\.?\\d+)";
    static final String variableDeclarationRegex = "(global\\s+)?" +
            maybeWhitespaceRegex +
            nameRegex +
            "(?:" + whitespaceRegex +
            defaultValueRegex + ")?";
    static final String thisRegex = "this\\s*\\[(\\d+)\\]";
    static final String setExprRegex = "((?:[a-zA-Z_]\\w*)|(?:this\\s*\\[\\s*\\d+\\s*\\])|(?:\\{\\s*-?\\d+\\s*,\\s*-?\\d\\s*\\}\\s*\\[\\s*\\d+\\s*\\]))" +
            maybeWhitespaceRegex +
            setOpRegex +
            maybeWhitespaceRegex +
            arithmeticExprRegex;
    static final Pattern thisPattern = Pattern.compile(thisRegex);
    static final Pattern ifPattern = Pattern.compile(ifRegex);
    static final Pattern setExprPattern = Pattern.compile(setExprRegex);
    static final Pattern variableDeclarationPattern = Pattern.compile(variableDeclarationRegex);
    static final String cellReferenceRegex = "\\{\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\}\\s*\\[\\s*(\\d+)\\s*\\]";
    static final Pattern cellReferencePattern = Pattern.compile(cellReferenceRegex);
    private static final int indentWidth = 4;

    ScriptBlock ParseBlock(ArrayList<String> lines,
                           ArrayList<Integer> indents,
                           int firstLine, int lastLine,
                           VariablesMap variablesMap) throws ScriptParserException {
        ScriptBlock scriptBlock = new ScriptBlock();
        int currentLine = firstLine;
        int blockIndent = indents.get(firstLine);

        while (currentLine <= lastLine) {
            int currentIndent = indents.get(currentLine);
            if (currentIndent < blockIndent)
                break;

            String line = lines.get(currentLine);
            if (line.isEmpty()) {
                currentLine++;
                continue;
            }
            //Match current line to correct type
            Matcher if_m = ifPattern.matcher(line);
            Matcher set_m = setExprPattern.matcher(line);
            if (line.equals("return")) {
                scriptBlock.AddCommand(new ScriptReturn());
            } else if (if_m.matches()) {
                //find next with the same indent
                int if_block_line = currentLine + 1;
                while (if_block_line <= lastLine && indents.get(if_block_line) > currentIndent)
                    if_block_line++;
                int if_block_first_line = currentLine + 1;
                int if_block_last_line = if_block_line - 1;

                if (if_block_line > lastLine ||
                        indents.get(if_block_line) < currentIndent ||
                        !lines.get(if_block_line).equals("else:")) //no else
                {
                    scriptBlock.AddCommand(IfCommandParser(if_m.group(1), lines, indents,
                            if_block_first_line,
                            if_block_last_line,
                            variablesMap));
                    currentLine = if_block_last_line;
                } else if (lines.get(if_block_line).equals("else:")) //here indent is always ok
                {
                    //match else block
                    int else_block_line = if_block_last_line + 2;
                    while (else_block_line <= lastLine && indents.get(else_block_line) > currentIndent)
                        else_block_line++;
                    int else_block_first_line = if_block_last_line + 2;
                    int else_block_last_line = else_block_line - 1;

                    scriptBlock.AddCommand(
                            IfElseCommandParser(if_m.group(1), lines, indents,
                                    if_block_first_line, if_block_last_line,
                                    else_block_first_line, else_block_last_line,
                                    variablesMap));
                    currentLine = else_block_last_line;
                }
            } else if (set_m.matches())
                scriptBlock.AddCommand(SetCommandParser(set_m.group(1), set_m.group(2),
                        set_m.group(3), variablesMap, currentLine));
            else {
                System.err.println("ScriptParser, ParseBlock: Incorrect command");
                return null;
            }
            currentLine++;
        }
        return scriptBlock;
    }

    private String RemoveComments(String line) {
        int index = line.indexOf('#');
        if (index != -1)
            return line.substring(0, index);
        return line;
    }

    Pair<VariablesCreator, Integer> ParseDeclareBlock(ArrayList<String> lines,
                                                      ArrayList<Integer> indents,
                                                      int firstLine,
                                                      int lastLine) throws ScriptParserException {
        VariablesCreator vm = new VariablesCreator();
        int declareIndent = indents.get(firstLine);
        if (!lines.get(firstLine).equals("declare:"))
            return new Pair<>(vm, firstLine);

        int currentLine = firstLine + 1;
        int first_line_indent = indents.get(firstLine);

        while (indents.get(currentLine) > first_line_indent && currentLine <= lastLine) {
            if (indents.get(currentLine) != declareIndent + 1) {
                throw new ScriptParserException("Incorrect indent in line " + GetRealLineNumber(currentLine));
            }

            Matcher m = variableDeclarationPattern.matcher(lines.get(currentLine));
            if (m.matches()) {
                float deff = 0.0f;
                if (m.group(3) != null)
                    try {
                        deff = Float.parseFloat(m.group(3));
                    } catch (NumberFormatException e) {
                        throw new ScriptParserException("Incorrect number in line " + GetRealLineNumber(currentLine));
                    }
                if(Variables.IsNameReserved(m.group(2)))
                {
                    throw new ScriptParserException("Used reserved keyword for variable name in line " + GetRealLineNumber(currentLine));
                }
                if (m.group(1) != null) { //global
                    vm.AddGlobal(m.group(2), deff);
                } else {
                    vm.AddLocal(m.group(2), deff);
                }
            } else {
                throw new ScriptParserException("Syntax error in line " + GetRealLineNumber(currentLine));
            }

            currentLine++;
        }
        return new Pair<>(vm, currentLine);
    }

    private Map<Integer, Integer> realLineNumberMap = new TreeMap<>();

    private int GetRealLineNumber(int nonEmptyLine)
    {
        Integer realLine = realLineNumberMap.get(nonEmptyLine);
        return realLine == null ? nonEmptyLine : realLine;
    }

    public Script Parse(FileHandle fileHandle) throws IOException, ScriptParserException {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Integer> indents = new ArrayList<>();
        String line;
        int realLine = 0;
        int nonEmptyLine = 0;
        try (BufferedReader br = fileHandle.reader(1000)) {
            while ((line = br.readLine()) != null) {
                realLineNumberMap.put(nonEmptyLine, realLine);
                realLine++;
                if (RemoveComments(line.trim()).isEmpty())
                    continue;
                nonEmptyLine++;
                indents.add(GetIndent(line, realLine));
                lines.add(line.trim());
            }
        }
        if (lines.isEmpty())
            return null; //throw exception
        Pair<VariablesCreator, Integer> v = ParseDeclareBlock(lines, indents, 0, lines.size() - 1);

        ScriptCommand ret = ParseBlock(lines, indents, v.second, lines.size() - 1,
                v.first.CreateVariablesMap());

        return new Script(v.first.CreateVariables(), ret);
    }

    int GetIndent(String s, int line) throws ScriptParserException {
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) != ' ') {
                if (i % indentWidth == 0)
                    return i / indentWidth;
                throw new ScriptParserException("Incorrect indent in line " + line);
            }
        throw new ScriptParserException("Indent error in line " + line);
    }

    private ScriptCommand IfCommandParser(final String predicate,
                                          final ArrayList<String> lines,
                                          final ArrayList<Integer> indents,
                                          int first_line, int last_line,
                                          VariablesMap variablesMap) throws ScriptParserException {
        try {
            return new ScriptIfCommand(new ScriptArithmeticExpression(predicate, variablesMap),
                    ParseBlock(lines, indents, first_line, last_line, variablesMap));
        } catch (ScriptArithmeticExpressionException e) {
            throw new ScriptParserException(e.getMessage() + " in line " + GetRealLineNumber(first_line));
        }
    }

    private ScriptCommand IfElseCommandParser(String predicate, ArrayList<String> lines,
                                              ArrayList<Integer> indents,
                                              int if_block_first_line, int if_block_last_line,
                                              int else_block_first_line, int else_block_last_line,
                                              VariablesMap variablesMap) throws ScriptParserException {
        try {
            return new ScriptIfElseCommand(new ScriptArithmeticExpression(predicate, variablesMap),
                    ParseBlock(lines, indents,
                            if_block_first_line, if_block_last_line,
                            variablesMap),
                    ParseBlock(lines, indents,
                            else_block_first_line, else_block_last_line,
                            variablesMap));
        } catch (ScriptArithmeticExpressionException e) {
            throw new ScriptParserException(e.getMessage() + " in line " + GetRealLineNumber(if_block_first_line));
        }
    }

    private ScriptCommand SetCommandParser(String name,
                                           String set_op,
                                           String arithmetic_expr,
                                           VariablesMap variablesMap,
                                           int line) throws ScriptParserException {
        ScriptSetOperator sso = ScriptSetOperator.GetOp(set_op);
        if(sso == null)
        {
            throw new ScriptParserException("Incorrect operator in line " + GetRealLineNumber(line));
        }
        Matcher this_m = thisPattern.matcher(name);
        Matcher cell_m = cellReferencePattern.matcher(name);
        if (this_m.matches()) {
            try {
                return new ScriptSetThisCellCommand(
                        Integer.parseInt(this_m.group(1)),
                        sso,
                        new ScriptArithmeticExpression(arithmetic_expr, variablesMap));
            } catch (ScriptArithmeticExpressionException e) {
                throw new ScriptParserException(e.getMessage() + " in line " + GetRealLineNumber(line));
            }
        } else if (cell_m.matches()) {
            try {
                return new ScriptSetReferenceCellCommand(
                        Integer.parseInt(cell_m.group(3)),
                        sso,
                        Integer.parseInt(cell_m.group(1)),
                        Integer.parseInt(cell_m.group(2)),
                        new ScriptArithmeticExpression(arithmetic_expr, variablesMap));
            } catch (ScriptArithmeticExpressionException e) {
                throw new ScriptParserException(e.getMessage() + " in line " + GetRealLineNumber(line));
            }
        }
        Integer v = variablesMap.GetVariable(name);
        if(v == null)
            throw new ScriptParserException("Incorrect variable name in set command in line " + GetRealLineNumber(line));
        try {
            return new ScriptSetVariableCommand(
                    v,
                    sso,
                    new ScriptArithmeticExpression(arithmetic_expr, variablesMap));
        } catch (ScriptArithmeticExpressionException e) {
            throw new ScriptParserException(e.getMessage() + " in line " + GetRealLineNumber(line));
        }
    }
}
