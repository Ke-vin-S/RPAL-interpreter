package cse_machine.operations.binary;

import cse_machine.elements.stack.StackElement;

public interface BinaryOperation {
    StackElement apply(StackElement left, StackElement right);
}
