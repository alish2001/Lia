package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.VarType;
import supply.exige.lia.variables.Variable;

public class VariableParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        return line.matches("var[\\s]+[a-zA-Z]+[\\s]+[a-zA-Z][a-zA-Z0-9]*[\\s]*=[\\s]*\"?.*\"?");
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        tokenizer.nextToken(); // Skip the "var" token

        VarType type = VarType.valueOf(tokenizer.nextToken().toString().toUpperCase()); // Retrieve type
        String name = tokenizer.nextToken().toString(); // Retrieve identifier\

        tokenizer.nextToken(); // Skip the "=" token

        Token valueToken = tokenizer.nextToken(); // retrieve variable token
        Object value = null;

        if (Runtime.getVariable(valueToken.toString()) != null) {
            if (type == VarType.STRING // If the type is a string, it can take any
            || (type == VarType.INT && Runtime.getVariable(valueToken.toString()).getType() == VarType.INT)){// If the type is an int, it can only take ints
                value = Runtime.getVariable(valueToken.toString()).getValue();
            } else {
                Runtime.throwException("Error assigning value to variable " + name);
            }
        } else if (valueToken.getType() == TokenType.INTEGER && type == VarType.INT) { // If the token is an integer
            value = Integer.valueOf(valueToken.toString()); // Store the integer value of the token
        } else if (type == VarType.STRING) { // If the token is a string
            value = valueToken.toString(); // Store the string value
        } /// if the var is an undeclared identifier, stay null

        if (Runtime.getVariable(name) == null) {
            Runtime.addVariable(new Variable(name, type, value)); // Store variable in runtime memory
        } else {
            Runtime.getVariable(name).setType(type);
            Runtime.getVariable(name).setValue(value);
        }
    }
}
