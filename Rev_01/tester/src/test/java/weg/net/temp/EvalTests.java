package weg.net.temp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class EvalTests {
    //@Todo: maybe delete
    /* 
    @Test
    public void eval1() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        Object result = null;
        result = engine.eval("1 + 2;");
        System.out.println(result);
        result = engine.eval("1 + 2; 3 + 4;");
        System.out.println(result);
        result = engine.eval("1 + 2; 3 + 4; var v = 5; v = 6;");
        System.out.println(result);
        result = engine.eval("1 + 2; 3 + 4; var v = 5;");
        System.out.println(result);
        result = engine.eval("print(1 + 2)");
        System.out.println(result);
    }

    @Test
    public void namingMethods() {
        for(int i = 0; i < EvalTests.class.getMethods().length; i++) {
            System.out.println(EvalTests.class.getMethods()[i].getName());
        }
    }

    @Test
    public void invokingMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method invoke = EvalTests.class.getMethod("randomFunction", int.class, int.class);
        Object result = invoke.invoke(invoke, 1, 2);
    }

    private void randomFunction(int a, int b) {
        System.out.println(a+b);
    }
    */
}
