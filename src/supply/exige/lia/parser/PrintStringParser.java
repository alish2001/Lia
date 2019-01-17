package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintStringParser extends Parser {

    @Override
    public boolean shouldParse(String line) {
        Pattern pattern = Pattern.compile("[\\s]*print[\\s]*\\([\\s]*\"[^\"].[^\"]*\"[\\s]*\\)");
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }

    @Override
    public void parse(Tokenizer tokenizer) {
            tokenizer.nextToken(); // Skip "print" token
            tokenizer.nextToken(); // Skip "(" token
            Runtime.print(tokenizer.nextToken()); // Print variable value associated with this value.
            tokenizer.nextToken(); // Skip ")" token
    }
}
