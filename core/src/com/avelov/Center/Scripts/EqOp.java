package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class EqOp extends Operator {
    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public Token getToken(Token token_right, Token token_left)
    {
        return new EqToken(token_left, token_right);
    }

}
