package supply.exige.lia;

public class LoopBlock {

    private int startIndex;
    private int endIndex;
    private int insertIndex;

    public LoopBlock(int insertIndex, int startIndex, int endIndex){
        this.insertIndex = insertIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

}
