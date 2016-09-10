package com.avelov.Backend.Boundary;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 27.07.16.
 */
public class BoundaryMirror implements BoundaryPolicy {

    private int getX(int v, int offset, int min, int max)
    {
        int size = max - min + 1;

        if(offset >= 0) {
            if(v + offset <= max)
                return v+offset;
            else {
                int rem = (v + offset - max - 1) % (2*size + 1);
                if(rem < size)
                    return max - rem;
                return min + rem - size;
            }
        }
        if (v + offset >= min)
            return v + offset;
        else {
            int rem = (min - (v+offset) - 1) % (2 * size + 1);
            if (rem < size)
                return min + rem;
            else
                return max - (rem - size);
        }
    }

    @Override
    public float getValue(int x, int y, int xOffset, int yOffset, int index, Board board) {
        return board.getValue(getX(x, xOffset, board.getMinX(y), board.getMaxX(y)),
                getX(y, yOffset, board.getMinY(), board.getMaxY()), index);
    }

    @Override
    public String toString() {
        return "Mirror";
    }

    @Override
    public void setValue(int x, int y, int xOffset, int yOffset, int index, Board board, float value) {
        board.setNextValue(getX(x, xOffset, board.getMinX(y), board.getMaxX(y)),
                getX(y, yOffset, board.getMinY(), board.getMaxY()), index, value);
    }

    @Override
    public void modifyValue(int x, int y, int xOffset, int yOffset, int index, Board board, float value) {
        board.modifyNextValue(getX(x, xOffset, board.getMinX(y), board.getMaxX(y)),
                getX(y, yOffset, board.getMinY(), board.getMaxY()), index, value);
    }
}
