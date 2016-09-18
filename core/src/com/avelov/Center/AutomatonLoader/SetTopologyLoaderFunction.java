package com.avelov.Center.AutomatonLoader;

import java.io.BufferedReader;

import com.avelov.Center.Files.AutomatonInfo;
import com.avelov.Center.TopologyPackage.HexTopology;
import com.avelov.Center.TopologyPackage.ITopology;
import com.avelov.Center.TopologyPackage.SquareTopology;

/**
 * Created by mateusz on 21.04.16.
 */
public class SetTopologyLoaderFunction implements AutomatonLoaderFunction {
    @Override
    public void run(String parameter, BufferedReader br, AutomatonInfo ab)
            throws AutomatonLoaderFunctionException {
        ITopology t;
        switch (parameter) {
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
        ab.addTopology(t);
    }
}