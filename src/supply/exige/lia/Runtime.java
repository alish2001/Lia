package supply.exige.lia;

import supply.exige.lia.parser.*;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.VarType;
import supply.exige.lia.variables.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class Runtime {

    final static Parser[] parsers = new Parser[]{new VariableParser(), new PrintParser()};

    public static List<Variable> variables = new ArrayList<>();

    public static void run(Document doc) {
        /*Tokenizer t = new Tokenizer(code);
        while (t.hasNextToken()) {
            System.out.println(t.nextToken());
        }*/

        Compiler.feedCode(doc);
        Compiler.compile();

        long time = System.currentTimeMillis();
        variables.clear();
        Lia.ide.clear();
        print("Executing...\n");

        boolean success = false;
        String[] lines = Compiler.code;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            Tokenizer tokenizer = new Tokenizer(line);
            for (Parser parser : parsers) {
                int parseCode = parser.shouldParse(line);
                if (parseCode != 0) {
                    parser.parse(tokenizer, parseCode);
                    success = true; // Successful run
                    break;
                }
            }

            if (!success)
                throwException("Invalid Code [ " + line + " ] on line #" + i); // throw exception if code was invalid
        }

        print("\nProgram Compiled & Executed in " + (System.currentTimeMillis() - time) + "ms");
    }

    public static void addVariable(Variable variable) {
        if (getVariable(variable.getName()) == null) {
            variables.add(variable);
        } else {
            getVariable(variable.getName()).setValue(variable.getValue());
            getVariable(variable.getName()).setType(variable.getType());
        }
    }

    public static Variable getVariable(String name) {

        for (Variable v : variables) { // For all variables in the stored variable list for this block
            if (v.getName().equals(name)) return v; // return variable if the identifiers match
        }
        return null; // no matches found
    }

    public static void print(Object value) {
        Lia.ide.outputLine((value == null) ? "<Error> NullPointerException!" : value.toString());
    }

    public static void throwException(String e) {
        print("<EXCEPTION> " + e);
    }

    public static int parseMathExpr(String expr) {
        System.out.println("parsing " + expr);
        ScriptEngineManager manager = new ScriptEngineManager(); // ez
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        int result = 0;
        try {
            for (Variable v : variables) {
                if (v.getType() != VarType.INT) continue;
                engine.eval("var " + v.getName() + " = " + v.getValue() + ";");
                System.out.println("var " + v.getName() + " = " + v.getValue() + ";");
            }
            result = Double.valueOf(engine.eval(expr).toString()).intValue();
        } catch (ScriptException e) {
            throwException("ExpressionException: Error while processing math expression.");
        }
        return result;
    }
}