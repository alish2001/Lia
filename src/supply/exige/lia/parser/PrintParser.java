package supply.exige.lia.parser;

import supply.exige.lia.Runtime;
import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;

public class PrintParser extends Parser {

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
        String printValue = "";
        Token printToken = tokenizer.nextToken(); // retrieve print token;
        do {
            if (printToken.getType() == TokenType.APPEND && tokenizer.hasNextToken())
                printToken = tokenizer.nextToken(); // If the value is the append ".", skip
            printValue += parsePrintValue(printToken);
            printToken = tokenizer.nextToken();
        } while (tokenizer.hasNextToken() && printToken.getType() != TokenType.BRACKET);

        Runtime.print(printValue); // output parsed print value
        tokenizer.nextToken(); // Skip ")" token
    }

    public String parsePrintValue(Token printToken) {
        String printValue = ""; // if the var is an undeclared identifier, stay null
        if (printToken.getType() == TokenType.IDENTIFIER) {
            if (Runtime.getVariable(printToken.toString()) != null) {
                printValue = Runtime.getVariable(printToken.toString()).getValue().toString();
            } else {
                Runtime.throwException("NullPointerException: Variable " + printToken.toString() + " does not exist or has not been declared.");
            }
        } else if (printToken.getType() == TokenType.MATH_EXPRESSION) {
            printValue = "" + Runtime.parseMathExpr(printToken.toString());
        } else if (printToken.getType() == TokenType.INTEGER || printToken.getType() == TokenType.STRING) { // If the token & type are integer
            printValue = printToken.toString(); // Get the string value of hte input
        }
        return printValue;
    }
}