package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class CellReferenceToken implements Token {
    private final int x;
    private final int y;
    private final int index;

    public CellReferenceToken(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

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
        return new CellReferenceExpressionPart(x, y, index);
    }
}
