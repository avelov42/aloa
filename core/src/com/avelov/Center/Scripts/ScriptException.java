package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 17.04.16.
 */
public class ScriptException extends Exception {
    public ScriptException(String reason) {
        super(reason);
    }

    public ScriptException(String reason, int line) {
        super("Line " + line + ": " + reason);
    }
}
