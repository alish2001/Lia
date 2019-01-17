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
		return line.matches("var[\\s]+[a-zA-Z]+[\\s]+[a-zA-Z_][a-zA-Z0-9_]*[\\s]*=[\\s]*\"?.*\"?");
	}

	@Override
	public void parse(Tokenizer tokenizer) {
		tokenizer.nextToken(); // Skip the "var" token

		VarType type = VarType.valueOf(tokenizer.nextToken().toString().toUpperCase()); // Retrieve type
		String name = tokenizer.nextToken().toString(); // Retrieve identifier
		tokenizer.nextToken(); // Skip the "=" token

		do {
			Token valueToken = tokenizer.nextToken(); // retrieve variable token
			if (valueToken.toString().equals(".")) valueToken = tokenizer.nextToken(); // If the value is the append "." skip
			assignVariable(name, type, valueToken, true);
		} while (tokenizer.hasNextToken());

	}

	// var string meme = meme2."hi"

	private void assignVariable(String name, VarType type, Token valueToken, boolean append) {
		Object value = null;
		if (Runtime.getVariable(valueToken.toString()) != null) {
			if (type == VarType.STRING // If the type is a string, it can take any
					// If the type is an int, it can only take ints
					|| (type == VarType.INT && Runtime.getVariable(valueToken.toString()).getType() == VarType.INT)) {
				value = Runtime.getVariable(valueToken.toString()).getValue();
			} else {
				Runtime.throwException("Error assigning value to variable " + name);
			}
		} else if (valueToken.getType() == TokenType.INTEGER && type == VarType.INT) { // If the token is an integer
			if (append) {
				value = Integer.valueOf(valueToken.toString());
			} else {
				value = Integer.valueOf(valueToken.toString());
			} // Store the integer value of the token
		} else if (type == VarType.STRING) { // If the token is a string
			value = valueToken.toString(); // Store the string value
		} /// if the var is an undeclared identifier, stay null

		if (Runtime.getVariable(name) == null) {
			Runtime.addVariable(new Variable(name, type, value)); // Store variable in runtime memory
		} else {
			Runtime.getVariable(name).setType(type);
			if (append && type == VarType.STRING) value = Runtime.getVariable(name).getValue().toString() + value;
			Runtime.getVariable(name).setValue(value);
		}
	}
}
