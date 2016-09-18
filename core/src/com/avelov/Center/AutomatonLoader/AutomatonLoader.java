package com.avelov.Center.AutomatonLoader;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avelov.Center.Files.AutomatonInfo;

/**
 * Created by mateusz on 21.04.16.
 * Static class used to load automata from saved files
 */
public class AutomatonLoader {
    private static HashMap<String, AutomatonLoaderFunction> functions;

    static {
        functions = new HashMap<>();
        functions.put("addbrushstate",  new AddBrushStateLoaderFunction());
        functions.put("automaton",      new SetAutomatonLoaderFunction());
        functions.put("boundary",       new SetBoundaryLoaderFunction());
        functions.put("cellsize",       new SetCellSizeLoaderFunction());
        functions.put("coloring",       new SetColoringLoaderFunction());
        functions.put("description",    new SetDescriptionFunction());
        functions.put("image",          new SetImageFunction());
        functions.put("maxvalue",       new SetMaxValueLoaderFunction());
        functions.put("minvalue",       new SetMinValueLoaderFunction());
        functions.put("rules",          new SetRulesLoaderFunction());
        functions.put("boardsize",      new SetSizeLoaderFunction());
        functions.put("topology",       new SetTopologyLoaderFunction());
        functions.put("values",         new SetValuesLoaderFunction());
    }

    public static void loadFromFileHandle(FileHandle fileHandle, AutomatonInfo outAutomaton) throws AutomatonLoaderException {
        int lineNumber = 0;
        try (BufferedReader r = fileHandle.reader(1000)) {
            String line;
            String regex = "\\s*([^\\s:]*)\\s*:\\s*(.*?)\\s*";
            Pattern pattern = Pattern.compile(regex);

            //OMIT EMPTY LINES
            do {
                line = r.readLine();
                lineNumber++;
            } while (line != null && line.trim().equals(""));
            if (line == null)
                throw new AutomatonLoaderException("File empty", 0);

            //GET ATTRIBUTES
            do {
                if (!(line.equals(""))) {
                    //line = line.toLowerCase(); don't do that, files can contain upper case letters
                    Matcher m = pattern.matcher(line);
                    if (!m.matches())
                        throw new AutomatonLoaderException("Syntax error", lineNumber);
                    AutomatonLoaderFunction alf = functions.get(m.group(1).toLowerCase());
                    if (alf == null)
                        throw new AutomatonLoaderException("No such function", lineNumber);
                    alf.run(m.group(2), r, outAutomaton);
                    lineNumber++;
                }
            } while ((line = r.readLine()) != null);
        } catch (IOException e) {
            throw new AutomatonLoaderException("Error while reading file " + fileHandle.name());
        } catch (AutomatonLoaderFunctionException e) {
            throw new AutomatonLoaderException(e.getMessage(), lineNumber);
        }
        if (!outAutomaton.isComplete())
            throw new AutomatonLoaderException("Automaton description incomplete.");
    }
}
