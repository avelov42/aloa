package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonInfo;

/**
 * Created by mateusz on 21.04.16.
 */
public interface AutomatonLoaderFunction {
    void run(String parameter, BufferedReader br, AutomatonInfo ab)
            throws AutomatonLoaderFunctionException;
}
