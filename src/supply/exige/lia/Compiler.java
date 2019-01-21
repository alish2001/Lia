package supply.exige.lia;

import supply.exige.lia.tokenizer.Token;
import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.*;

public class Compiler {

    public static String[] code;

    final static String loopStart = "[\\s]*loop[\\s]*\\([\\s]*[0-9]*[\\s]*\\)";
    final static String loopEnd = "[\\s]*endLoop[\\s]*";

    public static void feedCode(Document codeFeed) {
        try {
            code = codeFeed.getText(0, codeFeed.getLength()).split("\n");
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void compile() {
        addLoops();
        cleanComments();
    }

    private static LoopBlock[] retrieveLoopBlocks() {

        List<LoopBlock> blocks = new ArrayList<>();
        LoopBlock block = new LoopBlock();

        for (int i = 0; i < code.length; i++) {
            System.out.println("loop iteration #" + i + "\n");
            String line = code[i].trim();

            if (line.matches(loopStart)) {
                if (block.getStartIndex() == -1) {
                    System.out.println("LINE MATCHED TO START, INDEX=" + i);
                    block.setStartIndex(i + 1);
                    Tokenizer tokenizer = new Tokenizer(line);
                    tokenizer.nextToken(); // skip the "loop" token
                    tokenizer.nextToken(); // skip the "(" token
                    Token iterationTokenNum = tokenizer.nextToken();
                    if (iterationTokenNum.getType() == TokenType.INTEGER) {
                        int value = Integer.valueOf(iterationTokenNum.toString());
                        System.out.println("val="+value);
                        if (value == 0){
                            System.out.println("val=ZERO");
                            Runtime.throwException("<Compilation> LoopException: Your loop cannot iterate ZERO times. Please enter an integer > 0.");
                            return null;
                        }

                        block.setNumberOfIterations(Integer.valueOf(iterationTokenNum.toString()));
                    } else {
                        Runtime.throwException("<Compilation> LoopException: Please enter the number of iterations for your loop. (integer > 0)");
                        return null;
                    }

                } else {
                    Runtime.throwException("<Compilation> LoopException: Nested loops are not possible, please end the current loop (using endLoop) before starting a new one.");
                }
            } else if (line.matches(loopEnd)) {
                if (block.getEndIndex() == -1) {
                    System.out.println("LINE MATCHED TO END, INDEX=" + i);

                    block.setEndIndex(i - 1);
                } else {
                    Runtime.throwException("LoopException: Nested loops are not possible, please end the current loop (using endLoop) before starting a new one.");
                }
            }

            if (block.getStartIndex() > -1 && block.getEndIndex() > -1) { // If a looped block is found, process

                String[] loopedCode = new String[block.getEndIndex() - block.getStartIndex() + 1];

                for (int s = 0; s < loopedCode.length; s++) {
                    loopedCode[s] = code[block.getStartIndex() + s];
                }

                Collections.reverse(Arrays.asList(loopedCode));
                block.setCode(loopedCode);
                blocks.add(block);

                block = new LoopBlock();
            }
        }

        LoopBlock[] result = new LoopBlock[blocks.size()];

        for (LoopBlock b : blocks) {
            System.out.println("Block Code: " + Arrays.toString(b.getCode()) + " start @ " + b.getStartIndex());
        }
        result = blocks.toArray(result);

        return result;
    }

    private static void addLoops() {
        LoopBlock[] blocks = retrieveLoopBlocks();
        //if (blocks == null) return;
        List<String> codeList = new ArrayList<>();
        int offSet = 0;

        for (String s : code) codeList.add(s);

        for (LoopBlock b : blocks) {
            for (int l = 1; l < b.getNumberOfIterations(); l++) {
                System.out.println("hah");
                for (int i = 0; i < b.getCode().length; i++) {
                    System.out.println("adding " + b.getCode()[i] + " to index " + b.getInsertIndex() + " with offset=" + offSet);
                    codeList.add(b.getInsertIndex() + offSet, b.getCode()[i]);
                }
                System.out.println("endIndex=" + b.getEndIndex() + " Startindex=" + b.getStartIndex() + " is " + (b.getEndIndex() - b.getStartIndex()));
                offSet += b.getEndIndex() - b.getStartIndex() + 1;
            }

        }

        for (int i = 0; i < codeList.size(); i++)
            if (codeList.get(i).matches(loopStart) || codeList.get(i).matches(loopEnd)) codeList.remove(i);

        // System.out.println(codeList);

        code = new String[codeList.size()];
        for (int i = 0; i < codeList.size(); i++) {
            code[i] = codeList.get(i);
        }
    }

    private static void cleanComments() {
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