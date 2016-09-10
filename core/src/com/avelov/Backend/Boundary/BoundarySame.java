package com.avelov.Backend.Boundary;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 27.07.16.
 */
public class BoundarySame implements BoundaryPolicy {
    @Override
    public float getValue(int x, int y, int xOffset, int yOffset, int index, Board board) {
        return board.getValue(x, y, index);
    }

    @Override
    public String toString() {
        return "Same";
    }

    @Override
    public void setValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value) {
        //Intentionally empty. This value can't be changed.
    }

    @Override
    public void modifyValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value) {
        //Intentionally empty. This value can't be changed.
    }
}
