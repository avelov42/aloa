package com.avelov.Backend.Board;

/**
 * Klasa opisująca położenie Cella.
 */
public class Coordinates
{
    //public for tests
    public int x;
    public int y;

    public void move(Vector v)
    {
        x += v.dx;
        y += v.dy;
    }

    public void copyCoords(Coordinates orginal)
    {
        this.x = orginal.x;
        this.y = orginal.y;
    }

    public Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        //return "X: " + Integer.toString(x) + " Y: " + Integer.toString(y);
        return Integer.toString(x) + " " + Integer.toString(y);
    }
}
