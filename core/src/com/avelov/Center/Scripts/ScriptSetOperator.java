package com.avelov.Center.Scripts;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mateusz on 16.07.16.
 */
public enum ScriptSetOperator {
    ASSIGN,
    ASSIGN_ADD,
    ASSIGN_SUBTRACT,
    ASSIGN_MULTIPLY,
    ASSIGN_DIVIDE,
    ASSIGN_MODULO;

    private static Map<String, ScriptSetOperator> ops = new TreeMap<>();

    static {
        ops.put("=", ASSIGN);
        ops.put("+=", ASSIGN_ADD);
        ops.put("-=", ASSIGN_SUBTRACT);
        ops.put("*=", ASSIGN_MULTIPLY);
        ops.put("/=", ASSIGN_DIVIDE);
        ops.put("%=", ASSIGN_MODULO);
    }

    public static ScriptSetOperator GetOp(String s) throws ScriptParserException {
        ScriptSetOperator sso = ops.get(s);
        if(sso == null)
            return null;
        return sso;
    }
}
