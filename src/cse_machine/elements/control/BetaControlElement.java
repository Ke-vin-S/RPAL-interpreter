package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class BetaControlElement extends ControlElement {
    public BetaControlElement() {
        super("Beta");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitBeta(this);
    }
}
