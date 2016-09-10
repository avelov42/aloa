package com.avelov.Center.Scripts;

import java.util.ArrayList;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 15.07.16.
 */
public class ScriptBlock implements ScriptCommand{
    private ArrayList<ScriptCommand> commands = new ArrayList<>();
    private int commandsCount = 0;

    @Override
    public boolean Run(Cell thisCell, Variables variables, Board board) {
        for(int i = 0; i < commandsCount; i++)
        {
            if(!commands.get(i).Run(thisCell, variables, board))
                return false;
        }
        return true;
    }

    public void AddCommand(ScriptCommand sc)
    {
        commands.add(sc);
        commandsCount++;
    }
}
