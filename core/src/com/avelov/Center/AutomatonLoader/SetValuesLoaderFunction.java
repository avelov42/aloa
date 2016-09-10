package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;
import java.io.IOException;

import com.avelov.Center.Files.AutomatonBlueprint;
//import pl.mimuw.backend.Cell.FloatCell;

/**
 * Created by mateusz on 21.04.16.
 */
public class SetValuesLoaderFunction implements AutomatonLoaderFunction {

    @Override
    public void run(String parameter, BufferedReader br, AutomatonBlueprint ab) throws AutomatonLoaderFunctionException {
        String line;
        //String regex = "\\s*(\\d+)\\s+(\\d+)\\s*\\[\\s*(\\d+)\\s*\\]\\s+(.+?)\\s*";

        //Pattern pattern = Pattern.compile(regex);
        //Matcher m;
        int valuesCount = 0;
        try {
            while (!((line = br.readLine()) == null || line.equals("") || line.toLowerCase().equals("valuesend"))) {
                valuesCount++;

                line = line.trim();
                String[] params = line.split("\\s+");
                if(params.length < 3)
                    throw new AutomatonLoaderFunctionException("Value line malformed, not enough parameters", "values");
                int x, y;
                try {
                    x = Integer.parseInt(params[0]);
                    y = Integer.parseInt(params[1]);
                }
                catch (NumberFormatException e)
                {
                    throw new AutomatonLoaderFunctionException("Coordinates incorrect in line " + Integer.toString(valuesCount), "values");
                }

                float[] values = new float[params.length - 2];
                for (int i = 0; i < params.length - 2; i++) {
                    try {
                        values[i] = Float.parseFloat(params[i + 2]);
                    }
                    catch (NumberFormatException e)
                    {
                        throw new AutomatonLoaderFunctionException("Integer " + Integer.toString(i) + " in line " + Integer.toString(valuesCount) + " is not an float", "values");
                    }

                }
                ab.setValue(x, y, values);
            }
        } catch (IOException e) {
            throw new AutomatonLoaderFunctionException(
                    "Cannot read " + valuesCount + "th value", "Values");
        }
    }
}
