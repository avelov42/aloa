package com.avelov.Center.AutomatonLoader;

import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.Layer;
import com.avelov.Center.Scripts.ColoringScriptParser;
import com.avelov.Center.Scripts.ScriptException;
import com.avelov.Frontend.CellDrawers.Tinters.Tinter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 18.09.16.
 */
public class AddLayerLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(AutomatonLoader.Line line, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
        if(line.tokens.size() < 5)
            throw new AutomatonLoaderFunctionException("addLayer requires at least 5 parameters", "AddLayerLoaderFunction");

        String name = line.tokens.get(0);
        float min, max, def;

        try {
            min = Float.parseFloat(line.tokens.get(1));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException("min should be floating point number", "AddLayerLoaderFunction");
        }

        try {
            max = Float.parseFloat(line.tokens.get(2));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException("max should be floating point number", "AddLayerLoaderFunction");
        }

        try {
            def = Float.parseFloat(line.tokens.get(3));
        } catch (NumberFormatException e) {
            throw new AutomatonLoaderFunctionException("def should be floating point number", "AddLayerLoaderFunction");
        }

        List<AutomatonInfo.TinterDetails> tinters = new ArrayList<>();

        for(int i = 4; i < line.tokens.size(); i++)
        {
            String s = line.tokens.get(i);
            String coloringName;
            FileHandle coloring;

            int firstSpace = s.indexOf(' ');
            boolean isPredefined =  firstSpace != -1 && s.substring(0, firstSpace).equals("predefined");

            if(isPredefined) {
                coloringName = s.substring(firstSpace+1);
                coloring = Gdx.files.internal("predefs/coloring/" + coloringName); //TODO might throw?
            }
            else {
                coloringName = s;
                coloring = Gdx.files.local("aloa/coloring/" + s);
            }

            if (!coloring.exists())
                throw new AutomatonLoaderFunctionException("Coloring " + coloringName + " doesn't exist", "Coloring");
            try {
                //@todo: coloring may not be defined for all required values
                Tinter t = ColoringScriptParser.parse(coloring);
                tinters.add(new AutomatonInfo.TinterDetails(t, coloringName, isPredefined));
            } catch (ScriptException e) {
                throw new AutomatonLoaderFunctionException(
                        "Error in predefined script (WTF?) \" " + coloringName + " \": " + e.getMessage() + " ", "Coloring");
            }
        }

        Layer l = new Layer(name, min, max, def, tinters);
        ab.addLayer(l);
    }

    @Override
    public String getName() {
        return "addlayer";
    }
}
