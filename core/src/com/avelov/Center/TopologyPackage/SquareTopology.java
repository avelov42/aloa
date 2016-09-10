package com.avelov.Center.TopologyPackage;

/**
 * Created by mateusz on 18.04.16.
 */
public class SquareTopology implements ITopology {
    FrontendTopology ft = new SquareFrontendTopology();
    CenterTopology ct = new SquareCenterTopology();

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
        return "square";
    }
}
