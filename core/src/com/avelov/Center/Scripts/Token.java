package com.avelov.Center.Scripts;

/**
 * Created by mateusz on 22.07.16.
 */
public interface Token
{
    boolean isConst();
    float getValue();
    ExpressionPart GetExpressionPart();
}
