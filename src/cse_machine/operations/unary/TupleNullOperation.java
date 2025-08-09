package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;

public class TupleNullOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement arg) {
        boolean isNil = arg instanceof DataStackElement data && data.isNil();
        return new DataStackElement(DataStackElement.Type.BOOL, isNil);
    }

    @Override
    public String toString() {
        return "UnaryOperation(null)";
    }
}
