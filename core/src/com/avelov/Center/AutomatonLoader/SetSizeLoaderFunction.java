package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonInfo;

/**
 * Created by mateusz on 21.04.16.
 */
public class SetSizeLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(AutomatonLoader.Line parameter, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
        if (parameter.tokens.size() != 1)
            throw new AutomatonLoaderFunctionException("One parameter required", "size");
        try {
            ab.setBoardSize(Integer.parseInt(parameter.tokens.get(0)));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException("Parameter is not an integer", "size");
        }
    }

    @Override
    public String getName() {
        return "size";
    }
}
