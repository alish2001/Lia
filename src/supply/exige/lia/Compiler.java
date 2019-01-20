package supply.exige.lia;

import supply.exige.lia.tokenizer.TokenType;
import supply.exige.lia.tokenizer.Tokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.*;

public class Compiler {

    private static Document codeFeed;
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

    private static void addLoops() {
        List<String> codeList = new ArrayList<>();
        for (String s : code) codeList.add(s);

        System.out.println(codeList);

        int loopStartPos = -1;
        int loopEndPos = -1;

        boolean reset;

        for (int i = 0; i < codeList.size(); i++) {
            String line = codeList.get(i).trim();

            if (loopStartPos != -1 && loopEndPos != -1) { // If a looped block is found, process


                    String[] loopedCode = new String[loopEndPos - loopStartPos];

                    for (int s = 0; s < loopedCode.length; s++){
                        loopedCode[s] = codeList.get(s);
                    }

                    //Collections.reverse(Arrays.asList(loopedCode));
                    for (int j = 0; j < loopedCode.length; j++){
                        codeList.add(loopEndPos+1, loopedCode[j]);
                        System.out.println("Added " + loopedCode[j] + " to index " + loopEndPos+1);
                    }

                    loopStartPos = -1;
                    loopEndPos = -1;
            }

            if (line.matches(loopStart)) {
                if (loopStartPos == -1) {
                    System.out.println("LINE MATCHED TO START, INDEX=" + i);
                    codeList.set(i, "");
                    loopStartPos = i;
                } else {
                    Runtime.throwException("LoopException: Nested loops are not possible, please end the current loop (using endLoop) before starting a new one.");
                }
            } else if (line.matches(loopEnd)) {
                if (loopEndPos == -1) {
                    System.out.println("LINE MATCHED TO END, INDEX=" + i);
                    codeList.set(i, "");
                    loopEndPos = i;
                } else {
                    Runtime.throwException("LoopException: Nested loops are not possible, please end the current loop (using endLoop) before starting a new one.");
                }
            }
        }

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