//package com.avelov.Center.AutomatonLoader;
//
//import java.io.BufferedReader;
//import java.util.Map;
//import java.util.TreeMap;
//
//import com.avelov.Center.Files.AutomatonInfo;
//import com.avelov.Center.Scripts.Script;
//import com.avelov.Center.NativeScripts.SquareGameOfLifeNativeScript;
//import com.avelov.Center.NativeScripts.SquareHeatNativeScript;
//
///**
// * Created by mateusz on 21.07.16.
// */
//public class SetAutomatonLoaderFunction implements AutomatonLoaderFunction {
//
//    private static Map<String, Script> nativeAutomata = new TreeMap<>();
//
//    static {
//        nativeAutomata.put("game of life", new SquareGameOfLifeNativeScript());
//        nativeAutomata.put("heat", new SquareHeatNativeScript());
//    }
//
//    @Override
//    public void run(AutomatonLoader.Line parameters, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
//        if(parameters.tokens.size() != 1)
//            throw new AutomatonLoaderFunctionException("Wrong number of arguments", "SetAutomatonLoaderFunction");
//        String s = parameters.tokens.get(0);
//        Script u = nativeAutomata.get(s);
//        if(u == null)
//            throw new AutomatonLoaderFunctionException("No such native automaton: " + s,
//                    "SetAutomatonLoaderFunction");
//        ab.setScript(u);
//    }
//
//    @Override
//    public String getName() {
//        return "automaton";
//    }
//}
