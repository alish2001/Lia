package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.VarType;
import supply.exige.lia.variables.Variable;

public class VariableParser extends Parser {

    @Override
    public int shouldParse(String line) {
        int code = 0;
        if (line.matches("var[\\s]+[a-zA-Z]+[\\s]+[a-zA-Z_][a-zA-Z0-9_]*[\\s]*=?[\\s]*\"?.*\"?")) {
            code = 1;
        } else if (line.matches("[\\s]*[a-zA-Z][a-zA-Z0-9]*[\\s]*=[\\s]*\"?.*\"?")) {
            code = 2;
        }
        return code;
    }

    @Override
    public void parse(Tokenizer tokenizer, int code) {
        VarType type = null;
        if (code == 1) {
            tokenizer.nextToken(); // Skip the "var" token
            type = VarType.valueOf(tokenizer.nextToken().toString().toUpperCase()); // Retrieve type
        }

        String name = tokenizer.nextToken().toString(); // Retrieve identifier

        if (code == 2 && Runtime.getVariable(name) == null) { // If the variable has not been declared yet
            Runtime.throwException("NullPointerException: Variable [ " + name + " ] has not been declared.");
            return;
        } else if (code == 2) {
            type = Runtime.getVariable(name).getType();
        }
        if (tokenizer.hasNextToken()) { // If there is a next token
            tokenizer.nextToken(); // Skip the "=" token
        } else { // The variable is not being instantiated
            assignVariable(name, type, new Token("", TokenType.EMPTY), false);
            return;
        }

        do {
            Token valueToken = tokenizer.nextToken(); // retrieve variable token
            boolean append = false;
            if (valueToken.getType() == TokenType.APPEND && tokenizer.hasNextToken()) {
                valueToken = tokenizer.nextToken(); // If the value is the append "." skip  */
                append = true;
            }
            assignVariable(name, type, valueToken, append);
        } while (tokenizer.hasNextToken());

    }

    private void assignVariable(String name, VarType type, Token valueToken, boolean append) {
        Object value = null; // if the var is an undeclared identifier, stay null
        if (valueToken.getType() == TokenType.IDENTIFIER) {
            if (Runtime.getVariable(valueToken.toString()) != null) {
                if (type == VarType.STRING // If the type is a string, it can take an int or string
                        || (type == VarType.INT && Runtime.getVariable(valueToken.toString()).getType() == VarType.INT)) { // If the type is an int, it can only take ints
                    value = Runtime.getVariable(valueToken.toString()).getValue();
                } else {
                    Runtime.throwException("Type Mismatch: Error assigning value to variable " + name);
                }
            } else {
                Runtime.throwException("NullPointerException: Variable " + valueToken.toString() + " does not exist or has not been declared.");
            }
        } else if (type == VarType.INT) { // If the token & type are integer
            if (valueToken.getType() == TokenType.INTEGER) {
                value = Integer.valueOf(valueToken.toString()); // Store the integer value of the token
            } else if (valueToken.getType() == TokenType.MATH_EXPRESSION) {
                value = Runtime.parseMathExpr(valueToken.toString());
            }
        } else if (type == VarType.STRING) { // If the token is a string
            if (valueToken.getType() == TokenType.STRING) {
                value = valueToken.toString(); // Store the string value
            } else if (valueToken.getType() == TokenType.MATH_EXPRESSION) {
                value = Runtime.parseMathExpr(valueToken.toString());
            }
        }

        if (Runtime.getVariable(name) != null && type == VarType.STRING && append) {
            value = Runtime.getVariable(name).getValue().toString() + value;
        }
        Runtime.addVariable(new Variable(name, type, value)); // Store variable in runtime memory
    }
}