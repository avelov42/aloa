package com.avelov.Center.TopologyPackage;

/**
 * Created by mateusz on 18.04.16.
 */
public interface ITopology {
    FrontendTopology getFrontendTopology();

    BackendTopology getBackendTopology();

    CenterTopology getCenterTopology();
}
