package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class NotOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        if (!(operand instanceof DataStackElement dse) || dse.getDataType() != DataStackElement.Type.BOOL) {
            throw new IllegalArgumentException("Not operation expects a DataStackElement of type BOOL.");
        }

        boolean value = dse.getBooleanValue();
        return new DataStackElement(DataStackElement.Type.BOOL, !value);
    }

    @Override
    public String toString() {
        return "UnaryOperation(not)";
    }
}
