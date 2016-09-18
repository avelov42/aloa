package com.avelov.Center;

import com.badlogic.gdx.files.FileHandle;

import com.avelov.Center.Files.ISavable;
import com.avelov.Backend.Cell.CellValue;

/**
 * Created by adamek on 4/23/16.
 */

public class BrushState implements ISavable {
    private String description;
    private CellValue cv;
    private float value;

    public BrushState(float[] value, String description) {
        this.description = description;
        cv = new CellValue(value);
    }

    public String getDescription() {
        return description;
    }

    public CellValue getCell() {
        return cv;
    }

    public float getValue() { return value; }

    @Override
    public void save(FileHandle fileHandle) {
        fileHandle.writeString("addBrushState: " + cv.toString() + " \"" + getDescription() + "\"" + "\n", true);
    }
}
