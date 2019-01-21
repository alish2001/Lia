package supply.exige.lia.tokenizer;

/**
 * An object for storing tokens tokenized by the {@link Tokenizer}
 *
 * @Author Ali Shariatmadari
 */
public class Token {

    private String token;
    private TokenType type;

    public Token(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    @Override
    public String toString() {
        return token;
    }

    public TokenType getType() {
        return type;
    }
}
