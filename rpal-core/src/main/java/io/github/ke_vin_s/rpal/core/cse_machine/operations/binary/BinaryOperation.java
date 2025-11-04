package io.github.ke_vin_s.rpal.core.cse_machine.operations.binary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;

public interface BinaryOperation {
    StackElement apply(StackElement left, StackElement right);
}
