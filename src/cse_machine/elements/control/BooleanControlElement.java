package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class BooleanControlElement extends ControlElement {
    private final boolean value;

    public BooleanControlElement(boolean value) {
        super(String.valueOf(value));
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitBoolean(this);
    }
}
