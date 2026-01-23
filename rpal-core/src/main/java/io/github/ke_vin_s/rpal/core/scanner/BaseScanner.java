package io.github.ke_vin_s.rpal.core.scanner;

public abstract class BaseScanner implements Scanner {
    protected String input;
    protected int currentPosition;
    protected int lineNumber;
    protected int columnNumber;

    public abstract void setInput(String input);

    protected void reset() {
        this.currentPosition = 0;
        this.lineNumber = 1;
        this.columnNumber = 1;
    }

    protected void updatePosition(char c) {
        if (c == '\n') {
            lineNumber++;
            columnNumber = 1;
        } else {
            columnNumber++;
        }
    }
}