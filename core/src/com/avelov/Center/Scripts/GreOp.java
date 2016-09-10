package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class GreOp extends Operator {
    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public com.avelov.Center.Scripts.Token getToken(com.avelov.Center.Scripts.Token token_right, com.avelov.Center.Scripts.Token token_left)
    {
        return new GreToken(token_left, token_right);
    }

}
