package io.github.ke_vin_s.rpal.core.scanner;

public abstract class BaseScanner implements Scanner {
    protected final String input;
    protected int currentPosition;
    protected int lineNumber;
    protected int columnNumber;

    public BaseScanner(String input) {
        this.input = input;
    }

    protected void reset() {
        this.currentPosition = 0;
        this.lineNumber = 1;
        this.columnNumber = 1;
    }

    protected void advance() {
        if (currentPosition < input.length()) {
            char c = input.charAt(currentPosition++);
            if (c == '\n') {
                lineNumber++;
                columnNumber = 1;
            } else {
                columnNumber++;
            }
        }
    }
}