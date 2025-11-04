package io.github.ke_vin_s.rpal.core.cse_machine.operations.unary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.DataStackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;

public class StemOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement arg) {
        if (!(arg instanceof DataStackElement dse) || dse.getDataType() != DataStackElement.Type.STRING) {
            throw new IllegalArgumentException("Stem expects a string argument.");
        }

        String value = dse.getStringValue();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Stem cannot be applied to an empty string.");
        }

        String result = value.substring(0, 1); // First character
        return new DataStackElement(DataStackElement.Type.STRING, result);
    }

    @Override
    public String toString() {
        return "UnaryOperation(stem)";
    }
}
