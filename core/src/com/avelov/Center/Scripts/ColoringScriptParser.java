package com.avelov.Center.Scripts;

import com.avelov.Frontend.CellDrawers.Tinters.ContinuousTinter;
import com.avelov.Frontend.CellDrawers.Tinters.DiscreteTinter;
import com.avelov.Frontend.CellDrawers.Tinters.Tinter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by mateusz on 09.06.16.
 */
public class ColoringScriptParser {
    static public Tinter parse(FileHandle fileHandle) throws ScriptException {
        String line;
        String regex = "\\s*type:\\s+([a-z]+)\\s*";
        Pattern pattern = Pattern.compile(regex);
        int line_number = 0;
        Tinter ret = null;
        try (BufferedReader br = fileHandle.reader(1000)) {
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                line_number++;
                if (line.equals(""))
                    continue;
                Matcher m = pattern.matcher(line);

                if (!m.matches())
                    throw new ScriptException("First line should be in format: \"type: {discrete|continuous}\"");

                switch (m.group(1)) {
                    case "discrete":
                        ret = parseDiscreteColoring(br, line_number);
                        break;
                    case "continuous":
                        ret = parseContinuousColoring(br, line_number);
                        break;
                    default:
                        throw new ScriptException("First line doesn't contain coloring type:" +
                                " discrete or continuous");
                }
                break;
            }
            if (ret == null) //file was empty
            {
                throw new ScriptException("File is empty: " + fileHandle.name());
            }
        } catch (IOException e) {
            throw new ScriptException("Cannot read coloring from file " + fileHandle.name());
        }
        return ret;
    }

    private static Tinter parseContinuousColoring(BufferedReader br, int line_number)
            throws IOException, ScriptException {
        String regex = "(#[0-9a-fA-F]{6})\\s+([+-]?(?:\\d*\\.)?\\d+)";
        Pattern p = Pattern.compile(regex);
        ArrayList<Color> colors = new ArrayList<>();
        ArrayList<Float> values = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                line_number++;
                if (line.equals(""))
                    continue;
                Matcher m = p.matcher(line);
                if (!m.matches())
                    throw new ScriptException("Line is not in format <color> <value>", line_number);

                colors.add(Color.valueOf(m.group(1)));
                values.add(Float.parseFloat(m.group(2)));
            }
        } catch (NumberFormatException e) {
            throw new ScriptException("Incorrect hex number or floating value", line_number);
        }
        float[] fvalues = new float[values.size()];
        int i = 0;
        for (Float f : values)
            fvalues[i++] = f != null ? f : Float.NaN;

        Interpolation[] interpolations = new Interpolation[values.size() - 1];
        for (i = 0; i < interpolations.length; i++)
            interpolations[i] = Interpolation.linear;
        return new ContinuousTinter(colors.toArray(new Color[colors.size()]), fvalues, interpolations);
    }

    static private Tinter parseDiscreteColoring(BufferedReader br, int line_number)
            throws ScriptException, IOException {
        ArrayList<Color> colors = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                line_number++;
                if (line.equals(""))
                    continue;

                colors.add(Color.valueOf(line));
            }
        } catch (NumberFormatException e) {
            throw new ScriptException("Incorrect hex number", line_number);
        }
        return new DiscreteTinter(colors.toArray(new Color[colors.size()]));
    }
}
