package cse_machine.operations.two_step;

import cse_machine.elements.stack.StackElement;

public interface TwoStepOperation<I, R extends StackElement> {
    I processFirstArgument(StackElement arg);
    R combine(I intermediate, StackElement arg);
    String getName();
    String getPartialName(I intermediate);
}
