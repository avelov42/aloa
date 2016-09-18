package com.avelov.Center.AutomatonLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avelov.Center.Files.AutomatonInfo;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by mateusz on 21.04.16.
 * Static class used to load automata from saved files
 */
public class AutomatonLoader {
    //private static HashMap<String, AutomatonLoaderFunction> functions;
    private static ArrayList<AutomatonLoaderFunction> functionsList;

    static {
//        functions = new HashMap<>();
//        functions.put("addbrushstate", new AddBrushStateLoaderFunction());
//        functions.put("automaton", new SetAutomatonLoaderFunction());
//        functions.put("boundary", new SetBoundaryLoaderFunction());
//        functions.put("cellsize", new SetCellSizeLoaderFunction());
//        functions.put("coloring", new SetColoringLoaderFunction());
//        functions.put("description", new SetDescriptionFunction());
//        functions.put("image", new SetImageFunction());
//        functions.put("maxvalue", new SetMaxValueLoaderFunction());
//        functions.put("minvalue", new SetMinValueLoaderFunction());
//        functions.put("rules", new SetRulesLoaderFunction());
//        functions.put("boardsize", new SetSizeLoaderFunction());
//        functions.put("topology", new SetTopologyLoaderFunction());
//        functions.put("values", new SetValuesLoaderFunction());


        functionsList = new ArrayList<>();
        //functionsList.add(new SetAutomatonLoaderFunction());
        functionsList.add(new SetSizeLoaderFunction());
        functionsList.add(new AddLayerLoaderFunction());
        functionsList.add(new AddBrushStateLoaderFunction());
        functionsList.add(new SetTopologyLoaderFunction());
        //functionsList.add(new SetDescriptionFunction());
        //functionsList.add(new SetValuesLoaderFunction());
    }


    public static void loadFromFileHandle(FileHandle fileHandle, AutomatonInfo outAutomaton) throws AutomatonLoaderException {
        Map<String, List<Line>> file = parseFile(fileHandle);
        for(AutomatonLoaderFunction alf : functionsList) {
            List<Line> lines = file.get(alf.getName());
            for(Line line : lines)
                try {
                    alf.run(line, outAutomaton);
                } catch (AutomatonLoaderFunctionException e) {

                }
        }
    }
//        int lineNumber = 0;
//        try (BufferedReader r = fileHandle.reader(1000)) {
//            String line;
//            String regex = "\\s*([^\\s:]*)\\s*:\\s*(.*?)\\s*";
//            Pattern pattern = Pattern.compile(regex);
//
//            //OMIT EMPTY LINES
//            do {
//                line = r.readLine();
//                lineNumber++;
//            } while (line != null && line.trim().equals(""));
//            if (line == null)
//                throw new AutomatonLoaderException("File empty", 0);
//
//            //GET ATTRIBUTES
//            do {
//                if (!(line.equals(""))) {
//                    //line = line.toLowerCase(); don't do that, files can contain upper case letters
//                    Matcher m = pattern.matcher(line);
//                    if (!m.matches())
//                        throw new AutomatonLoaderException("Syntax error", lineNumber);
//                    AutomatonLoaderFunction alf = functions.get(m.group(1).toLowerCase());
//                    if (alf == null)
//                        throw new AutomatonLoaderException("No such function", lineNumber);
//                    alf.run(m.group(2), r, outAutomaton);
//                    lineNumber++;
//                }
//            } while ((line = r.readLine()) != null);
//        } catch (IOException e) {
//            throw new AutomatonLoaderException("Error while reading file " + fileHandle.name());
//        } catch (AutomatonLoaderFunctionException e) {
//            throw new AutomatonLoaderException(e.getMessage(), lineNumber);
//        }
//        if (!outAutomaton.isComplete())
//            throw new AutomatonLoaderException("Automaton description incomplete.");
//    }

    public static class Line {
        int lineNumber;
        List<String> tokens;

        public Line(int lineNumber, String line)
        {
            this.lineNumber = lineNumber;
            tokens = extractTokens(line);
        }
    }

    public static List<String> extractTokens(String s)
    {
        ArrayList<String> ret = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        s = s.trim();
        boolean isSpecialChar = false;
        boolean isNewWord = true;
        boolean isQuoted = false;
        for(char c : s.toCharArray())
        {
            if(c == '\\' && !isSpecialChar)
            {
                isSpecialChar = true;
            }
            else if (isSpecialChar)
            {
                if(c == '\\')
                    sb.append(c);
                else if (c == '"')
                    sb.append(c);
                else {
                    sb.append('\\');
                    sb.append(c);
                }
            }
            else {
                if(c == '"')
                {
                    if(isNewWord)
                    {
                        isQuoted = true;
                        isNewWord = false;
                    }
                    else
                    {
                        isQuoted = !isQuoted;
                        isNewWord = false;
                        ret.add(sb.toString());
                        sb = new StringBuilder();
                    }
                }
                else if (isNewWord) {
                    isNewWord = false;
                    sb.append(c);
                }
                else if (Character.isWhitespace(c))
                {
                    if(!isQuoted) {
                        isNewWord = true;
                        if (sb.length() > 0) {
                            ret.add(sb.toString());
                            sb = new StringBuilder();
                        }
                    }
                    else sb.append(c);
                }
                else sb.append(c);
            }
        }
        return ret;
    }

    public static Map<String, List<Line>> parseFile(FileHandle fileHandle)
    {
        Map<String, List<Line>> ret = new TreeMap<>();
        int lineNumber = 1;
        try (BufferedReader r = fileHandle.reader(1000)) {
            String line;
            while ((line = r.readLine()) != null) {
                int comment = line.indexOf('#');
                if(comment != -1)
                    line = line.substring(0, comment);
                line = line.trim();
                if(line.isEmpty())
                    continue;
                String[] split = line.split(":", 2);
                if(split.length != 2)
                    continue; //TODO throw sth

                split[0] = split[0].toLowerCase();

                Line l = new Line(lineNumber, split[1]);
                if(ret.containsKey(split[0]))
                    ret.get(split[0]).add(l);
                else {
                    ArrayList<Line> lines = new ArrayList<>();
                    lines.add(l);
                    ret.put(split[0], lines);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public static String extract(FileHandle fileHandle, String key) {
        try (BufferedReader r = fileHandle.reader(1000)) {
            String line;
            String regex = "\\s*" + key + "\\s*:\\s*\"(.*?)\"";
            Pattern pattern = Pattern.compile(regex);
            while ((line = r.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                if (m.matches()) {
                    return m.group(1).trim();
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

}
