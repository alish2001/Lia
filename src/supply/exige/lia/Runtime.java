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

    final static Parser[] parsers = new Parser[]{new VariableParser(), new PrintParser()}; // Program parsers
    public static List<Variable> variables = new ArrayList<>(); // Runtime variables

    /**
     * Runs & Compiles code
     *
     * @param doc Code document
     */
    public static void run(Document doc) {
        Compiler.feedCode(doc); // Feeds code into compiler
        Compiler.compile(); // Compiles code for runtime

        printProcessing("<Runtime> Clearing past run...");
        long time = System.currentTimeMillis(); // Store system time before execution
        variables.clear(); // Clear variable list
        Lia.ide.clear(); // Clear output console
        Lia.ide.clearProcessing(); // Clear processing console
        print("Executing...\n");

        boolean success;
        String[] lines = Compiler.code; // Retrieve code
        for (int i = 0; i < lines.length; i++) { // For every line of code
            success = false;
            String line = lines[i].trim(); // Trim spaces
            if (line.isEmpty()) continue; // If line is empty, skip
            Tokenizer tokenizer = new Tokenizer(line); // Tokenize line
            for (Parser parser : parsers) { // Try to locate a parser
                printProcessing("<Runtime> Parsing line #" + i);
                int parseCode = parser.shouldParse(line); // Retrieve parse code for specific line
                if (parseCode != 0) { // If the code is valid
                    parser.parse(tokenizer, parseCode); // Pass line to parser
                    success = true; // Successful run
                    break; // Break loop
                }
            }

            if (!success) // If a parser could not be found
                throwException("Invalid Code [ " + line + " ] on line #" + i); // throw exception if code was invalid
        }

        print("\nProgram Compiled & Executed in " + (System.currentTimeMillis() - time) + "ms"); // Output execution time
    }

    /**
     * Assigns/Reassigns variables
     *
     * @param variable
     */
    public static void addVariable(Variable variable) {
        printProcessing("Assigning variable " + variable.getName());
        if (getVariable(variable.getName()) == null) { // If a variable does not exit
            variables.add(variable);
        } else { // If it exists, overwrite values
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
        // Retrieve JavaScript ScriptEngine
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        int result = 0;
        try {
            for (Variable v : variables) { // Load all existing variables into the JavaScript engine
                if (v.getType() != VarType.INT) continue;
                engine.eval("var " + v.getName() + " = " + v.getValue() + ";"); // Evaluate expression
            }
            result = Double.valueOf(engine.eval(expr).toString()).intValue(); // Save result as an int
        } catch (ScriptException e) { // If the expression was invalid, throw exception
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
        Lia.ide.outputLine((value == null) ? "<EXCEPTION> NullPointerException!" : value.toString()); // Print if the value is not null
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