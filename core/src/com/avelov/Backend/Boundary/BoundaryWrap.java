package com.avelov.Backend.Boundary;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 27.07.16.
 */
public class BoundaryWrap implements BoundaryPolicy {

    private int get(int v, int offset, int min, int max)
    {
        int size = max - min + 1;
        if(offset > 0) {
            offset %= size;
            if(v + offset > max)
                return min + v + offset - max;
            return v + offset;
        }
        else
        {
            offset = -offset;
            offset %= size;
            if(v - offset < min)
                return max + 1 - (min - (v - offset));
            return v - offset;
        }
    }

    @Override
    public float getValue(int x, int y, int xOffset, int yOffset, int index, Board board) {
        return board.getValue(get(x, xOffset, board.getMinX(y), board.getMaxX(y)),
                get(y, yOffset, board.getMinY(), board.getMaxY()), index);

    }

    @Override
    public String toString() {
        return "Wrap";
    }

    @Override
    public void setValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value) {
        board.setNextValue(get(x, offsetX, board.getMinX(y), board.getMaxX(y)),
                get(y, offsetY, board.getMinY(), board.getMaxY()), index, value);
    }

    @Override
    public void modifyValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value) {
        board.modifyNextValue(get(x, offsetX, board.getMinX(y), board.getMaxX(y)),
                get(y, offsetY, board.getMinY(), board.getMaxY()), index, value);
    }
}
