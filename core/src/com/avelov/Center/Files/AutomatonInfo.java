package com.avelov.Center.Files;

import com.avelov.Frontend.CellDrawers.Tinters.Tinter;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.avelov.Center.BrushState;
import com.avelov.Center.Scripts.Script;
import com.avelov.Center.TopologyPackage.ITopology;

import com.avelov.Backend.Board.Coordinates;
import com.avelov.Backend.Boundary.BoundaryPolicy;
import com.avelov.Backend.Cell.Cell;
import com.avelov.Backend.Cell.CellValue;

/**
 * Created by mateusz on 13.03.16.
 * Instances of this class should hold information
 * required to build new automaton. Used by @AutomataFactory.
 */

public final class AutomatonInfo implements ISavable {
    private boolean                     areRulesPredefined  = false;
    private String                      automatonFilePath;
    private String                      automatonName;
    private BoundaryPolicy              boundaryPolicy;
    private int                         boardSize           = -1;
    private ArrayList<BrushState>       brushStates         = new ArrayList<>();
    private int                         cellSize            = -1;
    private ArrayList<TinterDetails>    colorings           = new ArrayList<>();
    private String                      description         = null;
    private String                      imagePath           = null;
    private boolean                     isImagePredefined   = false;
    private int                         maxValue;
    private int                         minValue;
    private String                      rulesPath;
    private Script                      script;
    private ITopology                   topology;
    private Map<Coordinates, CellValue> values              = new HashMap<>();

    public AutomatonInfo(String name, String filePath) {
        this.automatonFilePath = filePath;
        this.automatonName = name;
    }

    public boolean isComplete() {
        return topology != null &&
                script != null &&
                minValue <= maxValue &&
                colorings.size() != 0 &&
                boardSize != -1 &&
                cellSize != -1 &&
                boundaryPolicy != null;
    }

    public void setAreRulesPredefined(boolean areRulesPredefined) {
        this.areRulesPredefined = areRulesPredefined;
    }

    public int getBoardSize() {
        return boardSize;
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public BoundaryPolicy getBoundaryPolicy() {
        return boundaryPolicy;
    }
    public void setBoundaryPolicy(BoundaryPolicy boundaryPolicy) {
        this.boundaryPolicy = boundaryPolicy;
    }

    public ArrayList<BrushState> getBrushStates() {
        return brushStates;
    }
    public void addBrushState(BrushState b) {
        brushStates.add(b);
    }

    public int getCellSize() {
        return cellSize;
    }
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void addColoring(Tinter coloring, String path, boolean isPredefined) {
        this.colorings.add(new TinterDetails(coloring, path, isPredefined));
    }
    public ArrayList<TinterDetails> getColoring() {
        return colorings;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }

    public boolean isImagePredefined() {
        return isImagePredefined;
    }
    public void setIsImagePredefined(boolean isImagePredefined) {
        this.isImagePredefined = isImagePredefined;
    }

    public int getMaxValue() {
        return maxValue;
    }
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setRulesPath(String rulesPath) {
        this.rulesPath = rulesPath;
    }

    public Script getScript() {
        return script;
    }
    public void setScript(Script script) {
        this.script = script;
    }

    public ITopology getTopology() {
        return topology;
    }
    public void setTopology(ITopology topology) {
        this.topology = topology;
    }

    public Map<Coordinates, ? extends Cell> getValues() {
        return values;
    }
    public void setValue(int x, int y, int index, float value) {
        Coordinates coords = new Coordinates(x, y);
        CellValue c = values.get(coords);
        if(c == null)
        {
            float[] v = new float[cellSize];
            v[index] = value;
            values.put(new Coordinates(x, y), new CellValue(v));
        }
        else
            c.setValue(index, value);
    }
    public void setValue(int x, int y, float[] values) {
        this.values.put(new Coordinates(x, y), new CellValue(values));
    }

    public String getSaveString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("rules: ");
        sb.append(areRulesPredefined ? "predefined " : "");
        sb.append(rulesPath);
        sb.append("\n");
        if(description != null)
        {
            sb.append("description:\n");
            sb.append(description);
            sb.append("\ndescriptionEnd\n");
        }
        if(imagePath != null)
        {
            sb.append("image: ");
            sb.append(isImagePredefined ? "predefined " : "");
            sb.append(imagePath);
            sb.append("\n");
        }
        sb.append("coloring: ");
        for (int i = 0; i < colorings.size(); i++) {
            TinterDetails td = colorings.get(i);
            sb.append(td.isPredefined ? "predefined " : "");
            sb.append(td.filePath);
            if (i != colorings.size() - 1)
                sb.append(", ");
            else
                sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void save(FileHandle fh) {
        fh.writeString("topology: " + topology.toString() + "\n", true);
        fh.writeString("boardSize: " + Integer.toString(boardSize) + "\n", true);
        fh.writeString("maxValue: " + Integer.toString(maxValue) + "\n", true);
        fh.writeString("minValue: " + Integer.toString(minValue) + "\n", true);
        fh.writeString("boundary: " + boundaryPolicy.toString() + "\n", true);
        fh.writeString("rules: " + (areRulesPredefined ? "predefined " : "") + rulesPath + "\n", true);
        fh.writeString("coloring: ", true);
        for (int i = 0; i < colorings.size(); i++) {
            TinterDetails td = colorings.get(i);
            fh.writeString((td.isPredefined ? "predefined " : "") + td.filePath, true);
            if (i != colorings.size() - 1)
                fh.writeString(", ", true);
            else
                fh.writeString("\n", true);
        }
    }

    public ArrayList<Tinter> getTinters() {
        ArrayList<Tinter> ret = new ArrayList<>();
        for(TinterDetails td : colorings)
            ret.add(td.tinter);
        return ret;
    }

    //@todo jak ryba dostanie raka to pokazać lekarzowi.
    public class TinterDetails {
        public Tinter tinter;
        public String name;
        public String filePath;
        public boolean isPredefined;
        public TinterDetails(Tinter tinter, String filePath, boolean isPredefined) {
            this.tinter = tinter;
            this.filePath = filePath;
            this.isPredefined = isPredefined;

            String[] splitted = filePath.split("/");
            this.name = splitted[splitted.length-1].split("[.]")[0];
        }
        public String toString()
        {
            return name;
        }
    }

    public String getName() {
        return automatonName;
    }

    public String getFilePath()
    {
        return automatonFilePath;
    }
}
