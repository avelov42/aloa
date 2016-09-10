package com.avelov.Frontend.CellDrawers.Tinters;

import com.badlogic.gdx.graphics.Color;

import com.avelov.Backend.Cell.Cell;

/**
 * Interface of object which has ability to supply a tint based on given cell.
 */
public interface Tinter
{
    /**
     * @param cell Cell to be interpreted.
     * @return Color for tint.
     */
    Color getTint(Cell cell);
}
