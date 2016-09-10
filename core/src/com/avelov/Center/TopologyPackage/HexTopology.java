package com.avelov.Center.TopologyPackage;

/**
 * Created by mateusz on 18.04.16.
 */
public class HexTopology implements ITopology {
    FrontendTopology ft = new HexFrontendTopology();
    CenterTopology ct = new HexCenterTopology();

    @Override
    public FrontendTopology getFrontendTopology() {
        return ft;
    }

    @Override
    public BackendTopology getBackendTopology() {
        return null;
    }

    @Override
    public CenterTopology getCenterTopology() {
        return ct;
    }

    @Override
    public String toString() {
        return "hexagonal";
    }
}
