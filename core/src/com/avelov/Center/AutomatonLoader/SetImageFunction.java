package com.avelov.Center.AutomatonLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonBlueprint;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by mateusz on 28.07.16.
 */
public class SetImageFunction implements AutomatonLoaderFunction {
    @Override
    public void run(String parameter, BufferedReader br, AutomatonBlueprint ab) throws AutomatonLoaderFunctionException {
        String[] params = parameter.split("\\s+");
        if (params.length == 2 && params[0].toLowerCase().equals("predefined")) {
            FileHandle script = Gdx.files.internal("predefImages/" + params[1]);
            if (!script.exists())
                throw new AutomatonLoaderFunctionException("Image " + params[1] + " doesn't exist", "Image");
            ab.setImagePath("predefImages/" + params[1]);
            ab.setIsImagePredefined(true);
        } else if (params.length == 1) //load rules saved by user
            throw new NotImplementedException();
        else
            throw new AutomatonLoaderFunctionException("Syntax error, use image: {<name>|predefined <name>}", "Image");
    }
}
