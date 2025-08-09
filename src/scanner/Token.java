package scanner;

public record Token(TokenType type, String lexeme, int line, int column) {

    @Override
    public String toString() {
        return type + ": " + lexeme + " at line: " + line + " column: " + column;
    }
}
