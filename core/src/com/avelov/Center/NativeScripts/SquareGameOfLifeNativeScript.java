package com.avelov.Center.NativeScripts;

import java.util.Iterator;

import com.avelov.Center.Scripts.Script;
import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 21.07.16.
 */
public class SquareGameOfLifeNativeScript extends Script {
    public SquareGameOfLifeNativeScript()
    {}

    static private float[] aliveCell = new float[] {1};
    static private float[] deadCell = new float[] {0};

    @Override
    public void run(Board board) {
        Iterator<Cell> iter = board.getUpdateIter();
        while(iter.hasNext())
        {
            Cell currCell = iter.next();
            int aliveCells = 0;
            for(int x = -1; x <= 1; x++)
                for(int y = -1; y <=1; y++)
                    if(x != 0 || y != 0) {
                        if(currCell.getValue(x, y, 0) == 1)
                            aliveCells++;
                    }
            if(currCell.getValue(0) == 0)
            {
                if(aliveCells == 3)
                    currCell.setNextState(aliveCell);
                else
                    currCell.setNextState(deadCell);
            }
            else
            {
                if (2 <= aliveCells && aliveCells <= 3)
                    currCell.setNextState(aliveCell);
                else
                    currCell.setNextState(deadCell);
            }
        }
        board.nextIteration();
    }
}
