package cse_machine.operations.unary;

import cse_machine.elements.stack.DataStackElement;
import cse_machine.elements.stack.StackElement;
import cse_machine.io.OutputWriter;

public class PrintOperation implements UnaryOperation {
    private final OutputWriter outputWriter;

    public PrintOperation(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    @Override
    public DataStackElement apply(StackElement arg) {
        String output = arg.toString();
        outputWriter.write(output);
        return new DataStackElement(DataStackElement.Type.STRING, output);
    }

    @Override
    public String toString() {
        return "UnaryOperation(print)";
    }
}
