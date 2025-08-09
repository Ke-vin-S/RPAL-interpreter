package cse_machine.elements.stack;

import cse_machine.*;
import cse_machine.elements.control.EnvironmentControlElement;

import java.util.List;

public class LambdaClosureStackElement extends StackElement implements CallableElement {
    private final List<String> boundVariables;
    private final int newIndex;
    private final int environmentMarker;

    public LambdaClosureStackElement(List<String> boundVariables, int newIndex, int environmentMarker) {
        this.boundVariables = boundVariables;
        this.newIndex = newIndex;
        this.environmentMarker = environmentMarker;
    }

    public List<String> getBoundVariables() {
        return boundVariables;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public int getEnvironmentMarker() {
        return environmentMarker;
    }

    @Override
    public String toString() {
        String vars = String.join(", ", boundVariables);
        return String.format("[lambda closure: %s: %d]", vars, newIndex);
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Lambda expects exactly one argument.");
        }

        StackElement arg = arguments.get(0);

        if (boundVariables.size() == 1) {
            // Handle single variable lambda: just bind the argument as-is
            EnvironmentStackElement childEnv = envManager.createChildEnvironment(environmentMarker);
            Environment env = childEnv.getEnvironment();
            env.setVariable(boundVariables.get(0), arg);

            stack.push(new EnvironmentStackElement(childEnv.getNumber(), env));
            control.append(new EnvironmentControlElement(childEnv.getNumber()));
            control.appendControlStructureByLevel(newIndex);
            return;
        }

        // Expecting a tuple for multiple variables
        if (!(arg instanceof TupleStackElement tupleArg)) {
            throw new IllegalArgumentException("Lambda with multiple parameters expects a TupleStackElement.");
        }

        List<StackElement> values = tupleArg.getElements();

        if (values.size() != boundVariables.size()) {
            throw new IllegalArgumentException("Mismatch between number of bound variables and tuple elements.");
        }

        EnvironmentStackElement childEnv = envManager.createChildEnvironment(environmentMarker);
        Environment env = childEnv.getEnvironment();

        for (int i = 0; i < boundVariables.size(); i++) {
            env.setVariable(boundVariables.get(i), values.get(i));
        }

        stack.push(new EnvironmentStackElement(childEnv.getNumber(), env));
        control.append(new EnvironmentControlElement(childEnv.getNumber()));
        control.appendControlStructureByLevel(newIndex);
    }

    @Override
    public int getArity() {
        return 1;
    }
}