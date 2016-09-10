package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class VarialbeToken implements Token {
    private int variable;

    public VarialbeToken(int variable) {
        this.variable = variable;
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
        return new VariableExpressionPart(variable);
    }
}
