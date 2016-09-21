package com.avelov.Backend.Board;

import com.badlogic.gdx.files.FileHandle;

import com.avelov.Center.Files.ISavable;
import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Brush.CellFunctor;
import com.avelov.Backend.Cell.Cell;

import java.util.Iterator;

/**
 *
 */
public abstract class Board implements ISavable
{
    protected int boardSize = 0;
    protected int elementsPerCell = 0;
    protected float[] values;
    protected float[] nextValues;
    protected Cell[][] cells;
    protected BoundaryPolicy boundary;
    protected int generation = 0;

    public Board(BoundaryPolicy boundary)
    {
        this.boundary = boundary;
    }

    public Cell getCell(Coordinates c) {
        if(areCoordsCorrect(c.x, c.y))
            return cells[c.x][c.y];
        return null;
    }

    public abstract Iterator<Cell> getUpdateIter();

    public abstract Iterator<Cell> getNeighbours(Coordinates c, int n);

    //Returns iterator over cells in rectange(c1, c2)
    public abstract Iterator<Cell> getSquare(Coordinates c1, Coordinates c2);

    public void setNextValue(int x, int y, int layer, float value) {
        if(areCoordsCorrect(x,y))
            nextValues[getValuesLayer(x, y, layer)] = value;
    }

    public void setNextValue(int x, int y, int offsetX, int offsetY, int layer, float value) {
        if(areCoordsCorrect(x + offsetX, y + offsetY))
            nextValues[getValuesLayer(x + offsetX, y + offsetY, layer)] = value;
        else
            boundary.setValue(x, y, offsetX, offsetY, layer, this, value);
    }

    public void modifyNextValue(int x, int y, int layer, float value) {
        if(areCoordsCorrect(x,y))
            nextValues[getValuesLayer(x, y, layer)] += value;
    }

    public void modifyNextValue(int x, int y, int offsetX, int offsetY, int layer, float value) {
        if(areCoordsCorrect(x + offsetX, y + offsetY))
            nextValues[getValuesLayer(x + offsetX, y + offsetY, layer)] += value;
        else
            boundary.modifyValue(x, y, offsetX, offsetY, layer, this, value);
    }

    public float getValue(int x, int y, int layer)
    {
        return values[getValuesLayer(x, y, layer)];
    }

    public float getValue(int x, int y, int offsetX, int offsetY, int layer) {
        if(areCoordsCorrect(x + offsetX, y + offsetY))
            return values[getValuesLayer(x + offsetX, y + offsetY, layer)];
        else
            return boundary.getValue(x, y, offsetX, offsetY, layer, this);
    }

    public float getNextValue(int x, int y, int offsetX, int offsetY, int layer) {
        if(areCoordsCorrect(x + offsetX, y + offsetY))
            return nextValues[getValuesLayer(x + offsetX, y + offsetY, layer)];
        else
            return boundary.getValue(x, y, offsetX, offsetY, layer, this);
    }

    public void applyStatePartial(int x, int y)
    {
        for(int element = 0; element < elementsPerCell; element++) {
            int layer = getValuesLayer(x, y, element);
            values[layer] = nextValues[layer];
        }
    }

    public void nextIteration()
    {
        System.arraycopy(nextValues, 0, values, 0, values.length);
        generation++;
    }

    public void setCellValues(int x, int y, float[] values) {
        for(int element = 0; element < elementsPerCell; element++) {
            int layer = getValuesLayer(x, y, element);
            nextValues[layer] = values[element];
        }
    }

    public String getCellValueString(int x, int y)
    {
        StringBuilder sb = new StringBuilder();
        for(int element = 0; element < elementsPerCell; element++) {
            sb.append(values[getValuesLayer(x, y, element)]);
            sb.append(' ');
        }
        return sb.toString();
    }

    protected abstract boolean areCoordsCorrect(int x, int y);

    public abstract Iterator<Cell> getRenderIter(
            final Coordinates c1,
            final Coordinates c2);

    protected int getValuesLayer(int x, int y, int layer)
    {
        return (y * boardSize + x) * elementsPerCell + layer;
    }

    public void doBrush(CellFunctor brush, Coordinates c, int n)
    {
        Iterator<Cell> iter = getNeighbours(c, n);
        Cell currCell;
        while(iter.hasNext())
        {
            currCell = iter.next();
            if(currCell != null)
                brush.apply(currCell);
        }
    }

    public void save(FileHandle fileHandle)
    {
        fileHandle.writeString("boardSize: " + Integer.toString(boardSize) + "\n", true);
        fileHandle.writeString("cellSize: " + Integer.toString(elementsPerCell) + "\n", true);
        fileHandle.writeString("boundary: " + boundary.toString() + "\n", true);
        Iterator<Cell> ic = getUpdateIter();
        fileHandle.writeString("values:\n", true);
        while (ic.hasNext()) {
            Cell c = ic.next();
            String value = c.toString();
            if (!value.equals("0"))
                fileHandle.writeString(c.x + " " + c.y + " " + value + "\n", true);
        }
        fileHandle.writeString("valuesEnd\n", true);
    }

    public abstract int getMinX(int y);
    public abstract int getMaxX(int y);

    public abstract int getMinY();
    public abstract int getMaxY();

    public int getGeneration() {
        return 0;
    }
}
