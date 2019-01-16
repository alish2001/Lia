package supply.exige.lia;

import supply.exige.lia.variables.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of code
 */
public abstract class Block {

    private Block superBlock;
    private List<Block> subBlocks;
    private List<Variable> variables;

    public Block(Block superBlock) {
        this.superBlock = superBlock;
        subBlocks = new ArrayList<>();
        variables = new ArrayList<>();
    }

    public Block getSuperBlock() {
        return superBlock;
    }

    public void addBlock(Block b){
        subBlocks.add(b);
    }

    public void addVariable(Variable variable){
        variables.add(variable);
    }

    public Variable getVariable(String name){
        // Check superblocks for variables out scope

        for (Variable v : variables){ // For all variables in the stored variable list for this block
            if (v.getName().equals(name)) return v; // return variable if the identifiers match
        }
        return null; // no matches found
    }

    public abstract void execute();
}
