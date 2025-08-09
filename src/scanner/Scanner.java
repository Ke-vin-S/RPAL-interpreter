package scanner;

import java.util.List;

public abstract class Scanner {
    protected String input;
    protected int currentPosition;
    protected int lineNumber;
    protected int columnNumber;

    public abstract void setInput(String input);
    public abstract boolean hasNext();
    public abstract Token nextToken();
    public abstract List<Token> tokenize();

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