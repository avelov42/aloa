package com.avelov.Center.Scripts;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 16.07.16.
 */
public class ScriptIfElseCommand implements ScriptCommand
{
    ScriptArithmeticExpression predicate;
    com.avelov.Center.Scripts.ScriptBlock blockIf;
    com.avelov.Center.Scripts.ScriptBlock blockElse;

    public ScriptIfElseCommand(ScriptArithmeticExpression predicate, com.avelov.Center.Scripts.ScriptBlock blockIf, com.avelov.Center.Scripts.ScriptBlock blockElse)
    {
        this.predicate = predicate;
        this.blockIf = blockIf;
        this.blockElse = blockElse;
    }

    public boolean Run(Cell thisCell, Variables variables, Board board)
    {
        if(predicate.ValueFloat(thisCell, variables, board) != 0)
            return blockIf.Run(thisCell, variables, board);
        else
            return blockElse.Run(thisCell, variables, board);
    }

}
