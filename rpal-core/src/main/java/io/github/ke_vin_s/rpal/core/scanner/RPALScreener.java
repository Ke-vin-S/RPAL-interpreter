package io.github.ke_vin_s.rpal.core.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPALScreener implements Screener {
    private final Map<String, TokenType> keywords;
    private final Map<String, TokenType> symbolRefinements;

    public RPALScreener() {
        keywords = new HashMap<>();
        // --- Structural Keywords ---
        keywords.put("let", TokenType.KEYWORD_LET);
        keywords.put("in", TokenType.KEYWORD_IN);
        keywords.put("fn", TokenType.KEYWORD_FN);
        keywords.put("where", TokenType.KEYWORD_WHERE);
        keywords.put("within", TokenType.KEYWORD_WITHIN);
        keywords.put("and", TokenType.AND_SIMULTANEOUS_DEFINITION);
        keywords.put("rec", TokenType.REC);
        keywords.put("aug", TokenType.KEYWORD_AUG);

        // --- Boolean & Logical (Named) ---
        keywords.put("or", TokenType.OR);
        keywords.put("not", TokenType.NOT);
        keywords.put("gr", TokenType.GREATER_THAN);
        keywords.put("ge", TokenType.GREATER_THAN_EQUAL);
        keywords.put("ls", TokenType.LESS_THAN);
        keywords.put("le", TokenType.LESS_THAN_EQUAL);
        keywords.put("eq", TokenType.EQUAL);
        keywords.put("ne", TokenType.NOT_EQUAL);

        // --- Constants ---
        keywords.put("true", TokenType.BOOLEAN);
        keywords.put("false", TokenType.BOOLEAN);
        keywords.put("nil", TokenType.NIL);
        keywords.put("dummy", TokenType.DUMMY);

        // --- Symbol Refinements for OP_SINGLE ---
        symbolRefinements = new HashMap<>();
        symbolRefinements.put(".", TokenType.PERIOD);
        symbolRefinements.put(",", TokenType.COMMA);
        symbolRefinements.put("|", TokenType.VERTICAL_BAR);
        symbolRefinements.put("&", TokenType.AND);
        symbolRefinements.put("+", TokenType.PLUS);
        symbolRefinements.put("@", TokenType.INFIX_FUNCTION);
        symbolRefinements.put("(", TokenType.OPEN_BRACKET);
        symbolRefinements.put(")", TokenType.CLOSE_BRACKET);
        symbolRefinements.put("=", TokenType.EQUAL);
        symbolRefinements.put(";", TokenType.SEMICOLON);
    }

    @Override
    public List<Token> screen(List<Token> tokens) {
        List<Token> out = new ArrayList<>();
        Token lastSeenToken = null;

        for (Token token : tokens) {
            // Skip comments and whitespaces
            if (token.type() == TokenType.DELETE) {
                continue;
            }

            Token processedToken = token;

            // Refine Identifiers into Keywords
            if (token.type() == TokenType.IDENTIFIER) {
                TokenType type = keywords.get(token.lexeme());
                if (type != null) {
                    processedToken = new Token(type, token.lexeme(), token.line(), token.column());
                }
            }
            // Refine generic Operators (OP_SINGLE) into specific symbols
            else if (token.type() == TokenType.OPERATOR) {
                TokenType type = symbolRefinements.get(token.lexeme());
                if (type != null) {
                    processedToken = new Token(type, token.lexeme(), token.line(), token.column());
                }
            }

            out.add(processedToken);
            lastSeenToken = processedToken;
        }

        // Always add EOF at the end for the Parser
        if (lastSeenToken != null) {
            out.add(new Token(TokenType.EOF, "EOF", lastSeenToken.line(), lastSeenToken.column()));
        } else {
            out.add(new Token(TokenType.EOF, "EOF", 1, 1));
        }

        return out;
    }
}