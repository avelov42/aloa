package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class ConstantToken implements Token {
    private float v;

    public ConstantToken(float v) {
        this.v = v;
    }

    @Override
    public boolean isConst() {
        return true;
    }

    @Override
    public float getValue() {
        return v;
    }

    @Override
    public ExpressionPart GetExpressionPart() {
        return new ConstantExpressionPart(v);
    }
}
