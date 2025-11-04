package io.github.ke_vin_s.rpal.core.cse_machine.operations.unary;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.DataStackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.StackElement;
import io.github.ke_vin_s.rpal.core.cse_machine.elements.stack.TupleStackElement;

public class IsTupleOperation implements UnaryOperation {
    @Override
    public DataStackElement apply(StackElement operand) {
        boolean isTupleOrNil =
                operand instanceof TupleStackElement ||
                        (operand instanceof DataStackElement data && data.isNil());

        return new DataStackElement(DataStackElement.Type.BOOL, isTupleOrNil);
    }


    @Override
    public String toString() {
        return "UnaryOperation(istuple)";
    }
}
