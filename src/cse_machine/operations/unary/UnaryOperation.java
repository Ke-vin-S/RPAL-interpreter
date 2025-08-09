package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public interface UnaryOperation {
    DataStackElement apply(StackElement operand);
}
