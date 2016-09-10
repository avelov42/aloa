package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 23.07.16.
 */
public class CellReferenceExpressionPart implements ExpressionPart {
    private final int xOffset;
    private final int yOffset;
    private final int index;

    public CellReferenceExpressionPart(int xOffset, int yOffset, int index) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.index = index;
    }

    @Override
    public float GetValue(int x, int y, Variables v, Board b) {
        return b.getValue(x, y, xOffset, yOffset, index);
    }
}
