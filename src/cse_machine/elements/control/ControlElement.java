package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;
import cse_machine.Element;

public abstract class ControlElement extends Element {
    private final String name;

    public ControlElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void accept(ControlElementVisitor visitor);

    @Override
    public String toString() {
        return name;
    }
}