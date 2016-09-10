package com.avelov.Frontend.CellDrawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import com.avelov.Backend.Cell.Cell;

/**
 * Created by loudrainbow on 23.04.16.
 */
public class SquareCellDrawer extends CellDrawer
{
    private static float gridX = 0.15f; //relative to sizeX;
    private float gridY = 0.15f;

    public SquareCellDrawer(Vector2 boundingBoxSize)
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

        if(currentZoom <= 1f)
        {
            float borderWidth = sizeX * gridX/2;
            float borderHeight = sizeY * gridY/2;
            render.rect(positionX + borderWidth, positionY + borderHeight, sizeX - borderWidth*2, sizeY - borderHeight*2);
        }
        else
            render.rect(positionX, positionY, sizeX, sizeY);
        return this;
    }
}
