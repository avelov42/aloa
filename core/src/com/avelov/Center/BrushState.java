package com.avelov.Center;

import com.badlogic.gdx.files.FileHandle;

import com.avelov.Center.Files.ISavable;
import com.avelov.Backend.Cell.CellValue;

public class BrushState implements ISavable {
    private final String description;
    private final float value;
    private final int layer;

    public BrushState(float value, String description, int layer) {
        this.description = description;
        this.value = value;
        this.layer = layer;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() { return getDescription(); }

    public float getValue() { return value; }

    public int getLayer() {
        return layer;
    }

    @Override
    public void save(FileHandle fileHandle) {
        //fileHandle.writeString("addBrushState: " + cv.toString() + " \"" + getDescription() + "\"" + "\n", true);
    }
}
