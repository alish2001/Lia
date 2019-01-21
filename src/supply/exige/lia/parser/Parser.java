package supply.exige.lia.parser;

import supply.exige.lia.tokenizer.Tokenizer;

/**
 * Abstract Parser
 * Template for creating {@link supply.exige.lia.Runtime} Parsers
 *
 * @author Ali Shariatmadari
 */
public abstract class Parser {

    /**
     * Checks to see if the line provided is to be parsed by this parser.
     *
     * @return ParseCode
     */
    public abstract int shouldParse(String line);

    /**
     * Parses input.
     */
    public abstract void parse(Tokenizer tokenizer, int code);
}