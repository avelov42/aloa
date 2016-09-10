package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonBlueprint;

/**
 * Created by mateusz on 23.04.16.
 */
public class SetMaxValueLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(String parameter, BufferedReader br, AutomatonBlueprint ab)
            throws AutomatonLoaderFunctionException {
        try {
            ab.setMaxValue(Integer.parseInt(parameter));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException("Parameter is not an integer", "MaxValue");
        }
    }
}
