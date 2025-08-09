package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class IntLessThanOrEqual implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("Less-than-or-equal comparison requires DataStackElements.");
        }

        if (l.getDataType() != DataStackElement.Type.INT || r.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("Less-than-or-equal comparison requires integer operands.");
        }

        boolean result = l.getIntValue() <= r.getIntValue();
        return new DataStackElement(DataStackElement.Type.BOOL, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(le)";
    }
}
