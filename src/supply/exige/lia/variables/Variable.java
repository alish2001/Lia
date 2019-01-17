package supply.exige.lia.variables;

public class Variable {

    private String identifier;
    private VarType type;
    private Object value;

    public Variable(String identifier, VarType type, Object value){
        this.identifier = identifier;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return identifier;
    }

    public VarType getType() {
        return type;
    }

    public Object getValue(){
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(VarType type) {
        this.type = type;
    }
}