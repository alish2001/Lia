package supply.exige.lia.tokenizer;

/**
 * An enumerator for holding various types of tokens.
 *
 * @Author Ali Shariatmadari
 */
public enum TokenType {

    /**
     * An empty token.
     */
    EMPTY,

    /**
     * A comment. Just like this one.
     */
    COMMENT,

    /**
     * A token.
     */
    TOKEN,

    /**
     * An append used to append multiple variables. (e.g. "Test".2)
     */
    APPEND,

    /**
     * A mathematical expression. {2+2}
     */
    MATH_EXPRESSION,

    /**
     * A barcket. ()
     */
    BRACKET,

    /**
     * Starts with a letter, proceeding with letters/numbers
     */
    IDENTIFIER,

    /**
     * A number.
     */
    INTEGER,

    /**
     * Anything in double quotes.
     */
    STRING
}