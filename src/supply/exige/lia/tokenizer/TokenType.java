package supply.exige.lia.tokenizer;

public enum TokenType {

    /** An empty token.*/
    EMPTY,

    /** A token. (e.g. () = , )*/
    TOKEN,

    /** Starts with a letter, proceeding with letters/numbers */
    IDENTIFIER,

    /** A number.*/
    INTEGER,

    /** Anything in double quotes.*/
    STRING
}