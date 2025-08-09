package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class IntDivision implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("Division requires DataStackElements.");
        }

        if (l.getDataType() != DataStackElement.Type.INT || r.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("Division requires integer operands.");
        }

        int divisor = r.getIntValue();
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }

        int result = l.getIntValue() / divisor;
        return new DataStackElement(DataStackElement.Type.INT, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(div)";
    }
}
