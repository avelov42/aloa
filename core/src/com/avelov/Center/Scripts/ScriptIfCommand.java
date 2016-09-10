package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 16.07.16.
 */
public class ScriptIfCommand implements ScriptCommand
{
    ScriptArithmeticExpression predicate;
    ScriptBlock block;

    public ScriptIfCommand(ScriptArithmeticExpression predicate, ScriptBlock block)
    {
        this.predicate = predicate;
        this.block = block;
    }

    public boolean Run(Cell thisCell, Variables variables, Board board)
    {
        if(predicate.ValueFloat(thisCell, variables, board) != 0)
            return block.Run(thisCell, variables, board);
        return true;
    }
}
