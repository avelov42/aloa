//package com.avelov.Center.AutomatonLoader;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Map;
//import java.util.TreeMap;
//
//import com.avelov.Center.Files.AutomatonInfo;
//import com.avelov.Center.NativeScripts.SquareGameOfLifeNativeScript;
//import com.avelov.Center.NativeScripts.SquareHeatNativeScript;
//import com.avelov.Center.Scripts.Script;
//import com.avelov.Center.Scripts.ScriptParser;
//import com.avelov.Center.Scripts.ScriptParserException;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
///**
// * Created by mateusz on 21.04.16.
// */
//public class SetRulesLoaderFunction implements AutomatonLoaderFunction {
//
//    private static Map<String, Script> nativeAutomata = new TreeMap<>();
//
//    static {
//        nativeAutomata.put("gameoflife", new SquareGameOfLifeNativeScript());
//        nativeAutomata.put("heat", new SquareHeatNativeScript());
//    }
//
//    @Override
//    public void run(String parameter, BufferedReader br, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
//        String[] params = parameter.split("\\s+");
//        if (params.length == 2 && params[0].toLowerCase().equals("predefined")) {
//
//        }
//        else if (params.length == 2 &&params[0].toLowerCase().equals("native")) {
//            Script s = nativeAutomata.get(params[1]);
//            if(s == null)
//                throw new AutomatonLoaderFunctionException("Cannot find native automaton " + params[1], "rules");
//            ab.setScript(s);
//        }
//        else if (params.length == 1) //load rules saved by user
//            throw new NotImplementedException();
//            //TODO implement
//        else
//            throw new AutomatonLoaderFunctionException("Syntax error, use rules: {<name>|predefined <name>}", "Rules");
//    }
//
//    @Override
//    public void run(AutomatonLoader.Line line, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
//
//    }
//
//    @Override
//    public String getName() {
//        return "rules";
//    }
//}
