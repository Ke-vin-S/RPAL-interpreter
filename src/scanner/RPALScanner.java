package scanner;

import automaton.FiniteAutomaton;
import automaton.State;
import utils.StringUtils;

import java.util.*;

public class RPALScanner extends Scanner {
    protected final FiniteAutomaton automaton;
    protected final Map<State, TokenType> acceptingStatesToTokenTypes;

    public RPALScanner() {
        this.automaton = RPALAutomatonFactory.createRPALAutomaton();
        this.acceptingStatesToTokenTypes = createTokenMapping();
    }

    @Override
    public void setInput(String input) {
        this.input = input;
        reset();
        automaton.reset();
    }

    @Override
    public boolean hasNext() {
        return currentPosition < input.length();
    }

    @Override
    public Token nextToken() {
        int startPosition = currentPosition;
        int startLine = lineNumber;
        int startColumn = columnNumber;
        StringBuilder lexeme = new StringBuilder();

        automaton.reset();

        while (currentPosition < input.length()) {
            char currentChar = input.charAt(currentPosition);

            try {
                // lookahead for next state belonging to same token class
                // if not output the token
                if (!automaton.hasTransition(currentChar)) {
                    break;
                }

                // Process the character
                automaton.transition(currentChar);
                lexeme.append(currentChar);
                updatePosition(currentChar);
                currentPosition++;
            } catch (IllegalArgumentException | IllegalStateException e) {
                break;
            }
        }

        // output the valid token
        if (automaton.isAcceptingState()) {
            String tokenText = lexeme.toString();
            TokenType tokenType = acceptingStatesToTokenTypes.get(automaton.getCurrentState());

            if (tokenType == TokenType.STRING) {
                // Strip quotes and unescape characters
                tokenText = StringUtils.stripQuotes(tokenText);
                tokenText = StringUtils.unescape(tokenText);
            }


            return new Token(tokenType, tokenText, startLine, startColumn);
        }

        String invalidText = input.substring(startPosition, Math.min(startPosition + 10, input.length()));
        throw new ScannerException("Invalid token at line " + startLine + ", column " + startColumn +
                ": '" + invalidText + "'");
    }

    @Override
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Token token;
        while (hasNext()) {
            token = nextToken();
            tokens.add(token);
        }
        return tokens;
    }

    private HashMap<State, TokenType> createTokenMapping() {
        HashMap<State, TokenType> acceptingStatesToTokenTypes = new HashMap<>();
        acceptingStatesToTokenTypes.put(automaton.getState("q1"), TokenType.IDENTIFIER);
        acceptingStatesToTokenTypes.put(automaton.getState("q2"), TokenType.INTEGER);
        acceptingStatesToTokenTypes.put(automaton.getState("q4"), TokenType.STRING);
        acceptingStatesToTokenTypes.put(automaton.getState("q5"), TokenType.DELETE);
        acceptingStatesToTokenTypes.put(automaton.getState("q6"), TokenType.DIVIDE);
        // //+ - recognized as comments
        acceptingStatesToTokenTypes.put(automaton.getState("q8"), TokenType.DELETE);
        acceptingStatesToTokenTypes.put(automaton.getState("q9"), TokenType.PERIOD);
        acceptingStatesToTokenTypes.put(automaton.getState("q10"), TokenType.COMMA);
        acceptingStatesToTokenTypes.put(automaton.getState("q11"), TokenType.VERTICAL_BAR);
        acceptingStatesToTokenTypes.put(automaton.getState("q12"), TokenType.AND);
        acceptingStatesToTokenTypes.put(automaton.getState("q13"), TokenType.MINUS);
        acceptingStatesToTokenTypes.put(automaton.getState("q14"), TokenType.CONDITION_SIGN);
        acceptingStatesToTokenTypes.put(automaton.getState("q15"), TokenType.GREATER_THAN);
        acceptingStatesToTokenTypes.put(automaton.getState("q16"), TokenType.GREATER_THAN_EQUAL);
        acceptingStatesToTokenTypes.put(automaton.getState("q17"), TokenType.LESS_THAN);
        acceptingStatesToTokenTypes.put(automaton.getState("q18"), TokenType.LESS_THAN_EQUAL);
        acceptingStatesToTokenTypes.put(automaton.getState("q19"), TokenType.PLUS);
        acceptingStatesToTokenTypes.put(automaton.getState("q20"), TokenType.MULTIPLY);
        acceptingStatesToTokenTypes.put(automaton.getState("q21"), TokenType.EXPONENT);
        acceptingStatesToTokenTypes.put(automaton.getState("q22"), TokenType.INFIX_FUNCTION);
        acceptingStatesToTokenTypes.put(automaton.getState("q23"), TokenType.OPEN_BRACKET);
        acceptingStatesToTokenTypes.put(automaton.getState("q24"), TokenType.CLOSE_BRACKET);
        acceptingStatesToTokenTypes.put(automaton.getState("q25"), TokenType.EQUAL);
        return acceptingStatesToTokenTypes;
    }
}

class ScannerException extends RuntimeException {
    public ScannerException(String message) {
        super(message);
    }
}