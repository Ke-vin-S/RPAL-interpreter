package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class NilControlElement extends ControlElement {
    public NilControlElement() {
        super("NIL");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitNil(this);
    }
}
