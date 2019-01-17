package supply.exige.lia.tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	
    // Register regex patterns for tokens
    private final TokenData[] tokenDatas =  new TokenData[]{new TokenData(Pattern.compile("^([a-zA-Z][a-zA-Z0-9]*)"), TokenType.IDENTIFIER),
            new TokenData(Pattern.compile("^((-)?[0-9]+)"), TokenType.INTEGER),
            new TokenData(Pattern.compile("^(\".*\")"), TokenType.STRING),
            new TokenData(Pattern.compile("^(=)"), TokenType.TOKEN),
            new TokenData(Pattern.compile("^(\\()"), TokenType.TOKEN),
            new TokenData(Pattern.compile("^(\\))"), TokenType.TOKEN),
            new TokenData(Pattern.compile("^(\\+)"), TokenType.TOKEN),
            new TokenData(Pattern.compile("^(\\.)"), TokenType.TOKEN)}; // add expression processing token {}

    private String input;

    private Token lastToken;
    private boolean pushBack;

    public Tokenizer(String tokenStr) {
        this.input = tokenStr;
    }

    public Token nextToken() {
        input = input.trim(); // Get rid of whitespace

        if (pushBack) {
            pushBack = false;
            return lastToken;
        }

        if (input.isEmpty()) { // If the input is empty, return empty token
            return (lastToken = new Token("", TokenType.EMPTY));
        }

        for (TokenData data : tokenDatas) { // For every possible TokenData
            Matcher matcher = data.getPattern().matcher(input); // Create a matcher for the pattern
            if (matcher.find()) {
                String token = matcher.group().trim(); // Retrieve matched group as a token string
                input = matcher.replaceFirst(""); // Remove the found token from input

                if (data.getType() == TokenType.STRING) { // If the current pattern matched is a string
                    return (lastToken = new Token(token.substring(1, token.length() - 1), TokenType.STRING)); // return result, skipping the start and end quotes.
                } else {
                    return (lastToken = new Token(token, data.getType()));
                }
            }
        }
        throw new IllegalStateException("<Tokenizer> Could not tokenize " + input); // Throw exception if input cannot be parsed
    }

    public boolean hasNextToken() {
        return !input.isEmpty(); // If there is no more input left to process, there are no further tokens to parse
    }

    public void pushBack() {
        if (lastToken != null) {
            this.pushBack = true;
        }
    }
}