package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 15.07.16.
 */
public interface ScriptCommand {
    boolean Run(Cell thisCell, Variables variables, Board board);
}
