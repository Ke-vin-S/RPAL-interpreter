package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class IsIntegerOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        boolean isInt = operand instanceof DataStackElement dse && dse.getDataType() == DataStackElement.Type.INT;
        return new DataStackElement(DataStackElement.Type.BOOL, isInt);
    }

    @Override
    public String toString() {
        return "UnaryOperation(isinteger)";
    }
}
