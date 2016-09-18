package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonInfo;

/**
 * Created by mateusz on 21.04.16.
 */
public interface AutomatonLoaderFunction {
    void run(AutomatonLoader.Line line, AutomatonInfo ab)
            throws AutomatonLoaderFunctionException;

    String getName();
}
