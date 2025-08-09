package cse_machine;

import cse_machine.elements.stack.EnvironmentStackElement;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentManager {
    private final Map<Integer, EnvironmentStackElement> allEnvironments = new HashMap<>();
    private int nextEnvNumber = 1; // 0 reserved for primitive
    private EnvironmentStackElement current;

    public void initializePrimitiveEnvironment(Environment primitiveEnv) {
        allEnvironments.clear();
        EnvironmentStackElement primitive = new EnvironmentStackElement(0, primitiveEnv);
        allEnvironments.put(0, primitive);
        current = primitive;
    }

    public EnvironmentStackElement getCurrent() {
        return current;
    }

    public int getCurrentNumber() {
        return current.getNumber();
    }

    /**
     * Create a child environment specifying the parent environment number explicitly.
     */
    public EnvironmentStackElement createChildEnvironment(int parentEnvNumber) {
        EnvironmentStackElement parentElem = allEnvironments.get(parentEnvNumber);
        if (parentElem == null) {
            throw new IllegalArgumentException("Parent environment with number " + parentEnvNumber + " does not exist.");
        }

        Environment newEnv = new Environment(parentElem.getEnvironment());
        int number = nextEnvNumber++;
        EnvironmentStackElement child = new EnvironmentStackElement(number, newEnv);
        allEnvironments.put(number, child);
        current = child; // move context to the new env
        return child;
    }

    /**
     * Switches the current environment to a previously created one by number.
     * This does NOT delete or modify any environment.
     */
    public void switchToEnvironment(int envNumber) {
        EnvironmentStackElement target = allEnvironments.get(envNumber);
        if (target == null) {
            throw new IllegalArgumentException("Environment with number " + envNumber + " does not exist.");
        }
        current = target;
    }

    /**
     * Get a specific environment element by its number.
     */
    public EnvironmentStackElement getEnvironmentByNumber(int envNumber) {
        return allEnvironments.get(envNumber);
    }
}
