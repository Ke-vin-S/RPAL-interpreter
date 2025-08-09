package tree.ast.operators;

public enum BinaryOperator {
    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"),
    EXPONENT("**"),
    AND("&"), OR("or"),
    LESS_THAN("ls"), GREATER_THAN("gr"),LESS_THAN_OR_EQUAL("le"),
    GREATER_THAN_OR_EQUAL("ge"),
    EQUALS("eq"), NOT_EQUALS("ne");

    private final String symbol;
    BinaryOperator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
