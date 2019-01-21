package supply.exige.lia;

public class LoopBlock {

    private String[] code;
    private int numberOfIterations;
    private int startIndex, endIndex;

    public LoopBlock() {
        startIndex = -1;
        endIndex = -1;
    }

    public LoopBlock(int startIndex, int endIndex, int numberOfIterations) {

        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void setCode(String[] code) {
        this.code = code;
    }

    public void setNumberOfIterations(int iterations){
        numberOfIterations = iterations;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String[] getCode() {
        return code;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public int getInsertIndex() {
        return endIndex + 1; // Insert just before the end
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
