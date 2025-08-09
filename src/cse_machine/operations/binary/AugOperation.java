package cse_machine.operations.binary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;
import cse_machine.elements.stack.TupleStackElement;

import java.util.ArrayList;
import java.util.List;

public class AugOperation implements BinaryOperation {

    @Override
    public StackElement apply(StackElement left, StackElement right) {
        // If left is NIL DataStackElement, create a single-element tuple with right
        if (left instanceof DataStackElement dataLeft && dataLeft.isNil()) {
            List<StackElement> newElements = new ArrayList<>();
            newElements.add(right);
            return new TupleStackElement(newElements);
        }

        // If left is a TupleStackElement, append right to its elements
        if (left instanceof TupleStackElement tuple) {
            List<StackElement> newElements = new ArrayList<>(tuple.getElements());
            newElements.add(right);
            return new TupleStackElement(newElements);
        }

        // Otherwise, left is invalid for AugOperation
        throw new IllegalArgumentException("Left operand must be a tuple or NIL for AugOperation.");
    }

    @Override
    public String toString() {
        return "BinaryOperation(aug)";
    }
}
