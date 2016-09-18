//package com.avelov.Center.AutomatonLoader;
//
//import com.avelov.Frontend.CellDrawers.Tinters.Tinter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//
//import java.io.BufferedReader;
//
//import com.avelov.Center.Files.AutomatonInfo;
//import com.avelov.Center.Scripts.ColoringScriptParser;
//import com.avelov.Center.Scripts.ScriptException;
//
///**
// * Created by mateusz on 09.06.16.
// */
//public class SetColoringLoaderFunction implements AutomatonLoaderFunction {
//    @Override
//    public void run(String parameter, BufferedReader br, AutomatonInfo ab) throws AutomatonLoaderFunctionException {
//        String[] params = parameter.split(",");
//
//        for (String s : params) {
//            s = s.trim();
//            String[] p = s.split("\\s+");
//            if (p.length == 2 && p[0].toLowerCase().equals("predefined")) {
//                FileHandle coloring = Gdx.files.internal("predefs/coloring/" + p[1]);
//                if (!coloring.exists())
//                    throw new AutomatonLoaderFunctionException("Coloring " + p[1] + " doesn't exist", "Coloring");
//                try {
//                    //@todo: coloring may not be defined for all required values
//                    Tinter t = ColoringScriptParser.parse(coloring);
//                    ab.addColoring(t, p[1], true);
//                } catch (ScriptException e) {
//                    throw new AutomatonLoaderFunctionException(
//                            "Error in predefined script (WTF?) \" " + p[1] + " \": " + e.getMessage() + " ", "Coloring");
//                }
//            } else if (p.length == 1) //load Coloring saved by user
//                throw new UnsupportedOperationException();
//                //TODO implement
//            else
//                throw new AutomatonLoaderFunctionException("Syntax error, use coloring: {<name>|predefined <name>}, ...", "Coloring");
//        }
//
//
//
//    }
//}
