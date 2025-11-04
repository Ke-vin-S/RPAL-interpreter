package io.github.ke_vin_s.rpal.core.cse_machine.operations.unary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.DataStackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;

public class NegateOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        if (!(operand instanceof DataStackElement dse) || dse.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("Negate operation expects a DataStackElement of type INT.");
        }

        int value = dse.getIntValue();
        return new DataStackElement(DataStackElement.Type.INT, -value);
    }

    @Override
    public String toString() {
        return "UnaryOperation(neg)";
    }
}
