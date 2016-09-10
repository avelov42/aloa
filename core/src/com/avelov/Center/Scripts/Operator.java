package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public abstract class Operator
{
    public abstract int getPrecedence();
    public abstract Token getToken(Token token_right, Token token_left);
    public boolean isLeftParenthesis() { return false; }
}
