package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonInfo;

/**
 * Created by mateusz on 23.04.16.
 */
public class SetMinValueLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(String parameter, BufferedReader br, AutomatonInfo ab)
            throws AutomatonLoaderFunctionException {
        try {
            ab.setMinValue(Integer.parseInt(parameter));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException("Parameter is not an integer", "MinValue");
        }
    }
}
