package com.avelov.Backend.Accumulator;

/**
 * Created by piotr on 06.06.16.
 */
public class UnifiedAccumulator extends Accumulator
{
    public void newCell()
    {
        for(int i = 0; i < ints.length; i++)
            ints[i] = 0;
        for(int i = 0; i < floats.length; i++)
            floats[i] = 0;
    }

    public void newUpdate()
    {
    }

    public UnifiedAccumulator(int intLength, int floatLength)
    {
        this.ints = new int[intLength];
        this.floats = new float[floatLength];
    }
}
