package com.avelov.Center.Files;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import com.avelov.Center.*;
import com.avelov.Center.AutomatonLoader.AutomatonLoader;
import com.avelov.Center.AutomatonLoader.AutomatonLoaderException;

/**
 * Created by mateusz on 13.03.16.
 * Static methods of this class are used to
 * load and save automata, brushes, descriptions, etc.
 */

public class FileManager
{
    private static Comparator<FileHandle> byDateComparator = new Comparator<FileHandle>() {
        @Override
        public int compare(FileHandle o1, FileHandle o2) {
            return o2.lastModified() - o1.lastModified() < 0 ? -1 : 1;
        }
    };

    public static Collection<AutomatonDescription> getPredefinedAutomata() {
        ArrayList<AutomatonDescription> ret = new ArrayList<>();
        FileHandle[] fhs;
        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            fhs = Gdx.files.internal(Gdx.files.getLocalStoragePath() + "predefs/automaton/").list();
        else
            fhs = Gdx.files.internal("predefs/automaton/").list();

        Arrays.sort(fhs, byDateComparator);
        for (FileHandle fh : fhs) {
            if (fh.extension().equals("automaton"))
                ret.add(new AutomatonDescription(fh.nameWithoutExtension(), fh.path()));
        }
        return ret;
    }

    public static Collection<AutomatonDescription> getSavedAutomata()
    {
        ArrayList<AutomatonDescription> ret = new ArrayList<>();
        FileHandle[] fhs;
        fhs = Gdx.files.internal(".aloa/automaton/").list();
        Arrays.sort(fhs, byDateComparator);
        for (FileHandle fh : fhs) {
            if (fh.extension().equals("automaton"))
                ret.add(new AutomatonDescription(fh.nameWithoutExtension(), fh.path()));
        }
        return ret;
    }

    public static AutomatonBlueprint loadPredefinedAutomaton(String filePath)
            throws AutomatonLoaderException {
        FileHandle fh = Gdx.files.internal(filePath);
        return AutomatonLoader.loadFromFileHandle(fh);
    }

    public static AutomatonBlueprint loadSavedAutomaton(String filePath)
            throws AutomatonLoaderException {
        FileHandle fh = Gdx.files.local(filePath);
        return AutomatonLoader.loadFromFileHandle(fh);
    }

    public static void SaveAutomaton(BoardHandler bh, String fileName) {
        System.out.println("saving with name: " + fileName);
        FileHandle fh = Gdx.files.local(".aloa/automaton/" + fileName + ".automaton");
        fh.writeString("", false);
        bh.save(fh);
    }
}
