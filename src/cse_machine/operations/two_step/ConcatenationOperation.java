package cse_machine.operations.two_step;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class ConcatenationOperation implements TwoStepOperation<String, StackElement> {

    @Override
    public String processFirstArgument(StackElement arg) {
        if (!(arg instanceof DataStackElement dse) || dse.getDataType() != DataStackElement.Type.STRING) {
            throw new IllegalArgumentException("Concatenation expects string argument.");
        }
        return dse.getStringValue();
    }

    @Override
    public StackElement combine(String intermediate, StackElement arg) {
        if (!(arg instanceof DataStackElement dse) || dse.getDataType() != DataStackElement.Type.STRING) {
            throw new IllegalArgumentException("Concatenation expects string argument.");
        }
        String result = intermediate + dse.getStringValue();
        return new DataStackElement(DataStackElement.Type.STRING, result);
    }

    @Override
    public String getName() {
        return "conc";
    }

    @Override
    public String getPartialName(String intermediate) {
        return "conc_partial: \"" + intermediate + "\"";
    }
}
