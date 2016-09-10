package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;

/**
 * Created by mateusz on 22.07.16.
 */
public interface ExpressionPart {
    float GetValue(int x, int y, Variables v, Board b);
}
