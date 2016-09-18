package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.BrushState;
import com.avelov.Center.Files.Layer;

/**
 * Created by mateusz on 23.04.16.
 */
public class AddBrushStateLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(AutomatonLoader.Line line, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
        if(line.tokens.size() != 3)
            throw new AutomatonLoaderFunctionException("3 parameters required", "addState");

        Layer l = ab.getLayerByName(line.tokens.get(0));
        if(l == null)
            throw new AutomatonLoaderFunctionException(
                    "Synatx error: first parameter should be layer name",
                    "addState");
        float value;
        try {
            value = Float.parseFloat(line.tokens.get(2));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException(
                    "Synatx error: third parameter should be a number",
                    "addState");
        }

        l.addBrushState(new BrushState(value, line.tokens.get(1)));
    }

    @Override
    public String getName() {
        return "addstate";
    }
}
