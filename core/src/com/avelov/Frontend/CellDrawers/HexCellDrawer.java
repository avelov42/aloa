package com.avelov.Frontend.CellDrawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import com.avelov.Backend.Cell.Cell;

/**
 * Created by loudrainbow on 23.04.16.
 */
public class HexCellDrawer extends CellDrawer
{
    public HexCellDrawer(Vector2 boundingBoxSize)
    {
        super(boundingBoxSize);
    }

    @Override
    public CellDrawer draw(Cell cell, float currentZoom, boolean ghost)
    {
        if(tinter != null)
            render.setColor(tinter.getTint(cell));
        else
            render.setColor(Color.WHITE);
        if(ghost)
        {
            render.getColor().r = render.getColor().r * (1-ghostDim);
            render.getColor().g = render.getColor().g * (1-ghostDim);
            render.getColor().b = render.getColor().b * (1-ghostDim);
            render.getColor().a = CellDrawer.ghostAlpha;
        }


        if(currentZoom <= 2.41)
            render.circle(positionX+sizeX/2, positionY+sizeY/2, sizeY/2, 6);
        else
            render.circle(positionX+sizeX/2, positionY+sizeY/2, sizeX/2, 6);


        return this;
    }
}
