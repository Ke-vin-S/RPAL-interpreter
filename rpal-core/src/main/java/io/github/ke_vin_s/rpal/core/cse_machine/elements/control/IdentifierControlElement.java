package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class IdentifierControlElement extends ControlElement {
    private final String name;

    public IdentifierControlElement(String name) {
        super("ID: " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitData(this);
    }
}
