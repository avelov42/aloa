package com.avelov.Center.Scripts;

import java.util.Iterator;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 16.07.16.
 */
public class Script {
    Variables variables;
    com.avelov.Center.Scripts.ScriptCommand command;

    protected Script() {}

    public Script(Variables variables, ScriptCommand command) {
        this.variables = variables;
        this.command = command;
    }

    public void Run(Board board)
    {
        Iterator<Cell> iter = board.getUpdateIter();
        while(iter.hasNext())
        {
            Cell c = iter.next();
            command.Run(c, variables, board);
            variables.ResetLocals();
        }
        board.nextIteration();
    }
}
