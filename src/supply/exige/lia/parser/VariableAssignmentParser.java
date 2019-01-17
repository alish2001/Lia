package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.VarType;

public class VariableAssignmentParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        return line.matches("[\\s]*[a-zA-Z][a-zA-Z0-9]*[\\s]*=[\\s]*\"?.*\"?");
        // Add appending?
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        String name = tokenizer.nextToken().toString(); // Retrieve identifier\

        if (Runtime.getVariable(name) == null) { // If the variable has not been declared yet
            Runtime.throwException("Variable [ " + name + " ] has not been declared.");
            return;
        }

        tokenizer.nextToken(); // Skip the "=" token
        VarType type = Runtime.getVariable(name).getType();

        Token valueToken = tokenizer.nextToken(); // retrieve variable token
        Object value = null;

        if (Runtime.getVariable(valueToken.toString()) != null) {
            if (type == VarType.STRING // If the type is a string, it can take any
                    || (type == VarType.INT && Runtime.getVariable(valueToken.toString()).getType() == VarType.INT)) {// If the type is an int, it can only take ints
                value = Runtime.getVariable(valueToken.toString()).getValue();
            } else {
                Runtime.throwException("Error assigning value to variable " + name);
            }
        } else if (valueToken.getType() == TokenType.INTEGER && type == VarType.INT) { // If the token is an integer
            value = Integer.valueOf(valueToken.toString()); // Store the integer value of the token
        } else if (type == VarType.STRING) { // If the token is a string
            value = valueToken.toString(); // Store the string value
        }

        Runtime.getVariable(name).setValue(value); // Update variable value
    }
}
