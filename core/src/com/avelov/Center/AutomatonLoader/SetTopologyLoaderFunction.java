package com.avelov.Center.AutomatonLoader;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.Files.TopologyScript;
import com.avelov.Center.NativeScripts.SquareGameOfLifeNativeScript;
import com.avelov.Center.NativeScripts.SquareHeatNativeScript;
import com.avelov.Center.Scripts.Script;
import com.avelov.Center.Scripts.ScriptParser;
import com.avelov.Center.Scripts.ScriptParserException;
import com.avelov.Center.TopologyPackage.HexTopology;
import com.avelov.Center.TopologyPackage.ITopology;
import com.avelov.Center.TopologyPackage.SquareTopology;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by mateusz on 21.04.16.
 */
public class SetTopologyLoaderFunction implements AutomatonLoaderFunction {

    private static Map<String, Script> nativeAutomata = new TreeMap<>();

    static {
        nativeAutomata.put("game of life", new SquareGameOfLifeNativeScript());
        nativeAutomata.put("heat", new SquareHeatNativeScript());
    }

    @Override
    public void run(AutomatonLoader.Line line, AutomatonInfo ab)
            throws AutomatonLoaderFunctionException {
        if(line.tokens.size() != 2)
            throw new AutomatonLoaderFunctionException("Topology requires 2 parameters.", "Topology");

        ITopology t;
        switch (line.tokens.get(0)) {
            case "square":
                t = new SquareTopology();
                break;
            case "hexagonal":
                t = new HexTopology();
                break;
//                case "triangular":
//                    t = new TriangularTopology();
//                    break;
            default:
                throw new AutomatonLoaderFunctionException("No such topology.", "Topology");
        }

        String scr = line.tokens.get(2);
        String name;
        boolean isPredefined = false;
        boolean isNative = false;

        int firstSpace = scr.indexOf(' ');

        if(firstSpace != -1) {
            if(scr.substring(0, firstSpace).equals("predefined")) {
                name = scr.substring(firstSpace+1);
                isPredefined = true;
            }
            else if (scr.substring(0, firstSpace).equals("native")) {
                name = scr.substring(firstSpace+1);
                isNative = true;
            }
            else name = scr;
        }
        else
            name = scr;

        Script s;

        if(isNative)
            s = nativeAutomata.get(name);
        else
        {
            FileHandle script;
            if(isPredefined)
                script = Gdx.files.internal("predefs/rules/" + name);
            else
                script = Gdx.files.local("aloa/rules/" + name);

            if (!script.exists())
                throw new AutomatonLoaderFunctionException("Script " + name + " doesn't exist", "Rules");

            try {
                ScriptParser parser = new ScriptParser();
                s = parser.Parse(script);
            } catch (ScriptParserException | IOException e) {
                throw new AutomatonLoaderFunctionException("Error while loading script:\n" + e.getMessage(), "rules");
            }
        }

        ab.addTopology(new TopologyScript(t, s, name, isPredefined, isNative));
    }

    @Override
    public String getName() {
        return "topology";
    }
}