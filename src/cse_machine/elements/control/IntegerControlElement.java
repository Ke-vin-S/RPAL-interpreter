package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class IntegerControlElement extends ControlElement {
    private final int value;

    public IntegerControlElement(int value) {
        super(String.valueOf(value));
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitInteger(this);
    }
}
