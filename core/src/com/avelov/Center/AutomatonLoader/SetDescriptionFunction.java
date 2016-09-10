package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;
import java.io.IOException;

import com.avelov.Center.Files.AutomatonBlueprint;

/**
 * Created by mateusz on 28.07.16.
 */
public class SetDescriptionFunction implements AutomatonLoaderFunction {

    @Override
    public void run(String parameter, BufferedReader br, AutomatonBlueprint ab) throws AutomatonLoaderFunctionException {
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while (!((line = br.readLine()) == null || line.toLowerCase().equals("descriptionend"))) {
                sb.append(line);
            }
            ab.setDescription(sb.toString());
        } catch (IOException e) {
            throw new AutomatonLoaderFunctionException(
                    "Cannot read description", "Description");
        }
    }
}
