package com.avelov.Frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

import com.avelov.Center.BoardHandler;
import com.avelov.Center.BrushState;
import com.avelov.Backend.Brush.CellFunctor;
import com.avelov.Backend.Cell.Cell;

/**
 * Implements brush functionality.
 * Model class.
 */
public class Brush implements CellFunctor
{
    /**
     * Describes how much time must elapse until brush with
     * density set to 100% will affect all cells underneath it.
     */
    private static final float momentInSeconds = 0.1443f;

    private Random rand;
    private BoardHandler handler;
    private BoardRenderer renderer;

    private ArrayList<BrushState> states;
    private int currentState;

    private int size;
    private float density;
    private boolean isDrawing;
    private boolean isBeingModified;
    private boolean isVisible;

    public Brush(BoardHandler handler, BoardRenderer renderer, ArrayList<BrushState> states)
    {
        this.rand = new Random();
        this.handler = handler;
        this.renderer = renderer;
        this.states = states;
        this.currentState = 0;
        this.size = 2;
        this.density = 1f;
        this.isDrawing = false;
        this.isVisible = true;
    }

    /**
     * Selects state to be applied when drawing.
     */
    public void nextState() { setStateID(currentState + 1);}
    public void prevState() { setStateID(currentState - 1);}
    public void setStateID(int state) { currentState = state % states.size();}
    public int getCurrentStateID() { return currentState;}
    public BrushState getCurrentState() { return states.get(currentState);}
    public BrushState getState(int id) { return states.get(id);}
    public int getStatesCount() { return states.size();}

    /**
     * Call to obatain boardSize of brush filling whole view.
     */
    public int getMaxSize()
    {
        //@todo naprawić to dla hexow (mozna podzielic przez cellSize or sth)
        return renderer.getCellsCountOnY()/2;
    }

    /**
     * Call these functions when user wants to draw.
     */
    public void startDrawing() { isDrawing = true;}
    public void stopDrawing() { isDrawing = false;}

    /**
     * Call these if user changes brush properties.
     */
    public void startModifying() { isBeingModified = true;}
    public void stopModifying() { isBeingModified = false;}

    /**
     * Call these if user wants to hide/show brush ghost.
     */
    public boolean isVisible() { return isVisible;}
    public void setVisible(boolean isVisible) {this.isVisible = isVisible;}

    /**
     * Size getter & setter.
     */
    public int getSize() { return size;}
    public void setSize(int size)
    {
        this.size = MathUtils.clamp(size, 0, Integer.MAX_VALUE);
    }

    /**
     * Density getter & setter.
     */
    public float getDensity() { return density; }
    public void setDensity(float density)
    {
        this.density = MathUtils.clamp(density, 0f, 1f);
    }

    /**
     * Render and apply brush.
     */
    public void render()
    {
        Vector3 position;

        if(isBeingModified) position = renderer.screenToVirtual(new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0));
        else position = renderer.screenToVirtual(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if(isVisible && isDrawing)
            handler.doBrush(this, position, size);
        if(isVisible)
            renderer.renderBrush(states.get(currentState).getCell(), handler.getNeighbours(position, size), true);
    }

    @Override
    public String toString()
    {
        return states.get(currentState).getDescription();
    }

    /**
     * Implementation of the CellFunctor for backend.
     * @param c Cell to be modified
     */
    @Override
    public void apply(Cell c)
    {
        /* PIO
        float estimatedFramesInMoment = Gdx.graphics.getFramesPerSecond() * momentInSeconds;
        float instantDensity = 1 - (float) Math.pow((1-density), 1/(float)estimatedFramesInMoment);
         */

        /* RYBE */
        float estimatedFramesInMoment = Gdx.graphics.getFramesPerSecond() * momentInSeconds;
        float instantDensity = density / estimatedFramesInMoment;

        //System.out.println((float) Math.pow((1-density), 1/(float)estimatedFramesInMoment));
        if(Math.abs(density - 1) <= 0.01 || rand.nextFloat() <= instantDensity)
        {
            c.setNextState(states.get(currentState).getCell().getValue());
            c.applyState();
        }
    }
}
