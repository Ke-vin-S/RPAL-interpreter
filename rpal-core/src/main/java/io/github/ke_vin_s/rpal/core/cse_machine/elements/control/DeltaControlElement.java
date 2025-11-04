package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class DeltaControlElement extends ControlElement {
    private final int targetLevel;

    public DeltaControlElement(int targetLevel) {
        super(String.format("Delta(%d)", targetLevel));
        this.targetLevel = targetLevel;
    }

    public int getTargetLevel() {
        return targetLevel;
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitDelta(this);
    }
}
