package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class StringControlElement extends ControlElement {
    private final String value;

    public StringControlElement(String value) {
        super("\"" + value + "\"");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitString(this);
    }
}
