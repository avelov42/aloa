package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class GeqOp extends Operator {
    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public Token getToken(Token token_right, Token token_left)
    {
        return new GeqToken(token_left, token_right);
    }

}
