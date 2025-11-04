package io.github.ke_vin_s.rpal.core.cse_machine.elements.stack;

import io.github.ke_vin_s.rpal.core.cse_machine.CallableElement;
import io.github.ke_vin_s.rpal.core.cse_machine.Control;
import io.github.ke_vin_s.rpal.core.cse_machine.EnvironmentManager;
import io.github.ke_vin_s.rpal.core.cse_machine.Stack;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.GammaControlElement;

import java.util.List;

public class EtaStackElement extends StackElement implements CallableElement {
    private final List<String> boundVariables;
    private final int newIndex;
    private final int environmentMarker;

    public EtaStackElement(List<String> boundVariables, int newIndex, int environmentMarker) {
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
    public int getArity() {
        return 0;
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        stack.push(this); // Push self again
        stack.push(new LambdaClosureStackElement(boundVariables, newIndex, environmentMarker));
        control.append(new GammaControlElement());
        control.append(new GammaControlElement());
    }

    @Override
    public String toString() {
        return String.format(
                "EtaStackElement(boundVariables=%s, newIndex=%d, environmentMarker=%d)",
                boundVariables, newIndex, environmentMarker
        );
    }
}
