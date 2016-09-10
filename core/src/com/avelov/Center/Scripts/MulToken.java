package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public class MulToken implements Token {
    private final Token token_left;
    private final Token token_right;

    public MulToken(Token token_left, Token token_right)
    {
        this.token_left = token_left;
        this.token_right = token_right;
        is_const = token_left.isConst() && token_right.isConst();
    }
    private final boolean is_const;
    @Override
    public boolean isConst() {
        return is_const;
    }

    @Override
    public float getValue() {
        if(is_const)
            return token_left.getValue() * token_right.getValue();
        System.err.println("Value of MulToken queried");
        return -1;
    }

    public com.avelov.Center.Scripts.ExpressionPart GetExpressionPart()
    {
        if(token_left.isConst() == token_right.isConst())
        {
            if(token_left.isConst())
                return new com.avelov.Center.Scripts.MulExpressionPart(token_left.getValue(), token_right.getValue());
            else
                return new com.avelov.Center.Scripts.MulExpressionPart(token_left.GetExpressionPart(), token_right.GetExpressionPart());
        }
        else {
            if (token_left.isConst())
                return new com.avelov.Center.Scripts.MulExpressionPart(token_left.getValue(), token_right.GetExpressionPart());
            else
                return new com.avelov.Center.Scripts.MulExpressionPart(token_left.GetExpressionPart(), token_right.getValue());
        }
    }
}
