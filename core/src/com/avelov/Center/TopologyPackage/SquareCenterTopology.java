package com.avelov.Center.TopologyPackage;

import com.badlogic.gdx.math.Vector3;

import java.util.Map;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Board.Coordinates;
import com.avelov.Backend.Board.SquareBoard;
import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 18.04.16.
 */
public class SquareCenterTopology implements CenterTopology {
    @Override
    public Coordinates getCoordinates(Vector3 v) {
        return new Coordinates((int) Math.floor(v.x), (int) Math.floor(v.y));
    }

    @Override
    public Vector3 CenterFromCoords(int x, int y) {
        return new Vector3(x + 0.5f, y + 0.5f, 0.0f);
    }

    @Override
    public Board CreateAutomaton(int size, int floats, BoundaryPolicy bp, Map<Coordinates, ? extends Cell> values) {
        return new SquareBoard(size, floats, bp, values);
    }
}
