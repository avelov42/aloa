package com.avelov.Center.Files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static ArrayList<AutomatonInfo> getPredefinedAutomata() {
        ArrayList<AutomatonInfo> ret = new ArrayList<>();
        FileHandle[] fhs;
        fhs = Gdx.files.internal("predefs/automaton/").list();

        Arrays.sort(fhs, byDateComparator);
        for (FileHandle fh : fhs) {
            if (fh.extension().equals("automaton"))
                ret.add(new AutomatonInfo(fh));
        }
        return ret;
    }

    public static ArrayList<AutomatonInfo> getSavedAutomata()
    {
        ArrayList<AutomatonInfo> ret = new ArrayList<>();
        FileHandle[] fhs;
        fhs = Gdx.files.local("automaton/").list();
        Arrays.sort(fhs, byDateComparator);
        for (FileHandle fh : fhs) {
            if (fh.extension().equals("automaton"))
                ret.add(new AutomatonInfo(fh));
        }
        return ret;
    }

    public static void loadPredefinedAutomaton(AutomatonInfo outAutomaton)
            throws AutomatonLoaderException {
        FileHandle fh = Gdx.files.internal(outAutomaton.getFilePath());
        AutomatonLoader.loadFromFileHandle(fh, outAutomaton);
    }

    public static void loadSavedAutomaton(AutomatonInfo outAutomaton)
            throws AutomatonLoaderException {
        FileHandle fh = Gdx.files.local(outAutomaton.getFilePath());
        AutomatonLoader.loadFromFileHandle(fh, outAutomaton);
    }

    public static void SaveAutomaton(BoardHandler bh, String fileName) {
        System.out.println("saving with name: " + fileName);
        FileHandle fh = Gdx.files.local("automaton/" + fileName + ".automaton");
        fh.writeString("", false);
        bh.save(fh);
    }
}
