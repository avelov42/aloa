package com.avelov.Frontend.CellDrawers;

import com.avelov.Aloa;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import com.avelov.Backend.Cell.Cell;

/**
 * Abstract class representing cell view.
 * Method draw assumes that shapeRenderer is started.
 */
public abstract class CellDrawer
{
    protected float positionX, positionY;
    protected float sizeX, sizeY;
    protected ShapeRenderer render = Aloa.assets.shape;
    protected com.avelov.Frontend.CellDrawers.Tinters.Tinter tinter;
    public static final float ghostAlpha = 0.8f;// * 1.618f;
    public static final float ghostDim = 0.2f;

    /**
     * This protected constructor sets up the cell's boardSize via topology interface.
     * @param boundingBoxSize :)
     */
    protected CellDrawer(Vector2 boundingBoxSize)
    {
        sizeX = boundingBoxSize.x;
        sizeY = boundingBoxSize.y;
        positionX = 42;
        positionY = 42;
    }

    public CellDrawer setTinter(com.avelov.Frontend.CellDrawers.Tinters.Tinter tinter)
    {
        this.tinter = tinter;
        return this;
    }

    public com.avelov.Frontend.CellDrawers.Tinters.Tinter getTinter()
    {
        return tinter;
    }

    /**
     * Position of the center!
     * @param x
     * @param y
     * @return
     */
    public CellDrawer setPosition(float x, float y)
    {
        positionX = x;
        positionY = y;
        return this;
    }

    public float getSizeX() { return sizeX;}
    public float getSizeY() { return sizeY;}

    public abstract CellDrawer draw(Cell cell, float currentZoom, boolean ghost);


}
