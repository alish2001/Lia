package supply.exige.lia.parser;

import supply.exige.lia.tokenizer.Tokenizer;

public class PrintStringParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        return line.matches("print[\\s]*\\([\\s]*\".*\"[\\s]*\\)");
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        tokenizer.nextToken(); // Skip "print" token
        tokenizer.nextToken(); // Skip "(" token
        System.out.println(tokenizer.nextToken()); // Print variable value associated with this value.
        tokenizer.nextToken(); // Skip ")" token
    }
}
