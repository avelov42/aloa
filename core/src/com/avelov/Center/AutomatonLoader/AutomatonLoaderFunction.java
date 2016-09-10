package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonBlueprint;

/**
 * Created by mateusz on 21.04.16.
 */
public interface AutomatonLoaderFunction {
    void run(String parameter, BufferedReader br, AutomatonBlueprint ab)
            throws AutomatonLoaderFunctionException;
}
