package io.github.ke_vin_s.rpal.core.cse_machine.operations.unary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.DataStackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;

public class IsStringOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        boolean result = operand instanceof DataStackElement dse && dse.getDataType() == DataStackElement.Type.STRING;
        return new DataStackElement(DataStackElement.Type.BOOL, result);
    }

    @Override
    public String toString() {
        return "UnaryOperation(isstring)";
    }
}
