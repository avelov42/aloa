package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 22.07.16.
 */
public class SubExpressionPart implements ExpressionPart {
    private float left_value = 0;
    private float right_value = 0;
    private ExpressionPart left_expr = null;
    private ExpressionPart right_expr = null;
    private int values = 0;

    public float GetValue(int x, int y, Variables v, Board b) {
        switch (values)
        {
            case 0: return left_value - right_value;
            case 1: return left_expr.GetValue(x, y, v, b) - right_expr.GetValue(x, y, v, b);
            case 2: return left_value - right_expr.GetValue(x, y, v, b);
            case 3: return left_expr.GetValue(x, y, v, b) - right_value;
            default: return 0;
        }
    }
    public SubExpressionPart(float left_value, float right_value) {
        this.left_value = left_value;
        this.right_value = right_value;
    }

    public SubExpressionPart(ExpressionPart left_expr, ExpressionPart right_expr) {
        this.left_expr = left_expr;
        this.right_expr = right_expr;
        values = 1;
    }

    public SubExpressionPart(float left_value, ExpressionPart right_expr) {
        this.left_value = left_value;
        this.right_expr = right_expr;
        values = 2;
    }

    public SubExpressionPart(ExpressionPart left_expr, float right_value) {
        this.left_expr = left_expr;
        this.right_value = right_value;
        values = 3;
    }
}
