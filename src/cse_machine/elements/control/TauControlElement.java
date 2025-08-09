package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

public class TauControlElement extends ControlElement {
    private int numberOfElements;

    public TauControlElement() {
        super("tau");
        this.numberOfElements = 1;
    }

    public TauControlElement(int numberOfElements) {
        super("tau");
        this.numberOfElements = numberOfElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements){
        this.numberOfElements = numberOfElements;
    }

    @Override
    public String toString() {
        return "tau(" + numberOfElements + ")";
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitTau(this);
    }
}
