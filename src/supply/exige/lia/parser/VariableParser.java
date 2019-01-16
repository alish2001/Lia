package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.Tokenizer.Token;
import supply.exige.lia.Tokenizer.TokenType;
import supply.exige.lia.Tokenizer.Tokenizer;
import supply.exige.lia.variables.VarType;
import supply.exige.lia.variables.Variable;

public class VariableParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        return line.matches("var[\\s]+[a-zA-Z]+[\\s]+[a-zA-Z]+[\\s]*=[\\s]*\"?.*\"?");
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        tokenizer.nextToken(); // Skip the "var" token

        VarType type = VarType.valueOf(tokenizer.nextToken().toString().toUpperCase()); // Retrieve type
        String name = tokenizer.nextToken().toString(); // Retrieve identifier

        tokenizer.nextToken(); // Skip the "=" token

        Token token = tokenizer.nextToken(); // retrieve variable token
        Object value = null;
        if (token.getType() == TokenType.INTEGER && type == VarType.INT){ // If the token is an integer
            value = Integer.valueOf(token.toString()); // Store the integer value of the token
        } else if (token.getType() == TokenType.STRING && type == VarType.STRING){ // If the token is a string
            value = token.toString(); // Store the string value
        } else { // if the var is an undeclared identifier
          //  value = superBlock.getVariable(token.toString()).getValue(); // check the superblock for the value
        }

        Runtime.addVariable(new Variable(name, type, value)); // Store variable in runtime memory
    }
}
