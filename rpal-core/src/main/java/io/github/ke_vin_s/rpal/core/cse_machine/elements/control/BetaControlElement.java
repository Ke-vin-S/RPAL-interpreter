package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class BetaControlElement extends ControlElement {
    public BetaControlElement() {
        super("Beta");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitBeta(this);
    }
}
