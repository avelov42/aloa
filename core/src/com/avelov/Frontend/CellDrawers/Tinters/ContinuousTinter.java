package com.avelov.Frontend.CellDrawers.Tinters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

import java.util.Arrays;

import com.avelov.Backend.Cell.Cell;

/**
 * Created by loudrainbow on 24.04.16.
 */
public class ContinuousTinter implements Tinter
{
    private Color[] colors;
    private float[] values;
    private Interpolation[] interpolations;

    //Java, seriously?
    private boolean isSortedAscending(float[] values)
    {
        for(int i = 1; i < values.length; i++)
            if(values[i-1] > values[i])
                return false;
        return true;
    }

    public ContinuousTinter(Color[] colors, float[] values,  Interpolation[] interpolations)
    {
        this.colors = colors;
        this.values = values;
        this.interpolations = interpolations;
        if(!isSortedAscending(values))
            throw new IllegalArgumentException("Values must be sorted");
        if(colors.length != values.length)
            throw new IllegalArgumentException("Colors and values arrays differ in length");
        if(values.length != interpolations.length + 1)
            throw new IllegalArgumentException("Invalid number of interpolations: " + interpolations.length + " instead of " + (values.length-1));
    }
    @Override
    public Color getTint(Cell cell)
    {
        float cellValue = cell.getValue(0);
        int position = Arrays.binarySearch(values, cellValue);
        if(position >= 0) return colors[position];
        else if(position == -1) return colors[0];
        else if(position == -colors.length-1) return colors[values.length-1];
        else
        {
            position = (position + 2) * (-1); //position of left color
            Color returnColor = colors[position].cpy();
            float t = interpolations[position].apply((cellValue - values[position]) / (values[position+1]-values[position]));
            return returnColor.lerp(colors[position+1], t);
        }
    }
}
