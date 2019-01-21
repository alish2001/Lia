package supply.exige.lia;

import supply.exige.lia.parser.Parser;
import supply.exige.lia.parser.PrintParser;
import supply.exige.lia.parser.VariableParser;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.VarType;
import supply.exige.lia.variables.Variable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * The Lia Runtime.
 * Runs compiled Lia code.
 *
 * @author Ali Shariatmadari
 */

public class Runtime {

    final static Parser[] parsers = new Parser[]{new VariableParser(), new PrintParser()};

    public static List<Variable> variables = new ArrayList<>();

    /**
     * Runs & Compiles code
     *
     * @param doc Code document
     */
    public static void run(Document doc) {
        Compiler.feedCode(doc);
        Compiler.compile();

        printProcessing("<Runtime> Clearing past run...");
        long time = System.currentTimeMillis();
        variables.clear();
        Lia.ide.clear(); // Clear output console
        Lia.ide.clearProcessing(); // Clear processing console
        print("Executing...\n");

        boolean success = false;
        String[] lines = Compiler.code;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            Tokenizer tokenizer = new Tokenizer(line);
            for (Parser parser : parsers) {
                printProcessing("<Runtime> Parsing line #" + i);
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

    /**
     * Assigns/Reassigns variables
     *
     * @param variable
     */
    public static void addVariable(Variable variable) {
        printProcessing("Assigning variable " + variable.getName());
        if (getVariable(variable.getName()) == null) {
            variables.add(variable);
        } else {
            getVariable(variable.getName()).setValue(variable.getValue());
            getVariable(variable.getName()).setType(variable.getType());
        }
    }

    /**
     * Returns variables based on identifier
     *
     * @return {@link Variable}
     */
    public static Variable getVariable(String name) {
        for (Variable v : variables) { // For all variables in the stored variable list for this block
            if (v.getName().equals(name)) return v; // return variable if the identifiers match
        }
        return null; // no matches found
    }

    /**
     * Parses mathematical expressions using the JavaScript engine
     *
     * @param expr
     */
    public static int parseMathExpr(String expr) {
        printProcessing("<Runtime> Parsing " + expr);
        ScriptEngineManager manager = new ScriptEngineManager(); // ez
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        int result = 0;
        try {
            for (Variable v : variables) {
                if (v.getType() != VarType.INT) continue;
                engine.eval("var " + v.getName() + " = " + v.getValue() + ";");
            }
            result = Double.valueOf(engine.eval(expr).toString()).intValue();
        } catch (ScriptException e) {
            throwException("ExpressionException: Error while processing math expression.");
        }
        return result;
    }

    /**
     * Prints to output console
     *
     * @param value
     */
    public static void print(Object value) {
        Lia.ide.outputLine((value == null) ? "<Error> NullPointerException!" : value.toString());
    }

    /**
     * Throws exceptions to output console
     *
     * @param e
     */
    public static void throwException(String e) {
        print("<EXCEPTION> " + e);
    }

    /**
     * Prints to backend processing console
     *
     * @param e
     */
    public static void printProcessing(String str) {
        Lia.ide.outputProcessing(str);
    }
}