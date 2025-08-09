package cse_machine.io;

import java.io.PrintStream;

public class ConsoleOutputWriter implements OutputWriter {
    private final PrintStream out;
    private final StringBuilder buffer = new StringBuilder();

    public ConsoleOutputWriter() {
        this(System.out);
    }

    public ConsoleOutputWriter(PrintStream out) {
        this.out = out;
    }

    @Override
    public void write(String output) {
        buffer.append(output);
    }

    @Override
    public void flush() {
        // new line added anyway
        buffer.append('\n');
        out.print(buffer.toString());
        out.flush(); // Still flush the stream
        buffer.setLength(0);  // Clear buffer
    }
}
