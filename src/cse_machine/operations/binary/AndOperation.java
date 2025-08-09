package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class AndOperation implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || l.getDataType() != DataStackElement.Type.BOOL) {
            throw new IllegalArgumentException("Left operand of AND must be a boolean DataStackElement.");
        }

        if (!(right instanceof DataStackElement r) || r.getDataType() != DataStackElement.Type.BOOL) {
            throw new IllegalArgumentException("Right operand of AND must be a boolean DataStackElement.");
        }

        boolean result = l.getBooleanValue() && r.getBooleanValue();
        return new DataStackElement(DataStackElement.Type.BOOL, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(and)";
    }
}
