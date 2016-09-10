package com.avelov.Center.TopologyPackage;

import com.badlogic.gdx.math.Vector3;

import java.util.Map;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Board.Coordinates;
import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 18.04.16.
 */
public interface CenterTopology {
    //Translates frontend float coordinates into Board's int coordinates.
    Coordinates getCoordinates(Vector3 v);

    //Translates int coordinates of a cell into sprite's center coords.
    Vector3 CenterFromCoords(int x, int y);

    Board CreateAutomaton(int size, int floats, BoundaryPolicy bp, Map<Coordinates, ? extends Cell> values);
}
