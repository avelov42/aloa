package com.avelov.Backend.Boundary;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 27.07.16.
 */
public interface BoundaryPolicy {
    float getValue(int x, int y, int xOffset, int yOffset, int index, Board board);

    String toString();

    void setValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value);

    void modifyValue(int x, int y, int offsetX, int offsetY, int index, Board board, float value);


}
