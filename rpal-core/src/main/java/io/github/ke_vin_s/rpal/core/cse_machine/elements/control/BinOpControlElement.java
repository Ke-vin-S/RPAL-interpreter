package io.github.ke_vin_s.rpal.core.cse_machine.elements.control;

import io.github.ke_vin_s.rpal.core.cse_machine.ControlElementVisitor;

public class BinOpControlElement extends ControlElement {
    private final String operator;

    public BinOpControlElement(String operator) {
        super(String.format("BinOp(%s)", operator));
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitBinOp(this);
    }
}
