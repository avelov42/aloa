package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 12.08.16.
 */
public class LeftParenthesisOp extends Operator {
    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public Token getToken(Token token_right, Token token_left) {
        return null;
    }

    @Override
    public boolean isLeftParenthesis()
    {
        return true;
    }
}
