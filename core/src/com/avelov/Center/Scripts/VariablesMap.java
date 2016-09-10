package com.avelov.Center.Scripts;

import com.avelov.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by mateusz on 15.07.16.
 */
public class VariablesMap {
    private  Map<String, Integer> variables = new TreeMap<>();

    public VariablesMap(ArrayList<Pair<String, Integer>> locals,
                        ArrayList<Pair<String, Integer>> globals,
                        int offset)
    {
        for(Pair<String, Integer> p : locals)
            variables.put(p.first, p.second);

        for(Pair<String, Integer> p : globals)
            variables.put(p.first, p.second + offset);
    }

    public Integer GetVariable(String name)
    {
        return variables.get(name);
    }
}
