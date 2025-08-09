package cse_machine.operations.unary;

import cse_machine.elements.stack.StackElement;
import cse_machine.elements.stack.DataStackElement;
import cse_machine.CallableElement;

public class IsFunctionOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        boolean result = operand instanceof CallableElement;
        return new DataStackElement(DataStackElement.Type.BOOL, result);
    }

    @Override
    public String toString() {
        return "UnaryOperation(isfunction)";
    }
}
