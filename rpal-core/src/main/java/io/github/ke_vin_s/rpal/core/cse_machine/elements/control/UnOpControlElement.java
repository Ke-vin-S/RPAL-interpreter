package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class UnOpControlElement extends ControlElement {
    private final String operator;

    public UnOpControlElement(String operator) {
        super(String.format("UnOp(%s)", operator));
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitUnOp(this);
    }
}
