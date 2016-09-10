package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 12.08.16.
 */
public class YToken implements Token {
    @Override
    public boolean isConst() {
        return false;
    }

    @Override
    public float getValue() {
        return 0;
    }

    @Override
    public ExpressionPart GetExpressionPart() {
        return new YExpressionPart();
    }
}
