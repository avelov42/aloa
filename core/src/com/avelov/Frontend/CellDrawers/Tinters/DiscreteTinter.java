package com.avelov.Frontend.CellDrawers.Tinters;

import com.badlogic.gdx.graphics.Color;

import com.avelov.Backend.Cell.Cell;

/**
 * Simple implementation of Tinter, which maps subsequent integers onto given colors.
 */
public class DiscreteTinter implements Tinter
{
    private Color[] colorOfState;

    public DiscreteTinter(Color[] colors)
    {
        colorOfState = colors;
    }
    @Override
    public Color getTint(Cell cell)
    {
        try
        {
            return colorOfState[(int)(cell.getValue(0)+0.5f)];
        }
        catch(IndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException("Unknown value of the cell: " + (int)cell.getValue(0), e);
        }

    }

}
