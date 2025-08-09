package cse_machine.elements.stack;

import cse_machine.CallableElement;
import cse_machine.Control;
import cse_machine.EnvironmentManager;
import cse_machine.Stack;
import cse_machine.operations.binary.BinaryOperation;

import java.util.List;

public class BinOpStackElement extends StackElement implements CallableElement {
    private final String name;
    private final BinaryOperation operation;

    public BinOpStackElement(String name, BinaryOperation operation) {
        this.name = name;
        this.operation = operation;
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        if (arguments.size() != 2) {
            throw new IllegalArgumentException("Binary operation '" + name + "' expects 2 arguments.");
        }

        StackElement left = arguments.get(0);
        StackElement right = arguments.get(1);

        StackElement result = operation.apply(left, right);
        stack.push(result);
    }

    @Override
    public int getArity() {
        return 2;
    }

    @Override
    public String toString() {
        return "FunFrame(" + name + ")";
    }
}
