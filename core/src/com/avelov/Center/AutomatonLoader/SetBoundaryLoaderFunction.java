//package com.avelov.Center.AutomatonLoader;
//
//import java.io.BufferedReader;
//
//import com.avelov.Center.Files.AutomatonInfo;
//import com.avelov.Backend.Boundary.BoundaryConstant;
//import com.avelov.Backend.Boundary.BoundaryMirror;
//import com.avelov.Backend.Boundary.BoundaryPolicy;
//import com.avelov.Backend.Boundary.BoundarySame;
//import com.avelov.Backend.Boundary.BoundaryWrap;
//
///**
// * Created by mateusz on 28.07.16.
// */
//public class SetBoundaryLoaderFunction implements AutomatonLoaderFunction {
//    @Override
//    public void run(String parameter, BufferedReader br, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
//        String param = parameter.trim().toLowerCase();
//        if(param.equals(""))
//            throw new AutomatonLoaderFunctionException("No boundary provided", "Boundary");
//
//        String[] params = param.split("\\s+");
//        ab.setBoundaryPolicy(getPolicy(params));
//    }
//
//    private BoundaryPolicy getPolicy(String[] params) throws AutomatonLoaderFunctionException {
//        switch (params[0])
//        {
//            case "same":        return new BoundarySame();
//            case "constant":    return ParseConstantPolicy(params);
//            case "mirror":      return new BoundaryMirror();
//            case "wrap":        return new BoundaryWrap();
//        }
//        throw new AutomatonLoaderFunctionException("Boundary policy not supported", "boundary");
//    }
//
//    private BoundaryPolicy ParseConstantPolicy(String[] params) throws AutomatonLoaderFunctionException {
//        int len = params.length - 1;
//        float[] values = new float[len];
//        for(int i = 0; i < len; i++)
//        {
//            try
//            {
//                float f = Float.parseFloat(params[i+1]);
//                values[i] = f;
//            }
//            catch (NumberFormatException e)
//            {
//                throw new AutomatonLoaderFunctionException("Constant boundary value at position " + Integer.toString(i) + " is incorrect.", "boundary");
//            }
//        }
//        return new BoundaryConstant(values);
//    }
//}
