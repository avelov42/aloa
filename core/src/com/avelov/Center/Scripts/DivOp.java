package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class DivOp extends Operator {
    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public Token getToken(Token token_right, Token token_left)
    {
        return new DivToken(token_left, token_right);
    }

}
