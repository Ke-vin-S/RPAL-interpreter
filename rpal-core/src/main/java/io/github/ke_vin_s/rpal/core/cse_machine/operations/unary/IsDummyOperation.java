package io.github.ke_vin_s.rpal.core.cse_machine.operations.unary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.DataStackElement;

public class IsDummyOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        boolean result = operand instanceof DataStackElement
                && ((DataStackElement) operand).isDummy();
        return new DataStackElement(DataStackElement.Type.BOOL, result);
    }

    @Override
    public String toString() {
        return "UnaryOperation(isdummy)";
    }
}
