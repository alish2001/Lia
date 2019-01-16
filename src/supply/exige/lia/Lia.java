package supply.exige.lia;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Lia {


    public static IDE ide;

    public static void main(String[] args) throws ScriptException {
        Runtime.code = "var int str=\"hello\"\n" +
                        "print(str)";

        Runtime.run();
        ScriptEngineManager m = new ScriptEngineManager(); // ez
        ScriptEngine engine = m.getEngineByName("JavaScript");
        String evalu = "(2+2)*5";
        System.out.println(engine.eval(evalu));
        ide = new IDE();
    }
    //https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
}