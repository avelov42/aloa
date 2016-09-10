package com.avelov.Center.Scripts;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 15.07.16.
 */
public class ScriptArithmeticExpression {
    static final String cellReferenceRegex = "\\{\\s*(-?\\d+)\\s*,\\s*(-?\\d+)\\s*\\}\\s*\\[\\s*(\\d+)\\s*\\]";
    static final Pattern cellReferencePattern = Pattern.compile(cellReferenceRegex);
    private ExpressionPart expr = null;

    public ScriptArithmeticExpression(String expression, VariablesMap variablesMap) throws ScriptArithmeticExpressionException {
        Stack<Token> tokens = new Stack<>();
        Stack<Operator> opsStack = new Stack<>();
        //split
        //cases: variable name, {\d,\d}[\d(i|f)], value int or float, operator, parentheses

        for (int i = 0; i < expression.length(); i++) {
            try {
                final char c = expression.charAt(i);
                if (c == ' ')
                    continue;
                if (Character.isLetter(c) || c == '_') //name
                    i = NamePartParser(expression, i, variablesMap, tokens);
                else if (c == '{')
                    i = CellReferencePartParser(expression, i, tokens);
                else if (Character.isDigit(c) || c == '.')
                    i = ConstantPartParser(expression, i, tokens);
                else
                    i = OperatorPartParser(expression, i, tokens, opsStack);
            }
            catch (ScriptArithmeticExpressionException e)
            {
                throw new ScriptArithmeticExpressionException(e.getMessage() + " in token at position " + Integer.toString(i));
            }
        }

        while (!opsStack.empty())
            tokens.add(opsStack.pop().getToken(tokens.pop(), tokens.pop()));

        this.expr = tokens.pop().GetExpressionPart();
    }

    private static int NamePartParser(final String expression,
                                      int index,
                                      VariablesMap variablesMap,
                                      Stack<Token> valStack) throws ScriptArithmeticExpressionException {
        StringBuilder part = new StringBuilder();
        int i = index;
        boolean firstLetter = true;
        for (; i < expression.length(); i++, firstLetter = false) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c))
                break;

            if (Character.isLetter(c) || c == '_' || (!firstLetter && Character.isDigit(c)))
                part.append(c);
            else
                break;
        }

        final String name = part.toString();

        Integer variable = variablesMap.GetVariable(name);
        if (variable == null) {
            //try board variables
            switch (name) {
                case "MINX":
                    valStack.push(new MinXToken());
                    break;
                case "MINY":
                    valStack.push(new MinYToken());
                    break;
                case "MAXX":
                    valStack.push(new MaxXToken());
                    break;
                case "MAXY":
                    valStack.push(new MaxYToken());
                case "GENERATION":
                    valStack.push(new GenerationToken());
                    break;
                case "X":
                    valStack.push(new XToken());
                    break;
                case "Y":
                    valStack.push(new YToken());
                    break;
                case "this":
                    part = new StringBuilder("this");
                    for (; i < expression.length(); i++) {
                        part.append(expression.charAt(i));
                        if (expression.charAt(i) == ']')
                            break;
                    }
                    String regex = "\\w*\\[\\w*(\\d+)\\w*]";
                    Pattern pattern = Pattern.compile(regex);
                    String s = part.toString();
                    Matcher m = pattern.matcher(s);
                    if (!m.matches()) {
                        throw new ScriptArithmeticExpressionException("Incorrect variable name");
                    }
                    valStack.push(new ThisCellToken(Integer.parseInt(m.group(1))));
                    break;
                default:
                    throw new ScriptArithmeticExpressionException("Incorrect variable name");
            }
        } else
            valStack.push(new VarialbeToken(variable));

        return i;
    }

    private static int CellReferencePartParser(final String expression,
                                               int index,
                                               Stack<Token> valStack) throws ScriptArithmeticExpressionException {
        StringBuilder sb = new StringBuilder();
        int i = index;
        for (; i < expression.length(); i++) {
            sb.append(expression.charAt(i));
            if (expression.charAt(i) == ']')
                break;
        }

        final String s = sb.toString();
        Matcher m = cellReferencePattern.matcher(s);

        if (!m.matches())
        {
            throw new ScriptArithmeticExpressionException("Incorrect cell reference");
        }
        int x = Integer.parseInt(m.group(1));
        int y = Integer.parseInt(m.group(2));
        int ind = Integer.parseInt(m.group(3));

        valStack.push(new com.avelov.Center.Scripts.CellReferenceToken(x, y, ind));
        return i + 1;
    }

    private static int ConstantPartParser(String expression, int index, Stack<Token> valStack) throws ScriptArithmeticExpressionException {
        StringBuilder sb = new StringBuilder();
        int i = index;
        for (; i < expression.length(); i++) {
            if (!Character.isDigit(expression.charAt(i)) && expression.charAt(i) != '.')
                break;
            sb.append(expression.charAt(i));
        }

        final String floatValueRegex = "(?:\\+|-)?(?:(?:\\d+(\\.\\d+)?)|(?:\\.\\d+))";
        final Pattern floatValuePattern = Pattern.compile(floatValueRegex);
        final String s = sb.toString();
        Matcher m = floatValuePattern.matcher(s);
        if (m.matches()) {
            valStack.push(new com.avelov.Center.Scripts.ConstantToken(Float.parseFloat(s)));
            return i;
        }
        throw new ScriptArithmeticExpressionException("Incorrect value");
    }

    private static int OperatorPartParser(String expression, int index,
                                          Stack<Token> valStack,
                                          Stack<Operator> opsStack) throws ScriptArithmeticExpressionException {
        int i = index;
        final String opChars = "+-*/%|&<>=!";
        final String oneCharOp = "+-*/%";
        final String oneOrTwoCharsOp = "<>";
        final String twoCharsOp = "&|!=";
        char c = expression.charAt(i);
        Operator thisOp;

        if (opChars.indexOf(c) == -1)
            throw new ScriptArithmeticExpressionException("Incorrect operator");
        if (c == '(') {
            opsStack.push(new LeftParenthesisOp());
            return i + 1;
        } else if (c == ')') {
            try {
                while (!opsStack.empty() && !opsStack.peek().isLeftParenthesis())
                    valStack.push(opsStack.pop().getToken(valStack.pop(), valStack.pop()));
            }
            catch (EmptyStackException e){
                throw new ScriptArithmeticExpressionException("Malformed expression, parentheses don't match.");
            }
            return i + 1;
        } else if (oneCharOp.indexOf(c) != -1) {
            switch (c) {
                case '+':
                    thisOp = new AddOp();
                    break;
                case '-':
                    thisOp = new com.avelov.Center.Scripts.SubOp();
                    break;
                case '*':
                    thisOp = new MulOp();
                    break;
                case '/':
                    thisOp = new DivOp();
                    break;
                default: //modulo
                    thisOp = new ModOp();
                    break;
            }
            i++;
        } else if (oneOrTwoCharsOp.indexOf(c) != -1) {
            if (i + 1 >= expression.length()) {
                throw new ScriptArithmeticExpressionException("Incorrect operator");
            }
            if (expression.charAt(i + 1) == '=') //ok
            {
                if (c == '<')
                    thisOp = new LeqOp();
                else
                    thisOp = new com.avelov.Center.Scripts.GeqOp();
                i += 2;
            } else {
                if (c == '<')
                    thisOp = new LesOp();
                else
                    thisOp = new GreOp();
                i++;
            }
        } else if (twoCharsOp.indexOf(c) != -1) {
            if (i + 1 >= expression.length()) {
                throw new ScriptArithmeticExpressionException("Incorrect operator");
            }
            if (expression.charAt(i) == '&' && expression.charAt(i + 1) == '&')
                thisOp = new com.avelov.Center.Scripts.AndOp();
            else if (expression.charAt(i) == '!' && expression.charAt(i + 1) == '=')
                thisOp = new com.avelov.Center.Scripts.NeqOp();
            else if (expression.charAt(i) == '|' && expression.charAt(i + 1) == '|')
                thisOp = new OrOp();
            else if (expression.charAt(i) == '=' && expression.charAt(i + 1) == '=')
                thisOp = new EqOp();
            else
                throw new ScriptArithmeticExpressionException("Incorrect operator");
            i += 2;
        } else
            throw new ScriptArithmeticExpressionException("Incorrect operator");

        while (!opsStack.empty() && opsStack.peek().getPrecedence() <= thisOp.getPrecedence())
            valStack.push(opsStack.pop().getToken(valStack.pop(), valStack.pop()));

        opsStack.push(thisOp);

        return i;
    }

    public float ValueFloat(Cell thisCell, Variables variables, Board board) {
        return expr.GetValue(thisCell.x, thisCell.y, variables, board);
    }
}
