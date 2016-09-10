package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 12.08.16.
 */
public class MinYExpressionPart implements ExpressionPart {
    @Override
    public float GetValue(int x, int y, Variables v, Board b) {
        return b.getMinY();
    }
}
