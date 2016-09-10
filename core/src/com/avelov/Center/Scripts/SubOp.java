package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class SubOp extends Operator {
    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public Token getToken(Token token_right, Token token_left)
    {
        return new SubToken(token_left, token_right);
    }

}
