package cse_machine;

import cse_machine.elements.stack.StackElement;

import java.util.List;

public interface CallableElement {
    void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments);
    int getArity();
}
