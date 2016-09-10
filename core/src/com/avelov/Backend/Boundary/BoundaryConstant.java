package com.avelov.Backend.Boundary;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 27.07.16.
 */
public class BoundaryConstant implements BoundaryPolicy {
    float[] values;

    public BoundaryConstant(float[] values)
    {
        this.values = values;
    }

    @Override
    public float getValue(int x, int y, int xOffset, int yOffset, int index, Board board) {
        return values[index];
    }

    @Override
    public String toString() {
        return "Constant";
    }

    @Override
    public void setValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value) {
        //Intentionally do nothing. Value can't be changed.
    }

    @Override
    public void modifyValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value) {
        //Intentionally do nothing. Value can't be changed.
    }
}
