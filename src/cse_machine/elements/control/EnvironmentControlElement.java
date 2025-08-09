package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class EnvironmentControlElement extends ControlElement {
    private final int number;

    public EnvironmentControlElement(int number) {
        super("env " + number + ";");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitEnvironment(this);
    }
}
