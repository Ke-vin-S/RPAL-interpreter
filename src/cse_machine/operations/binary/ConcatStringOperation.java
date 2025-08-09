package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class ConcatStringOperation implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || l.getDataType() != DataStackElement.Type.STRING) {
            throw new IllegalArgumentException("Left operand of 'Conc' must be a string.");
        }

        if (!(right instanceof DataStackElement r) || r.getDataType() != DataStackElement.Type.STRING) {
            throw new IllegalArgumentException("Right operand of 'Conc' must be a string.");
        }

        String result = l.getStringValue() + r.getStringValue();
        return new DataStackElement(DataStackElement.Type.STRING, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(Conc)";
    }
}
