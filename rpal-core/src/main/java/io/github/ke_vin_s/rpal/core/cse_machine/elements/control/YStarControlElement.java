package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class YStarControlElement extends ControlElement {
    public YStarControlElement() {
        super("Y*");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitYStar(this);
    }
}
