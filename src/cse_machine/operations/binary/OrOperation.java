package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class OrOperation implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("OR operation requires DataStackElements.");
        }
        if (l.getDataType() != DataStackElement.Type.BOOL || r.getDataType() != DataStackElement.Type.BOOL) {
            throw new IllegalArgumentException("OR operation requires boolean operands.");
        }

        boolean result = l.getBooleanValue() || r.getBooleanValue();
        return new DataStackElement(DataStackElement.Type.BOOL, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(or)";
    }
}
