package com.avelov.Center.Files;

import com.avelov.Center.BrushState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 18.09.16.
 */
public class Layer {
    static private int layerId = 0;
    private final String name;
    private final float min;
    private final float max;
    private final int layerNumber = layerId++;
    private BrushState defState;
    private float def;
    private List<AutomatonInfo.TinterDetails> tinters;
    private ArrayList<BrushState> brushStates;

    public Layer(String name, float min, float max, float def, List<AutomatonInfo.TinterDetails> tinters) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.tinters = tinters;
        this.brushStates = new ArrayList<>();
        this.def = def;
        defState = new BrushState(def, "Default", layerNumber);
        this.brushStates.add(new BrushState(def, "Default", layerNumber));
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

    public float getMin() {
        return min;
    }

    public String getName() {
        return name;
    }

    public int getLayerNumber()
    {
        return layerNumber;
    }

    //public void setName(String name) {
    //    this.name = name;
    //}

    public List<BrushState> getBrushStates() {
        return brushStates;
    }

    public void addBrushState(BrushState brushState) {

        if(brushState.getValue() == def)
            defState = brushState;

        boolean found = false;
        for(int i = 0; i < brushStates.size(); i++) {
            if(brushStates.get(i).getValue() >= brushState.getValue()) {
                if(brushStates.get(i).getValue() == brushState.getValue())
                    brushStates.remove(i);

                brushStates.add(i, brushState);
                found = true;
                break;
            }
        }
        if(!found)
            brushStates.add(brushStates.size(), brushState);
    }

    public BrushState getDefState() {
        return defState;
    }
}
