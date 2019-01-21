package supply.exige.lia;

import supply.exige.lia.modules.LoopBlock;
import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.*;

/**
 * Compiles code to ready it for @{@link Runtime}
 *
 * @author Ali Shariatmadari
 */

public class Compiler {

    public static String[] code; // Globally stored compiled code

    // loop block regex values
    final static String loopStart = "[\\s]*loop[\\s]*\\([\\s]*[0-9]*[\\s]*\\)";
    final static String loopEnd = "[\\s]*endLoop[\\s]*";

    /**
     * Feeds code into the compiler.
     *
     * @param codeFeed
     */
    public static void feedCode(Document codeFeed) {
        try {
            code = codeFeed.getText(0, codeFeed.getLength()).split("\n");
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compiles input code.
     */
    public static void compile() {
        Runtime.printProcessing("<Compiler> Compiling code...");
        addLoops(); // Add loop statements
        cleanComments(); // Remove user comments before runtime
    }

    /**
     * Retrieves an array of {@link LoopBlock}s within the code.
     *
     * @return {@link LoopBlock}[]
     */
    private static LoopBlock[] retrieveLoopBlocks() {

        List<LoopBlock> blocks = new ArrayList<>();
        LoopBlock block = new LoopBlock();

        Runtime.printProcessing("<Compiler> Retrieving LoopBlocks within the code.\n");

        for (int i = 0; i < code.length; i++) { // Loop through the code array
            String line = code[i].trim(); // Get code at index i

            if (line.matches(loopStart)) { // If the input matches the loopStart regex pattern
                if (block.getStartIndex() == -1) { // If the startIndex for the block has not been set
                    Runtime.printProcessing("<Compiler> LoopBlock Start Index FOUND = " + i);
                    block.setStartIndex(i + 1); // Set the start index of the LoopBlock 1 after the "loop()" token
                    Tokenizer tokenizer = new Tokenizer(line); // tokenize line
                    tokenizer.nextToken(); // skip the "loop" token
                    tokenizer.nextToken(); // skip the "(" token
                    Token iterationTokenNum = tokenizer.nextToken(); // Retrieve iteration value token
                    if (iterationTokenNum.getType() == TokenType.INTEGER) {
                        int value = Integer.valueOf(iterationTokenNum.toString()); // Retrieve iteration value
                        if (value == 0) { // If the value is zero, prompt error
                            Runtime.throwException("<Compilation> LoopException: Your loop cannot iterate ZERO times. Please enter an integer > 0.");
                            return null;
                        }
                        Runtime.printProcessing("<Compiler> LoopBlock Iteration Value = " + value);
                        block.setNumberOfIterations(Integer.valueOf(iterationTokenNum.toString())); // Set the iteration number for the LoopBlock
                    } else {
                        Runtime.throwException("<Compilation> LoopException: Please enter the number of iterations for your loop. (integer > 0)");
                        return null;
                    }
                } else {
                    Runtime.throwException("<Compilation> LoopException: Nested loops are not possible, please end the current loop (using endLoop) before starting a new one.");
                }
            } else if (line.matches(loopEnd)) { // If the input matches the loopEnd regex pattern
                if (block.getEndIndex() == -1) {
                    Runtime.printProcessing("<Compiler> LoopBlock End Index FOUND = " + i);
                    block.setEndIndex(i - 1); // Set the start index of the LoopBlock 1 before the "loop()" token
                } else {
                    Runtime.throwException("<Compiler> LoopException: Nested loops are not possible, please end the current loop (using endLoop) before starting a new one.");
                }
            }

            if (block.getStartIndex() > -1 && block.getEndIndex() > -1) { // If both start and end values have been found

                String[] loopedCode = new String[block.getEndIndex() - block.getStartIndex() + 1]; // Create array based on number of lines in the LoopBlock

                // Copy code between the start and end indices to the looped code array
                for (int s = 0; s < loopedCode.length; s++) {
                    loopedCode[s] = code[block.getStartIndex() + s];
                }

                // Reverse the order of copied code and store in LoopedBlock list
                Collections.reverse(Arrays.asList(loopedCode));
                block.setCode(loopedCode);
                blocks.add(block);
                block = new LoopBlock(); // Reset block object
            }
        }

        LoopBlock[] result = new LoopBlock[blocks.size()];

        Runtime.printProcessing("<Compiler> LoopBlocks found:");
        for (LoopBlock b : blocks) {
            Runtime.printProcessing("<Compiler> Block Code: " + Arrays.toString(b.getCode()) + " start @ index " + b.getStartIndex());
        }
        result = blocks.toArray(result);
        return result;
    }

    /**
     * Retrieves loopblocks from the code and adds them according to their properties
     */
    private static void addLoops() {
        LoopBlock[] blocks = retrieveLoopBlocks();
        List<String> codeList = new ArrayList<>();
        int offSet = 0;

        for (String s : code) codeList.add(s);

        for (LoopBlock b : blocks) {
            for (int l = 1; l < b.getNumberOfIterations(); l++) {
                Runtime.printProcessing("<Compiler> Adding iteration #" + l);
                for (int i = 0; i < b.getCode().length; i++) {
                    Runtime.printProcessing("<Compiler> Adding " + b.getCode()[i] + " to index " + b.getInsertIndex() + " with offset=" + offSet);
                    codeList.add(b.getInsertIndex() + offSet, b.getCode()[i]);
                }
                offSet += b.getEndIndex() - b.getStartIndex() + 1;
                Runtime.printProcessing("<Compiler> New adding offset is " + offSet);
            }
        }

        Runtime.printProcessing("<Compiler> Cleaning up remaining loop elements...");
        for (int i = 0; i < codeList.size(); i++)
            if (codeList.get(i).matches(loopStart) || codeList.get(i).matches(loopEnd)) codeList.remove(i);

        Runtime.printProcessing("<Compiler> Overwriting code array...");
        code = new String[codeList.size()];
        for (int i = 0; i < codeList.size(); i++) {
            code[i] = codeList.get(i);
        }
        Runtime.printProcessing("<Compiler> Compiled code: " + Arrays.toString(code));
    }

    /**
     * Removes user comments from input code
     */
    private static void cleanComments() {
        Runtime.printProcessing("<Compiler> Cleaning up comments...");
        List<String> codeList = new LinkedList<>(Arrays.asList(code));
        for (int i = 0; i < codeList.size(); i++) {
            String line = codeList.get(i).trim();
            if (line.isEmpty()) continue;
            Tokenizer tokenizer = new Tokenizer(line);
            if (tokenizer.nextToken().getType() != TokenType.COMMENT) continue;
            codeList.remove(i);
        }
        code = new String[codeList.size()];
        for (int i = 0; i < codeList.size(); i++) {
            code[i] = codeList.get(i);
        }
    }
}