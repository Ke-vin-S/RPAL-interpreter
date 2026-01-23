package io.github.ke_vin_s.rpal.core.scanner;

import io.github.ke_vin_s.rpal.core.automaton.FiniteAutomaton;
import io.github.ke_vin_s.rpal.core.utils.StringUtils;

import java.util.*;

public class RPALScanner extends BaseScanner {
    protected final FiniteAutomaton automaton;
    protected final Map<String, TokenType> stateNameToTokenType;
    protected final Screener screener;

    public RPALScanner(String input) {
        super(input);
        this.automaton = RPALAutomatonFactory.createRPALAutomaton();
        this.stateNameToTokenType = createTokenMapping();
        this.screener = new RPALScreener();
    }

    @Override
    public boolean hasNext() {
        return currentPosition < input.length();
    }

    @Override
    public List<Token> tokenize() {
        List<Token> rawTokens = new ArrayList<>();
        while (hasNext()) {
            rawTokens.add(nextToken());
        }
        // Pipe the raw tokens through the screener
        return screener.screen(rawTokens);
    }

    @Override
    public Token nextToken() {
        automaton.reset();
        int startLine = lineNumber;
        int startColumn = columnNumber;
        StringBuilder lexeme = new StringBuilder();

        while (hasNext()) {
            char currentChar = input.charAt(currentPosition);

            if (automaton.step(currentChar)) {
                lexeme.append(currentChar);
                advance(); // Updates line/col/pos in BaseScanner
            } else {
                break;
            }
        }

        if (automaton.isAccepting()) {
            String stateName = automaton.getCurrentStateName();
            String text = lexeme.toString();

            // Map the DFA state to the preliminary TokenType
            TokenType type = stateNameToTokenType.getOrDefault(stateName, TokenType.IDENTIFIER);

            // Basic post-processing for raw strings
            if (stateName.equals("END_STR")) {
                text = StringUtils.stripQuotes(text);
                text = StringUtils.unescape(text);
                type = TokenType.STRING;
            }

            return new Token(type, text, startLine, startColumn);
        }

        throw new ScannerException("Lexical error at line " + startLine + ", col " + startColumn);
    }

    private Map<String, TokenType> createTokenMapping() {
        Map<String, TokenType> mapping = new HashMap<>();
        mapping.put("IN_ID", TokenType.IDENTIFIER);
        mapping.put("IN_INT", TokenType.INTEGER);
        mapping.put("END_STR", TokenType.STRING);
        mapping.put("IN_SPACE", TokenType.DELETE);    // Screener will remove
        mapping.put("END_COMMENT", TokenType.DELETE); // Screener will remove
        mapping.put("OP_MINUS", TokenType.MINUS);
        mapping.put("OP_ARROW", TokenType.CONDITION_SIGN);
        mapping.put("OP_GT", TokenType.GREATER_THAN);
        mapping.put("OP_GE", TokenType.GREATER_THAN_EQUAL);
        mapping.put("OP_LT", TokenType.LESS_THAN);
        mapping.put("OP_LE", TokenType.LESS_THAN_EQUAL);
        mapping.put("OP_MUL", TokenType.MULTIPLY);
        mapping.put("OP_EXP", TokenType.EXPONENT);
        mapping.put("OP_SINGLE", TokenType.OPERATOR);
        return mapping;
    }
}

class ScannerException extends RuntimeException {
    public ScannerException(String message) {
        super(message);
    }
}