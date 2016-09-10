package com.avelov.Backend.Accumulator;

/**
 * Pomocnicza klasa dla updatera.
 */
public abstract class Accumulator
{
    public int[] ints;
    public float[] floats;

    public abstract void newCell();

    public abstract void newUpdate();
}
