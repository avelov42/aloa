package com.avelov.Backend.Board;

import java.util.Iterator;
import java.util.Map;

import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Cell.Cell;


/**
 * Created by piotr on 13.03.16.
 */
public class SquareBoard extends Board {
    public SquareBoard(int size, int elementsPerCell, BoundaryPolicy bp, Map<Coordinates, ? extends Cell> cellValues)
    {
        super(bp);
        this.cells = new Cell[size][size];
        this.values = new float[size*size*elementsPerCell];
        this.nextValues = new float[size*size*elementsPerCell];
        this.elementsPerCell = elementsPerCell;
        this.boardSize = size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cells[i][j] = new Cell(i, j, elementsPerCell, this);
            }
        }

        for (Map.Entry<Coordinates, ? extends Cell> c : cellValues.entrySet()) {
            if (0 <= c.getKey().x && c.getKey().x < size &&
                    0 <= c.getKey().y && c.getKey().y < size) {
                for(int i = 0 ; i < elementsPerCell; i++) {
                    values[getValuesLayer(c.getKey().x, c.getKey().y, i)] = c.getValue().getValue(i);
                    nextValues[getValuesLayer(c.getKey().x, c.getKey().y, i)] = c.getValue().getValue(i);
                }
            }
        }
    }

    @Override
    protected boolean areCoordsCorrect(int x, int y)
    {
        return 0 <= x && x < cells.length && 0 <= y && y < cells.length;
    }

    private Iterator<Cell> updateIter = new Iterator<Cell>() {
        int x = -1;
        int y = 0;

        @Override
        public boolean hasNext() {
            if(!((y == cells.length - 1) && (x == cells[0].length - 1)))
                return true;
            x = -1;
            y = 0;
            return false;
        }

        @Override
        public Cell next() {
            if (x == cells[0].length - 1) {
                ++y;
                x = 0;
            } else
                ++x;
            return cells[x][y];
        }
    };

    public Iterator<Cell> getUpdateIter() {
        return updateIter;
    }

    public Iterator<Cell> getSquare(final Coordinates leftTop, final Coordinates rightBottom)
    {
        return getSquare(leftTop.x, leftTop.y, rightBottom.x, rightBottom.y);
    }

    public Iterator<Cell> getSquare(final int leftTopX, final int leftTopY,
                                    final int rightBottomX, final int rightBottomY)
    {
        return new Iterator<Cell>() {
            int endY = rightBottomY;
            int endX = rightBottomX;
            int prevX = leftTopX;
            int prevY = leftTopY;

            @Override
            public boolean hasNext() {
                return prevY >= endY && prevX <= endX;// || currX != endX;
            }

            @Override
            public Cell next() {
                Cell result = cells[prevX][prevY];//new Coordinates(prevX, prevY);
                if (prevX == endX) {
                    prevX = leftTopX;
                    --prevY;
                } else {
                    prevX++;
                }
                return result;
            }
        };
    }

    private Iterator<Cell> getNotNullIter(int leftTopX, int leftTopY,
                                          int rightBottomX, int rightBottomY)
    {
        if (leftTopX < 0)
            leftTopX = 0;
        if (leftTopY > cells.length) {
            leftTopY = cells.length;
        }
        if (rightBottomY < 0) {
            rightBottomY = 0;
        }
        if (rightBottomX > cells[0].length) {
            rightBottomX = cells[0].length;
        }
        return getSquare(leftTopX, leftTopY, rightBottomX, rightBottomY);
    }

    public Iterator<Cell> getNeighbours(final Coordinates c, final int n)
    {
        return getNotNullIter(clamp(c.x - n), clamp(c.y + n), clamp(c.x + n), clamp(c.y - n));
    }

    @Override
    public Iterator<Cell> getRenderIter(
            final Coordinates c1,
            final Coordinates c2)
    {
        return getSquare(clamp(c1), clamp(c2));
    }

    @Override
    public int getMinX(int y) {
        return 0;
    }

    @Override
    public int getMaxX(int y) {
        return boardSize - 1;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getMaxY() {
        return boardSize - 1;
    }

    protected int clamp(int c)
    {
        if(c < 0)
            return 0;
        if(boardSize <= c)
            return boardSize -1;
        return c;
    }

    protected Coordinates clamp(final Coordinates coords)
    {
        return new Coordinates(clamp(coords.x), clamp(coords.y));
    }
}
