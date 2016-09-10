package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class ThisCellToken implements Token {
    private int index;

    public ThisCellToken(int index) {
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
        return new com.avelov.Center.Scripts.ThisCellExpressionPart(index);
    }
}
