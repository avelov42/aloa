package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 23.07.16.
 */
public class ConstantExpressionPart implements ExpressionPart {
    private float v;

    public ConstantExpressionPart(float v) {
        this.v = v;
    }

    @Override
    public float GetValue(int x, int y, Variables v, Board b) {
        return this.v;
    }
}
