package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class EqualOperation implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("Equality check requires DataStackElements.");
        }

        // If types differ, not equal
        if (l.getDataType() != r.getDataType()) {
            return new DataStackElement(DataStackElement.Type.BOOL, false);
        }

        boolean isEqual;
        switch (l.getDataType()) {
            case INT:
                isEqual = l.getIntValue() == r.getIntValue();
                break;
            case STRING:
                isEqual = l.getStringValue().equals(r.getStringValue());
                break;
            case BOOL:
                isEqual = l.getBooleanValue() == r.getBooleanValue();
                break;
            case NIL:
                // Both are NIL, consider equal
                isEqual = true;
                break;
            default:
                throw new IllegalArgumentException("Equality not supported for type: " + l.getDataType());
        }

        return new DataStackElement(DataStackElement.Type.BOOL, isEqual);
    }

    @Override
    public String toString() {
        return "BinaryOperation(generic_eq)";
    }
}
