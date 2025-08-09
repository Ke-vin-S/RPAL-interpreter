package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class NilControlElement extends ControlElement {
    public NilControlElement() {
        super("NIL");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitNil(this);
    }
}
