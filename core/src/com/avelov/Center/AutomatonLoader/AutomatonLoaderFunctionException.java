package com.avelov.Center.AutomatonLoader;

/**
 * Created by mateusz on 21.04.16.
 */
public class AutomatonLoaderFunctionException extends Exception {
    public AutomatonLoaderFunctionException(String error, String functionName) {
        super("While parsing " + functionName + ": " + error);
    }
}