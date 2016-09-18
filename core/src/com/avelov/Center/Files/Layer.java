package com.avelov.Center.Files;

import com.avelov.Center.BrushState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 18.09.16.
 */
public class Layer {
    private String name;
    private float min;
    private float max;
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
        defState = new BrushState(new float[]{def}, "Default");
        this.brushStates.add(new BrushState(new float[]{def}, "Default"));
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

    public void setName(String name) {
        this.name = name;
    }

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
