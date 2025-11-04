package io.github.ke_vin_s.rpal.core.compiler;

import io.github.ke_vin_s.rpal.core.cse_machine.Control;
import io.github.ke_vin_s.rpal.core.cse_machine.CseMachine;
import io.github.ke_vin_s.rpal.core.cse_machine.Environment;
import io.github.ke_vin_s.rpal.core.cse_machine.Stack;
import io.github.ke_vin_s.rpal.core.cse_machine.io.OutputWriter;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructure;

import java.util.List;

public class RPALInterpreter {
    private final List<ControlStructure> controlStructures;
    private final OutputWriter outputWriter;
    private CseMachine cseMachine;

    public RPALInterpreter(List<ControlStructure> controlStructures, OutputWriter outputWriter) {
        this.controlStructures = controlStructures;
        this.outputWriter = outputWriter;
        setup();
    }

    private void setup() {
        Control control = new Control(controlStructures);
        Stack stack = new Stack();
        Environment primitiveEnvironment = PrimitiveEnvironmentFactory.create();
        PrimitiveEnvironmentFactory.injectOutputWriter(primitiveEnvironment, outputWriter);
        cseMachine = new CseMachine(control, stack, primitiveEnvironment);
    }

    public void run() {
        if (cseMachine == null) {
            throw new IllegalStateException("CSE Machine not set up. Call setup() first.");
        }
        cseMachine.execute();
        outputWriter.flush();
    }

    public CseMachine getCseMachine() {
        return cseMachine;
    }

    public void print() {
        outputWriter.flush();
    }
}
