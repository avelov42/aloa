package com.avelov.Center.Files;

import com.avelov.Center.BrushState;

import java.util.List;

/**
 * Created by mateusz on 18.09.16.
 */
public class Layer {
    private String name;
    private float min;
    private float max;
    private float def;
    private List<AutomatonInfo.TinterDetails> tinters;
    private List<BrushState> brushStates;

    public Layer(String name, float min, float max, float def, List<AutomatonInfo.TinterDetails> tinters) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.def = def;
        this.tinters = tinters;
    }

    public List<AutomatonInfo.TinterDetails> getTinters() {
        return tinters;
    }

    public void setTinters(List<AutomatonInfo.TinterDetails> tinters) {
        this.tinters = tinters;
    }

    public float getDefault() {
        return def;
    }

    public void setDefault(float def) {
        this.def = def;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BrushState> getBrushStates() {
        return brushStates;
    }

    public void setBrushStates(List<BrushState> brushStates) {
        this.brushStates = brushStates;
    }
}
