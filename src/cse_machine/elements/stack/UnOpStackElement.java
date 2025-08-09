package cse_machine.elements.stack;

import cse_machine.CallableElement;
import cse_machine.Control;
import cse_machine.EnvironmentManager;
import cse_machine.Stack;
import cse_machine.operations.unary.UnaryOperation;

import java.util.List;

public class UnOpStackElement extends StackElement implements CallableElement {
    private final String name;
    private final UnaryOperation operation;

    public UnOpStackElement(String name, UnaryOperation operation) {
        this.name = name;
        this.operation = operation;
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Unary operation '" + name + "' expects 1 argument.");
        }

        StackElement arg = arguments.get(0);
        DataStackElement result = operation.apply(arg);
        stack.push(result);
    }

    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public String toString() {
        return "FunFrame(" + name + ")";
    }
}
