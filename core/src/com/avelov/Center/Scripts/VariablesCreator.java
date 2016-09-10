package com.avelov.Center.Scripts;

import com.avelov.Pair;

import java.util.ArrayList;


/**
 * Created by mateusz on 15.07.16.
 */
public class VariablesCreator
{
    private ArrayList<Float> default_floats = new ArrayList<>();
    private ArrayList<Float> global_floats = new ArrayList<>();

    private ArrayList<Pair<String, Integer>> locals = new ArrayList<>();
    private ArrayList<Pair<String, Integer>> globals = new ArrayList<>();

    public void AddLocal(String name, float default_value)
    {
        locals.add(new Pair<>(name, default_floats.size()));
        default_floats.add(default_value);
    }

    public void AddGlobal(String name, float default_value)
    {
        globals.add(new Pair<>(name, global_floats.size()));
        global_floats.add(default_value);
    }

    public Variables CreateVariables()
    {
        return new Variables(default_floats, global_floats);
    }

    public VariablesMap CreateVariablesMap()
    {
        return new VariablesMap(locals, globals, default_floats.size());
    }
}
