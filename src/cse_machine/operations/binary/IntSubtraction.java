package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class IntSubtraction implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("Subtraction requires DataStackElements.");
        }

        if (l.getDataType() != DataStackElement.Type.INT || r.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("Subtraction requires integer operands.");
        }

        int result = l.getIntValue() - r.getIntValue();
        return new DataStackElement(DataStackElement.Type.INT, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(-)";
    }
}
