package com.avelov.Backend.Board;

import java.util.Iterator;
import java.util.Map;

import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by piotr on 16.04.16.
 */
public class HexBoard extends Board {
    private int         radius;
    private Coordinates center;

    //for testing purpose
//    public HexBoard(int boardSize, int floats) {
//        this(boardSize, floats, new BoundaryConstant(new float[]{0}), null);
//    }

    public HexBoard(int size, int elementsPerCell, BoundaryPolicy bp, Map<Coordinates, ? extends Cell> values) {
        super(bp);
        this.radius = size;
        this.elementsPerCell = elementsPerCell;
        this.center = new Coordinates(radius, radius);
        this.cells = new Cell[2 * radius + 1][2 * radius + 1];
        this.values = new float[(2 * radius + 1) * (2 * radius + 1) * elementsPerCell];
        this.nextValues = new float[(2 * radius + 1) * (2 * radius + 1) * elementsPerCell];
        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells.length; j++)
                this.cells[i][j] = new Cell(i, j, elementsPerCell, this);

        this.boardSize = 2 * radius + 1;

        for (Map.Entry<Coordinates, ? extends Cell> c : values.entrySet()) {
            if (0 <= c.getKey().x && c.getKey().x <= 2 * radius &&
                    0 <= c.getKey().y && c.getKey().y <= 2 * radius) {
                this.cells[c.getKey().x][c.getKey().y] = c.getValue();
            }
        }

        updateIter = new Iterator<Cell>() {
            int nextY = Math.max(0, center.y - radius);
            int nextX = Math.max(getMinX(nextY), center.x - radius + center.y - nextY);
            int nextMaxX = Math.min(getMaxX(nextY), center.x + radius + Math.min(0, center.y - nextY));

            @Override
            public boolean hasNext() {
                while(nextX > nextMaxX) {
                    nextY++;
                    nextX = Math.max(getMinX(nextY), center.x - radius + Math.max(0, center.y - nextY));
                    nextMaxX = Math.min(getMaxX(nextY), center.x + radius + Math.min(0, center.y - nextY));
                    if(!(nextY <= center.y + radius && nextY < 2*radius+1))
                        break;
                }
                if(nextY <= center.y + radius && nextY < 2*radius+1)
                    return true;
                nextY = Math.max(0, center.y - radius);
                nextX = Math.max(getMinX(nextY), center.x - radius + center.y - nextY);
                nextMaxX = Math.min(getMaxX(nextY), center.x + radius + Math.min(0, center.y - nextY));
                return false;
            }

            @Override
            public Cell next() {
                Cell ret = cells[nextX][nextY];
                nextX++;
                return ret;
            }
        };

    }

    public Iterator<Cell> getSquare(final Coordinates leftTop, final Coordinates rightBottom) {
        Coordinates edge = new Coordinates(leftTop.x, rightBottom.y);
        int size = ((leftTop.y - edge.y > rightBottom.x - edge.x ? leftTop.y - edge.y : rightBottom.x - edge.x  + 1) / 2);
        Coordinates Center = new Coordinates(edge.x + size, edge.y + size);
        return getNeighbours(Center, 2*size);
    }

    @Override
    protected boolean areCoordsCorrect(int x, int y)
    {
        return getMinY() <= y && y <= getMaxY() && getMinX(y) <= x && x <= getMaxX(y);
    }

    @Override
    public Iterator<Cell> getRenderIter(Coordinates c1, Coordinates c2) {
        return getSquare(c1, c2);
    }

    @Override
    public int getMinX(int y) {
        return Math.max(0, radius - y);
    }

    @Override
    public int getMaxX(int y) {
        return Math.min(2*radius, 3*radius - y);
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getMaxY() {
        return 2 * radius;
    }

    private Iterator<Cell> updateIter;

    public Iterator<Cell> getUpdateIter() {
        return updateIter;
    }

    @Override
    public Iterator<Cell> getNeighbours(Coordinates c, int n)
    {
        return getSquare(c, n);
    }

    public Iterator<Cell> getSquare(final Coordinates c, final int n)
    {
        return new Iterator<Cell>() {
            int nextY = Math.max(0, c.y - n);
            int nextX = Math.max(getMinX(nextY), c.x - n + c.y - nextY);
            int nextMaxX = Math.min(getMaxX(nextY), c.x + n + Math.min(0, c.y - nextY));

            @Override
            public boolean hasNext() {
                while(nextX > nextMaxX) {
                    nextY++;
                    nextX = Math.max(getMinX(nextY), c.x - n + Math.max(0, c.y - nextY));
                    nextMaxX = Math.min(getMaxX(nextY), c.x + n + Math.min(0, c.y - nextY));
                    if(!(nextY <= c.y + n && nextY < 2*radius+1))
                        return false;
                }
                return nextY <= c.y + n && nextY < 2*radius+1;
            }

            @Override
            public Cell next() {
                Cell ret = cells[nextX][nextY];
                nextX++;
                return ret;
            }
        };
    }
}