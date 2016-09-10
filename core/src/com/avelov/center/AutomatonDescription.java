package com.avelov.center;
/**
 * Created by mateusz on 13.03.16.
 * Contains information about automaton saved
 * on local storage. Used by AutomataFactory.
 */

public class AutomatonDescription
{
    private String name;
    private String filePath;

    public String getName() {
        return name;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public AutomatonDescription(String name, String filePath) {
        this.filePath = filePath;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}