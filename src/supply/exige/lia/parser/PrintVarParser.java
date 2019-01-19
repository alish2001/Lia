package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;
import supply.exige.lia.variables.Variable;

public class PrintVarParser extends Parser {

    @Override
    public int shouldParse(String line) {
        int code = 0;
        if (line.matches("[\\s]*print[\\s]*\\([\\s]*.*[\\s]*\\)")) code = 1;
        return code;
    }

    @Override
    public void parse(Tokenizer tokenizer, int code) {
        tokenizer.nextToken(); // Skip "print" token
        tokenizer.nextToken(); // Skip "(" token
        Token printToken = tokenizer.nextToken();
        if (printToken.getType() == TokenType.STRING || printToken.getType() == TokenType.INTEGER) {
            Runtime.print(printToken.toString());
        } else if (printToken.getType() == TokenType.IDENTIFIER) {
            Variable var = null;
            do {
                var = Runtime.getVariable(printToken.toString());
                // TODO add variable appending from VariableParser for print
            }while (tokenizer.hasNextToken());
            Runtime.print((var != null) ? var.getValue() : null);
        }
        tokenizer.nextToken(); // Skip ")" token
    }
}
