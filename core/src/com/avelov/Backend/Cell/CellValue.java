package com.avelov.Backend.Cell;

/**
 * Created by mateusz on 24.07.16.
 */
public class CellValue extends Cell
{
    private float[] values;

    public CellValue(float[] values)
    {
        super(0, 0, values.length, null);
        this.values = values;
    }

    public void setValue(int index, float value)
    {
        values[index] = value;
    }

    public float[] getValue()
    {
        return values;
    }

    @Override
    public float getValue(int index)
    {
        return values[index];
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < elements; i++) {
            sb.append(values[i]);
            sb.append(' ');
        }
        return sb.toString();
    }

}
