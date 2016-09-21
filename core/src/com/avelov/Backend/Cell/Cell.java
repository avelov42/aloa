package com.avelov.Backend.Cell;

import com.avelov.Backend.Board.Board;

/**
 *
 */
public class Cell
{
    protected final int elements;
    public final int x;
    public final int y;
    protected Board board;

    public Cell(int x, int y, int elements, Board b)
    {
        this.elements = elements;
        this.x = x;
        this.y = y;
        this.board = b;
    }

    public void setNextValue(int index, float value)
    {
        board.setNextValue(x, y, index, value);
    }

    public void setNextValue(int xOffset, int yOffset, int index, float value) {
        board.setNextValue(x, y, xOffset, yOffset, index, value);
    }

    public void modifyNextState(int index, float value) {
        board.modifyNextValue(x, y, index, value);
    }

    public void modifyNextValue(int xOffset, int yOffset, int index, float value) {
        board.modifyNextValue(x, y, xOffset, yOffset, index, value);
    }

    public float getNextValue(int index) { return board.getNextValue(x, y, 0, 0, index); }

    public float getNextValue(int xOffset, int yOffset, int index) {
        return board.getNextValue(x, y, xOffset, yOffset, index);
    }

    public float getValue(int index)
    {
        return board.getValue(x, y, index);
    }

    public float getValue(int xOffset, int yOffset, int index) {
        return board.getValue(x, y, xOffset, yOffset, index);
    }

    public void applyState()
    {
        board.applyStatePartial(x, y);
    }

    public void setNextState(float value, int layer)
    {
        board.setNextValue(x, y, layer, value);
    }

    @Override
    public String toString()
    {
        return board.getCellValueString(x, y);
    }
}
