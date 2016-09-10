package com.avelov.Center.Files;

import com.badlogic.gdx.files.FileHandle;

/**
 * Created by mateusz on 10.06.16.
 * Used to save objects to file
 */
public interface ISavable {
    void save(FileHandle fileHandle);
}
