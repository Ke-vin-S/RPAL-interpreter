package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;
import cse_machine.elements.stack.TupleStackElement;

public class OrderOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement arg) {
        int size;

        if (arg instanceof DataStackElement data && data.isNil()) {
            size = 0;
        } else if (arg instanceof TupleStackElement tuple) {
            size = tuple.getElements().size();
        } else {
            throw new IllegalArgumentException("Order expects a tuple or NIL.");
        }

        return new DataStackElement(DataStackElement.Type.INT, size);
    }

    @Override
    public String toString() {
        return "UnaryOperation(order)";
    }
}
