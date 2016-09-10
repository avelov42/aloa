package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avelov.Center.Files.AutomatonBlueprint;
import com.avelov.Center.BrushState;
//import pl.mimuw.backend.Cell.DiscreteCell;
//import pl.mimuw.backend.Cell.FloatCell;

/**
 * Created by mateusz on 23.04.16.
 */
//value 2
public class AddBrushStateLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(String parameter, BufferedReader br, AutomatonBlueprint ab)
            throws AutomatonLoaderFunctionException {
        String regex = "\\s*(.*?)\\s+\"(.*?)\"\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(parameter);
        if (!m.matches())
            throw new AutomatonLoaderFunctionException("Syntax error", "AddBrushState");
        float[] value;
        try {
            float v = Float.parseFloat(m.group(1));
            value = new float[] {v};
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException(
                    "Synatx error: first parameter should be a number",
                    "AddBrushState");
        }
        ab.addBrushState(new BrushState(value, m.group(2)));
    }
}
