package supply.exige.lia.tokenizer;

import java.util.regex.Pattern;

/**
 * An object for storing regex patterns for the {@link Tokenizer}
 *
 * @Author Ali Shariatmadari
 */
public class TokenData {

    private Pattern pattern;
    private TokenType type;

    public TokenData(Pattern pattern, TokenType type) {
        this.pattern = pattern;
        this.type = type;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public TokenType getType() {
        return type;
    }
}