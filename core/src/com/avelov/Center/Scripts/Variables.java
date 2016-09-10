package com.avelov.Center.Scripts;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by mateusz on 15.07.16.
 */
public class Variables {
    private float[] floats;

    private float[] default_floats;

    public Variables(final ArrayList<Float> def_floats, final ArrayList<Float> global_floats)
    {
        int floats_size = def_floats.size() + global_floats.size();
        floats = new float[floats_size];
        default_floats = new float[def_floats.size()];
        for(int i = 0; i < def_floats.size(); i++)
        {
            default_floats[i] = def_floats.get(i);
        }
        for(int i = def_floats.size(), j = 0; i < def_floats.size() + global_floats.size(); i++, j++)
        {
            floats[i] = global_floats.get(j);
        }
    }

    public void ResetLocals()
    {
        System.arraycopy(default_floats, 0, floats, 0, default_floats.length);
    }

    public void SetFloat(int index, float value)
    {
        floats[index] = value;
    }

    public float GetFloat(int index) {
        return floats[index];
    }

    private static TreeSet<String> reservedNames = new TreeSet<>();
    static {
        reservedNames.add("declare");
        reservedNames.add("if");
        reservedNames.add("else");
        reservedNames.add("GENERATION");
        reservedNames.add("X");
        reservedNames.add("Y");
        reservedNames.add("MINX");
        reservedNames.add("MINY");
        reservedNames.add("MAXX");
        reservedNames.add("MAXY");
        reservedNames.add("this");
    }
    public static boolean IsNameReserved(String name)
    {
        return reservedNames.contains(name);
    }
}
