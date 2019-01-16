package supply.exige.lia.parser;

import supply.exige.lia.Tokenizer.Tokenizer;

public abstract class Parser {

    /**
     * Checks to see if the line provided is to be parsed by this parser
     */
    public abstract boolean shouldParse(String line);

    /**
     * Take the superblock and tokenizer for the line and returns a block of this parser's type
     */
    public abstract void parse(Tokenizer tokenizer);
}