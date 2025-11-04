package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class GammaControlElement extends ControlElement {
    public GammaControlElement() {
        super("GAMMA");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitGamma(this);
    }
}
