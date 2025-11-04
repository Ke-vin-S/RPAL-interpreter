package io.github.ke_vin_s.rpal.core.cse_machine.operations.unary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.DataStackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;

public class ItoSOperation implements UnaryOperation {

    @Override
    public DataStackElement apply(StackElement arg) {
        if (!(arg instanceof DataStackElement data) || data.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("ItoS expects an integer argument.");
        }

        String result = Integer.toString(data.getIntValue());
        return new DataStackElement(DataStackElement.Type.STRING, result);
    }

    @Override
    public String toString() {
        return "UnaryOperation(itos)";
    }
}
