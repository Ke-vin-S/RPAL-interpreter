package io.github.ke_vin_s.rpal.core.cse_machine.elements.stack;

import io.github.ke_vin_s.rpal.core.cse_machine.Environment;

public class EnvironmentStackElement extends StackElement {
    private final int number;
    private final Environment environment;

    public EnvironmentStackElement(int number, Environment environment) {
        this.number = number;
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "EnvFrame" + environment.toString();
    }
}
