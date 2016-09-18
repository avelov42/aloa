package com.avelov.Center.Files;

import com.avelov.Center.Scripts.Script;
import com.avelov.Center.TopologyPackage.ITopology;
import java.lang.String;

/**
 * Created by mateusz on 18.09.16.
 */
public class TopologyScript {
    private ITopology topology;
    private String name;
    private Script script;
    private boolean isScriptPredefined;
    private boolean isScriptNative;

    public TopologyScript(ITopology topology, Script script, String name, boolean isScriptPredefined, boolean isScriptNative) {
        this.topology = topology;
        this.script = script;
        this.isScriptPredefined = isScriptPredefined;
        this.isScriptNative = isScriptNative;
        this.name = name;
    }

    public ITopology getTopology() {
        return topology;
    }

    public void setTopology(ITopology topology) {
        this.topology = topology;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public boolean isScriptPredefined() {
        return isScriptPredefined;
    }

    public void setScriptPredefined(boolean scriptPredefined) {
        isScriptPredefined = scriptPredefined;
    }

    public boolean isScriptNative() {
        return isScriptNative;
    }

    public void setScriptNative(boolean scriptNative) {
        isScriptNative = scriptNative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
