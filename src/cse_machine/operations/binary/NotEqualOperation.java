package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class NotEqualOperation implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("NotEqual operation requires DataStackElements.");
        }

        // If types differ, they are definitely not equal
        if (l.getDataType() != r.getDataType()) {
            return new DataStackElement(DataStackElement.Type.BOOL, true);
        }

        switch (l.getDataType()) {
            case INT:
                return new DataStackElement(
                        DataStackElement.Type.BOOL,
                        l.getIntValue() != r.getIntValue()
                );
            case STRING:
                return new DataStackElement(
                        DataStackElement.Type.BOOL,
                        !l.getStringValue().equals(r.getStringValue())
                );
            case BOOL:
                return new DataStackElement(
                        DataStackElement.Type.BOOL,
                        l.getBooleanValue() != r.getBooleanValue()
                );
            case NIL:
                // Both NIL means equal, so not equal = false
                return new DataStackElement(DataStackElement.Type.BOOL, false);
            default:
                throw new IllegalArgumentException(
                        "Inequality not supported for type: " + l.getDataType()
                );
        }
    }

    @Override
    public String toString() {
        return "BinaryOperation(generic_ne)";
    }
}
