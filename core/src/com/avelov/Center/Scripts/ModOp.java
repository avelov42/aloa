package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class ModOp extends Operator {
    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public com.avelov.Center.Scripts.Token getToken(com.avelov.Center.Scripts.Token token_right, com.avelov.Center.Scripts.Token token_left)
    {
        return new ModToken(token_left, token_right);
    }

}
