package com.avelov.Center.NativeScripts;

import java.util.Iterator;

import com.avelov.Center.Scripts.Script;
import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 22.07.16.
 */
public class SquareHeatNativeScript extends Script {
    @Override
    public void run(Board board) {
        Iterator<Cell> iter = board.getUpdateIter();
        while (iter.hasNext()) {
            Cell currCell = iter.next();
            float x1 = 0;
            float x2 = 0;
            float x3 = 0;
            float x4 = 0;
            float c1 = currCell.getValue(1, 0, 0);
            float c2 = currCell.getValue(0, 1, 0);
            float c3 = currCell.getValue(0, -1, 0);
            float c4 = currCell.getValue(-1, 0, 0);

            float currCellValue = currCell.getValue(0);

            if(c1 > currCellValue)
                x1 = (c1 - currCellValue) * 0.25f;

            if(c2 > currCellValue)
                x2 = (c2 - currCellValue) * 0.25f;

            if(c3 > currCellValue)
                x3 = (c3 - currCellValue) * 0.25f;

            if(c4 > currCellValue)
                x4 = (c4 - currCellValue) * 0.25f;

            currCell.modifyNextState(0, x1 + x2 + x3 + x4);
            board.modifyNextValue(currCell.x, currCell.y, 1, 0, 0, x1);
            board.modifyNextValue(currCell.x, currCell.y, 0, 1, 0, x2);
            board.modifyNextValue(currCell.x, currCell.y, 0, -1, 0, x3);
            board.modifyNextValue(currCell.x, currCell.y, -1, 0, 0, x4);
        }

        board.nextIteration();
    }
}