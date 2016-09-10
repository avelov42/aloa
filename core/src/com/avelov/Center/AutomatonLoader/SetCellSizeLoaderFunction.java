package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonBlueprint;

/**
 * Created by mateusz on 28.07.16.
 */
public class SetCellSizeLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(String parameter, BufferedReader br, AutomatonBlueprint ab) throws AutomatonLoaderFunctionException {
        String param = parameter.trim();
        int cellSize;
        try {
            cellSize = Integer.parseInt(param);
        }
        catch (NumberFormatException e)
        {
            throw new AutomatonLoaderFunctionException("Cell Size is not an Integer", "CellSize");
        }
        ab.setCellSize(cellSize);
    }
}
