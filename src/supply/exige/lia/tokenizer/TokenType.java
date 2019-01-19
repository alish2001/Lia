package supply.exige.lia.tokenizer;

public enum TokenType {

    /** An empty token.*/
    EMPTY,

    /** An append used to append multiple variables. (e.g. "Test".2)*/
    APPEND,

    /** A token. (e.g. () = , )*/
    TOKEN,

    /** Starts with a letter, proceeding with letters/numbers */
    IDENTIFIER,

    /** A number.*/
    INTEGER,

    /** Anything in double quotes.*/
    STRING
}