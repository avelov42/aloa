package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class AndOp extends Operator {
    @Override
    public int getPrecedence() {
        return 4;
    }

    @Override
    public Token getToken(Token token_right, Token token_left)
    {
        return new AndToken(token_left, token_right);
    }

}
