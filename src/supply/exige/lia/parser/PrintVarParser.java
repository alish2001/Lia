package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.Variable;

public class PrintVarParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        return line.matches("[\\s]*print[\\s]*\\([\\s]*[a-zA-Z][a-zA-Z0-9]*[\\s]*\\)");
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        tokenizer.nextToken(); // Skip "print" token
        tokenizer.nextToken(); // Skip "(" token
        Variable var = Runtime.getVariable(tokenizer.nextToken().toString());
        Runtime.print((var != null) ? var.getValue() : null);
        tokenizer.nextToken(); // Skip ")" token
    }
}
