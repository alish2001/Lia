package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Tokenizer;

public class PrintVarParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        return line.matches("print[\\s]*\\([\\s]*[a-zA-Z][a-zA-Z0-9]*[\\s]*\\)");
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        tokenizer.nextToken(); // Skip "print" token
        tokenizer.nextToken(); // Skip "(" token
        Runtime.print(Runtime.getVariable(tokenizer.nextToken().toString()).getValue());
        tokenizer.nextToken(); // Skip ")" token
    }
}
