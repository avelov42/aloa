package com.avelov.Center.TopologyPackage;

import com.badlogic.gdx.math.Vector3;

import java.util.Map;

import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Board.Coordinates;
import com.avelov.Backend.Board.HexBoard;
import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Cell.Cell;

/**
 * Created by mateusz on 18.04.16.
 */
public class HexCenterTopology implements CenterTopology {
    @Override
    public Coordinates getCoordinates(Vector3 v) {
        float s = (float) Math.sqrt(3.0);

        //coordinates with moved x and scaled y
        float x = v.x - 0.5f;
        float y = v.y / s;

        //Coordinates of leftBottom corner of bounding 1.5x0.5 rectangle.
        int a = (int) (x * (2.0f / 3.0f));
        int b = (int) (y * 2.0f);

        //Coords of bounding rect in original x and scaled y (<- perf.)
        float i = a * 1.5f + 0.5f;
        float j = (float) b;

        //Choose bounding box coords. Two of its vertices are coords of hex centers.
        Vector3 v1, v2;
        if ((a + b) % 2 == 0) {
            v1 = new Vector3(i + 1.5f, j * s / 2.0f, 0.0f);
            v2 = new Vector3(i, (j + 1) * s / 2.0f, 0.0f);
        } else {
            v1 = new Vector3(i, j * s / 2.0f, 0.0f);
            v2 = new Vector3(i + 1.5f, (j + 1) * s / 2.0f, 0.0f);
        }

        //v1 contains coords of hex center closer to given point
        if (v1.dst2(v) >= v2.dst2(v))
            v1 = v2;

        v1.x -= 0.5f;
        v1.y -= s / 2.0f;

        return new Coordinates(Math.round(2.0f * v1.x / 3.0f), Math.round((s * v1.y - v1.x) / 3.0f));
    }

    @Override
    public Vector3 CenterFromCoords(int cx, int cy) {
        float x = (float) cx * (3.0f / 2.0f);
        float y = (3.0f * cy + x) / (float) Math.sqrt(3);
        return new Vector3(x + 0.5f, y + (float) Math.sqrt(3) / 2.0f, 0.0f);
    }

    @Override
    public Board CreateAutomaton(int size, int floats, BoundaryPolicy boundaryPolicy, Map<Coordinates, ? extends Cell> values) {
        return new HexBoard(size, floats, boundaryPolicy, values);
    }

    static public class TestBoardHandler {
        static public void main(String[] s) {
            testgetCoordinatesHex();
        }

        static public void testgetCoordinatesHex() {
            HexCenterTopology t = new HexCenterTopology();
            Coordinates c;
            c = t.getCoordinates(new Vector3(1.5f, 2.5f * (float) Math.sqrt(3.0), 0.0f));
            assert c.x == 1;
            assert c.y == 2;

            c = t.getCoordinates(new Vector3(3.0f, 2.0f * (float) Math.sqrt(3.0), 0.0f));
            assert c.x == 2;
            assert c.y == 1;

            c = t.getCoordinates(new Vector3(4.5f, 1.5f * (float) Math.sqrt(3.0), 0.0f));
            assert c.x == 3;
            assert c.y == 0;

            c = t.getCoordinates(new Vector3(0.25f, 0.25f * (float) Math.sqrt(3.0), 0.0f));
            assert c.x == 0;
            assert c.y == 0;

            c = t.getCoordinates(new Vector3(2.25f, 1.25f * (float) Math.sqrt(3.0), 0.0f));
            assert c.x == 1;
            assert c.y == 0;
        }
    }
}
