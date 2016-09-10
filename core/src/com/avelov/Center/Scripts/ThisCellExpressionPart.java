package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 23.07.16.
 */
public class ThisCellExpressionPart implements ExpressionPart {
    private int index;

    public ThisCellExpressionPart(int index) {
        this.index = index;
    }

    @Override
    public float GetValue(int x, int y, Variables v, Board b) {
        return b.getValue(x, y, index);
    }
}
