package io.github.ke_vin_s.rpal.core.tree.ast.operators;

public enum UnaryOperator {
    NEGATE("neg"), NOT("not");
    private final String symbol;
    UnaryOperator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
