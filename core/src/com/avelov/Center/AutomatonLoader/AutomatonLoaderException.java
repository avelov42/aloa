package com.avelov.Center.AutomatonLoader;

/**
 * Created by mateusz on 21.04.16.
 */
public class AutomatonLoaderException extends Exception {
    public AutomatonLoaderException(String error, int line) {
        super("Error while loading automaton:\n" + error + "\nin line: " + line);
    }

    public AutomatonLoaderException(String error) {
        super("Error while loading automaton:\n" + error);
    }
}
