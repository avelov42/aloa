package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;
import java.io.IOException;

import com.avelov.Center.Files.AutomatonInfo;

/**
 * Created by mateusz on 28.07.16.
 */
public class SetDescriptionFunction implements AutomatonLoaderFunction {
    @Override
    public void run(AutomatonLoader.Line line, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
        if(line.tokens.size() != 1)
            throw new AutomatonLoaderFunctionException(
                    "1 parameter required", "Description");

        ab.setDescription(line.tokens.get(0));
    }

    @Override
    public String getName() {
        return "description";
    }
}
