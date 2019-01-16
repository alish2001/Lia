package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.Tokenizer.Tokenizer;

public class PrintIntParser extends Parser{

    @Override
    public boolean shouldParse(String line) {
        return line.matches("print[\\s]*\\([\\s]*(-)?[0-9]+\\)");
    }

    @Override
    public void parse(Tokenizer tokenizer) {
        tokenizer.nextToken(); // Skip "print" token
        tokenizer.nextToken(); // Skip "(" token
        Runtime.print(tokenizer.nextToken());
        tokenizer.nextToken(); // Skip ")" token
    }
}
