package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class DummyControlElement extends ControlElement {
    public DummyControlElement() {
        super("DUMMY");
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitDummy(this);
    }
}
