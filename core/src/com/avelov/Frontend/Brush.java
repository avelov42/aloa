package com.avelov.Frontend;

import com.avelov.Center.Files.Layer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.List;
import java.util.Random;

import com.avelov.Center.BoardHandler;
import com.avelov.Center.BrushState;
import com.avelov.Backend.Brush.CellFunctor;
import com.avelov.Backend.Cell.Cell;

/**
 * Represents a brush object, which is capable of
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

    private List<Layer> layerList;
    private int[] layerBrushStateIndex; //i-th value describes which brush state of this layer is chosen
    private int currentLayer = 0;

    private int size = 2;
    private float density = 1f;
    private boolean isDrawing = false;
    private boolean showcaseMode = false;
    private boolean isVisible = true;

    public Brush(BoardHandler handler, BoardRenderer renderer, List<Layer> layerList)
    {
        this.rand = new Random();
        this.handler = handler;
        this.renderer = renderer;
        this.layerList = layerList;
        this.layerBrushStateIndex = new int[layerList.size()];

    }

    public void setLayerByIndex(int layer)
    {
        currentLayer = layer;
    }

    public void setBrushStateByIndex(int brushStateIndex)
    {
        layerBrushStateIndex[currentLayer] = brushStateIndex;
    }

    public int getCurrentLayerIndex()
    {
        return currentLayer;
    }

    public int getCurrentBrushStateIndex()
    {
        return layerBrushStateIndex[currentLayer];
    }

    public float getCurrentBrushStateValue()
    {
        return layerList.get(currentLayer).getBrushStates().get(layerBrushStateIndex[currentLayer]).getValue();
    }

    public int getLayerCount()
    {
        return layerList.size();
    }

    public int getLayerBrushStateCount(int layer)
    {
       return layerList.get(layer).getBrushStates().size();
    }

    public String getCurrentLayerName()
    {
        return layerList.get(currentLayer).toString();
    }

    public String getCurrentBrushStateName()
    {
        return layerList.get(currentLayer).getBrushStates().get(layerBrushStateIndex[currentLayer]).toString();
    }


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
    public void startShowcase() { showcaseMode = true;}
    public void stopShowcase() { showcaseMode = false;}

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

        if(showcaseMode) position = renderer.screenToVirtual(new Vector3(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0));
        else position = renderer.screenToVirtual(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if(isVisible && isDrawing)
            handler.doBrush(this, position, size);
        //if(isVisible)
        //    renderer.renderBrush(states.get(layerBrushStateIndex).getValue(), handler.getNeighbours(position, size), true);
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
            c.setNextValue(currentLayer, getCurrentBrushStateValue());
            c.applyState();
        }
    }
}
