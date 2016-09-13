package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 19.07.16.
 */
public class ScriptReturn implements ScriptCommand {
    @Override
    public boolean run(Cell thisCell, Variables variables, Board board) {
        return false;
    }
}
