package io.github.ke_vin_s.rpal.core.cse_machine.elements.stack;

import io.github.ke_vin_s.rpal.core.cse_machine.CallableElement;
import io.github.ke_vin_s.rpal.core.cse_machine.Control;
import io.github.ke_vin_s.rpal.core.cse_machine.EnvironmentManager;
import io.github.ke_vin_s.rpal.core.cse_machine.Stack;

import java.util.List;

public class YStarStackElement extends StackElement implements CallableElement {
    @Override
    public String toString() {
        return "Y*";
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        if (arguments.size() != 1) {
            throw new IllegalArgumentException("Y* expects exactly one argument.");
        }

        StackElement arg = arguments.get(0);

        if (!(arg instanceof LambdaClosureStackElement lambda)) {
            throw new IllegalStateException("Y* expects a LambdaClosureStackElement.");
        }

        EtaStackElement eta = new EtaStackElement(
                lambda.getBoundVariables(),
                lambda.getNewIndex(),
                lambda.getEnvironmentMarker()
        );

        stack.push(eta);
    }

    @Override
    public int getArity() {
        return 1;
    }
}
