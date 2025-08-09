package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class YStarControlElement extends ControlElement {
    public YStarControlElement() {
        super("Y*");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitYStar(this);
    }
}
