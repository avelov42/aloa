package com.avelov.Backend.Accumulator;

/**
 * Created by piotr on 03.04.16.
 */
public class CountingAccumulator extends Accumulator
{
    public void newCell()
    {
        for(int i = 0; i < ints.length; i++)
            ints[i] = 0;
    }

    public void newUpdate()
    {
    }

    public CountingAccumulator(int length)
    {
        this.ints = new int[length];
    }
}
