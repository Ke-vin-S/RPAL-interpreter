package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class IntExponentiation implements BinaryOperation {
    @Override
    public StackElement apply(StackElement left, StackElement right) {
        if (!(left instanceof DataStackElement l) || !(right instanceof DataStackElement r)) {
            throw new IllegalArgumentException("Exponentiation requires DataStackElements.");
        }

        if (l.getDataType() != DataStackElement.Type.INT || r.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("Exponentiation requires integer operands.");
        }

        int base = l.getIntValue();
        int exponent = r.getIntValue();

        if (exponent < 0) {
            throw new ArithmeticException("Negative exponents are not supported for integer exponentiation.");
        }

        int result = (int) Math.pow(base, exponent);
        return new DataStackElement(DataStackElement.Type.INT, result);
    }

    @Override
    public String toString() {
        return "BinaryOperation(exp)";
    }
}
