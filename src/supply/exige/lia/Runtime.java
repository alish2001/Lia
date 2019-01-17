package supply.exige.lia;

import supply.exige.lia.parser.*;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class Runtime {

    final static Parser[] parsers = new Parser[]{new VariableParser(), new VariableAssignmentParser(), new PrintVarParser(), new PrintStringParser(), new PrintIntParser()};

    public static List<Variable> variables = new ArrayList<>();

    public static void run(String code) {
        long time = System.currentTimeMillis();
        variables.clear();
        Lia.ide.clear();
        print("Executing...\n");

        boolean success = false;
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            Tokenizer tokenizer = new Tokenizer(line);
            for (Parser parser : parsers) {
                if (parser.shouldParse(line)) {
                    parser.parse(tokenizer);
                    success = true; // Successful run
                    break;
                }
            }

            if (!success)
                throwException("Invalid Code [ " + line + " ] on line #" + i); // throw exception if code was invalid
        }

        print("\nProgram Executed in " + (System.currentTimeMillis() - time) + "ms");
    }

    public static void addVariable(Variable variable) {
        variables.add(variable);
    }

    public static Variable getVariable(String name) {

        for (Variable v : variables) { // For all variables in the stored variable list for this block
            if (v.getName().equals(name)) return v; // return variable if the identifiers match
        }
        return null; // no matches found
    }

    public static void setVariableValue(String identifier, Object value) {
        for (int i = 0; i < variables.size(); i++) {
            if (!variables.get(i).getName().equals(identifier)) continue; // If the names do not match, skip
            variables.get(i).setValue(value); // Set the new value of found variable
        }
    }

    public static void print(Object value) {
        Lia.ide.outputLine((value == null) ? "<Error> NullPointerException!" : value.toString());
    }

    public static void throwException(String e){
        print("<EXCEPTION> " + e);
    }
}