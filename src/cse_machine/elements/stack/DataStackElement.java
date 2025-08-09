package cse_machine.elements.stack;

public class DataStackElement extends StackElement {
    public enum Type { INT, BOOL, STRING, NIL, DUMMY }

    private final Type type;
    private final Object value;

    public DataStackElement(Type type, Object value) {
        this.type = type;
        if (type == Type.DUMMY) {
            this.value = "";
            return;
        }
        this.value = value;
    }

    public Type getDataType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return "Data(" + type + ")";
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "nil";
    }

    public int getIntValue() {
        if (type != Type.INT) {
            throw new IllegalStateException("DataStackElement does not contain an INT");
        }
        return (Integer) value;
    }

    public boolean getBooleanValue() {
        if (type != Type.BOOL) {
            throw new IllegalStateException("DataStackElement does not contain a BOOL");
        }
        return (Boolean) value;
    }

    public String getStringValue() {
        if (type != Type.STRING) {
            throw new IllegalStateException("DataStackElement does not contain a STRING");
        }
        return (String) value;
    }

    public boolean isNil() {
        return type == Type.NIL;
    }

    public boolean isDummy() {
        return type == Type.DUMMY;
    }
}
