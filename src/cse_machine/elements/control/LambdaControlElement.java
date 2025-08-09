package cse_machine.elements.control;

import cse_machine.ControlElementVisitor;

import java.util.List;

public class LambdaControlElement extends ControlElement {
    private final List<String> boundVariables;
    private final int newIndex;

    public LambdaControlElement(List<String> boundVariables, int newIndex) {
        super(String.format("Lambda(%d, %s)", newIndex, boundVariables.toString()));
        this.boundVariables = boundVariables;
        this.newIndex = newIndex;
    }

    public List<String> getBoundVariables() {
        return boundVariables;
    }

    public int getNewIndex() {
        return newIndex;
    }

    @Override
    public String toString() {
        return "LambdaControlElement(boundVariables=" + boundVariables + ", newIndex=" + newIndex + ")";
    }

    @Override
    public void accept(ControlElementVisitor visitor) {
        visitor.visitLambda(this);
    }
}
