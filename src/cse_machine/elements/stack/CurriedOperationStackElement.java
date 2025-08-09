package cse_machine.elements.stack;

import cse_machine.CallableElement;
import cse_machine.Control;
import cse_machine.EnvironmentManager;
import cse_machine.Stack;
import cse_machine.operations.two_step.TwoStepOperation;

import java.util.List;

public class CurriedOperationStackElement<I> extends StackElement implements CallableElement {

    private final TwoStepOperation<I, ? extends StackElement> operation;
    private final I intermediate;

    public CurriedOperationStackElement(TwoStepOperation<I, ? extends StackElement> operation) {
        this.operation = operation;
        this.intermediate = null;
    }

    private CurriedOperationStackElement(TwoStepOperation<I, ? extends StackElement> operation, I intermediate) {
        this.operation = operation;
        this.intermediate = intermediate;
    }

    public StackElement apply(StackElement arg) {
        if (intermediate == null) {
            I next = operation.processFirstArgument(arg);
            return new CurriedOperationStackElement<>(operation, next);
        } else {
            return operation.combine(intermediate, arg);
        }
    }

    @Override
    public String toString() {
        return intermediate == null
                ? "FunFrame(" + operation.getName() + ")"
                : "FunFrame(" + operation.getPartialName(intermediate) + ")";
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Curried operation expects exactly 1 argument.");
        }

        StackElement result = apply(arguments.get(0));
        stack.push(result);
    }

    @Override
    public int getArity() {
        return 1;
    }
}
