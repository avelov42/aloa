package com.avelov.Center;

import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Center.Files.Layer;
import com.avelov.Center.Files.TopologyScript;
import com.avelov.Frontend.CellDrawers.Tinters.Tinter;
import com.avelov.Pair;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.ISavable;
import com.avelov.Center.Scripts.Script;
import com.avelov.Backend.Board.Board;
import com.avelov.Backend.Brush.CellFunctor;
import com.avelov.Backend.Cell.Cell;
import com.avelov.Center.TopologyPackage.ITopology;

/**
 * Created by mateusz on 13.03.16.
 * This class is a proxy between front- and backend.
 */
public class BoardHandler implements ISavable {
    private final List<Layer> layers;
    //private ArrayList<BrushState> brushStates;
    private ArrayList<Tinter> colorings;
    private final Board handled;
    //private final float maxValue;
    //private final float minValue;
    private final Script script;
    //private final String saveString;
    private ITopology topology;

    //private List<Paste> pastes;

    public ITopology getTopology() {
        return topology;
    }

    public BoardHandler(AutomatonInfo ab, TopologyScript topology, BoundaryPolicy bp) {
        handled = topology.getTopology().getCenterTopology().CreateAutomaton(
                ab.getBoardSize(), ab.getCellSize(), bp, ab.getValues());
        this.script = topology.getScript();
        this.topology = topology.getTopology();
        //this.minValue = ab.getMinValue();
        //this.maxValue = ab.getMaxValue();
        //this.brushStates = ab.getBrushStates();
        this.colorings = null;//ab.getTinters(); TODO
        //this.saveString = ab.getSaveString();
        this.layers = ab.getLayers();
    }

    public ArrayList<Tinter> getColoring() {
        return colorings;
    }

    public void makeStep() {
        script.run(handled);
    }

    public long getStepsCount() {
        return handled.getGeneration();
    }

    public Iterator<Vector3> getCellsCoords(Vector3 leftTop, Vector3 rightBottom) {
        final Iterator<Cell> iter =
                handled.getSquare(topology.getCenterTopology().getCoordinates(leftTop),
                        topology.getCenterTopology().getCoordinates(rightBottom));
        return new Iterator<Vector3>() {
            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public Vector3 next() {
                Cell c = iter.next();
                return topology.getCenterTopology().CenterFromCoords(c.x, c.y);
            }
        };
    }

    public Iterator<Pair<Cell, Vector3>> getCells(Vector3 leftTop, Vector3 rightBottom) {
        final Iterator<Cell> iter = handled.getRenderIter(
                topology.getCenterTopology().getCoordinates(leftTop),
                topology.getCenterTopology().getCoordinates(rightBottom));
        return new Iterator<Pair<Cell, Vector3>>() {
            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public Pair<Cell, Vector3> next() {
                Cell current = iter.next();
                return new Pair<>(current,
                        topology.getCenterTopology().CenterFromCoords(current.x, current.y));
            }
        };
    }

    public void doBrush(CellFunctor b, Vector3 coords, int n) {
        handled.doBrush(b, topology.getCenterTopology().getCoordinates(coords), n);
    }

    public Iterator<Vector3> getNeighbours(Vector3 center, int size) {
        final Iterator<Cell> iter =
                handled.getNeighbours(
                        topology.getCenterTopology().getCoordinates(center),
                        size);
        return new Iterator<Vector3>() {
            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public Vector3 next() {
                Cell c = iter.next();
                return topology.getCenterTopology().CenterFromCoords(c.x, c.y);
            }
        };
    }

//    public ArrayList<BrushState> getBrushStates() {
//        return brushStates;
//    }

//    public float getMaxValue() {
//        return maxValue;
//    }
//
//    public float getMinValue() {
//        return minValue;
//    }

    @Override
    public void save(FileHandle fileHandle) {
        fileHandle.writeString("topology: " + topology.toString() + "\n", true);
        //fileHandle.writeString("maxValue: " + Float.toString(maxValue) + "\n", true);
        //fileHandle.writeString("minValue: " + Float.toString(minValue) + "\n", true);
        handled.save(fileHandle);
//        fileHandle.writeString(this.saveString, true);
//        for (BrushState bs : brushStates)
//            bs.save(fileHandle);
    }

    public List<Layer> getLayers() {
        return layers;
    }

    //    Collection<Paste> getPasteCollection() {
//        return pastes;
//    }
//
//    void addPaste(Rectangle area, String name) {
//        //TODO Mateusz: what's area?
//    }
//
//    void removePaste(Paste p) {
//        pastes.remove(p);
//    }
//
//    void applyPaste(Paste p, float x, float y) {
//        //TODO Mateusz
//    }
//
}
