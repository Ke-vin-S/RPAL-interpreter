package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class DummyControlElement extends ControlElement {
    public DummyControlElement() {
        super("DUMMY");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitDummy(this);
    }
}
