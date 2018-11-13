package com.wan37.util;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

public class ScriptEngineUtil {

    private static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    public static double eval(String script, Map<String, Object> bindings) {
        Bindings b = jse.createBindings();
        b.putAll(bindings);

        Object result = eval(script, b);
        return result == null ? 0 : Double.parseDouble(result.toString());
    }

    private static Object eval(String script, Bindings bindings) {
        try {
            return jse.eval(script, bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }
}
