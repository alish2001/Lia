package supply.exige.lia;

import supply.exige.lia.parser.*;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class Runtime {
    // TODO: OBJECT

    final static Parser[] parsers = new Parser[]{new VariableParser(), new PrintVarParser(), new PrintStringParser(), new PrintIntParser()};

    public static String code = "";
    public static List<Variable> variables = new ArrayList<>();

    public static void run() {
       /* Tokenizer t = new Tokenizer(code);
        while (t.hasNextToken()) {
            System.out.println(t.nextToken());
        }*/

        boolean success = false;
        for (String line : code.split("\n")) {
            line = line.trim();
            Tokenizer tokenizer = new Tokenizer(line);
            for (Parser parser : parsers) {
                if (parser.shouldParse(line)) {
                    parser.parse(tokenizer);
                    success = true; // Successful run
                    break;
                }
            }

            if (!success)
                throw new IllegalArgumentException("Invalid line: " + line); // throw exception if code was invalid
        }

        /*for (Variable v : variables) { // For all variables in the stored variable list for this block
            System.out.println(v.getValue());
        }*/
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
        System.out.println((value == null) ? "<Error> NullPointerException!" : value.toString());
    }
}