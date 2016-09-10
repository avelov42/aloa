package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 23.07.16.
 */
public class VariableExpressionPart implements ExpressionPart {
    private int variable;

    public VariableExpressionPart(int variable) {
        this.variable = variable;
    }

    @Override
    public float GetValue(int x, int y, Variables v, Board b) {
        return v.GetFloat(variable);
    }
}
