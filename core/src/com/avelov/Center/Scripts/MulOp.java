package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class MulOp extends Operator {
    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public Token getToken(Token token_right, Token token_left)
    {
        return new MulToken(token_left, token_right);
    }

}
