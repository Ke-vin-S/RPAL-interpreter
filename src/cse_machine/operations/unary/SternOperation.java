package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class SternOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement arg) {
        if (!(arg instanceof DataStackElement dse) || dse.getDataType() != DataStackElement.Type.STRING) {
            throw new IllegalArgumentException("Stern expects a string argument.");
        }

        String value = dse.getStringValue();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Stern cannot be applied to an empty string.");
        }

        String result = value.substring(1); // From second character to end
        return new DataStackElement(DataStackElement.Type.STRING, result);
    }

    @Override
    public String toString() {
        return "UnaryOperation(stern)";
    }
}
