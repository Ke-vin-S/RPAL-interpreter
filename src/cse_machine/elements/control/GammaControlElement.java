package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class GammaControlElement extends ControlElement {
    public GammaControlElement() {
        super("GAMMA");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitGamma(this);
    }
}
