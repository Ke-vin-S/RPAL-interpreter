package scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPALScreener implements Screener {
    Map<String, TokenType> keywords;

    public RPALScreener() {
        keywords = new HashMap<>();
        keywords.put("let", TokenType.KEYWORD_LET);
        keywords.put("in", TokenType.KEYWORD_IN);
        keywords.put("fn", TokenType.KEYWORD_FN);
        keywords.put("where", TokenType.KEYWORD_WHERE);

        keywords.put("aug", TokenType.KEYWORD_AUG);

        keywords.put("or", TokenType.OR);
        keywords.put("not", TokenType.NOT);
        keywords.put("gr", TokenType.GREATER_THAN);
        keywords.put("ge", TokenType.GREATER_THAN_EQUAL);
        keywords.put("ls", TokenType.LESS_THAN);
        keywords.put("le", TokenType.LESS_THAN_EQUAL);
        keywords.put("eq", TokenType.EQUAL);
        keywords.put("ne", TokenType.NOT_EQUAL);

        // no arithmetic

        // these are not keywords
        keywords.put("true", TokenType.BOOLEAN);
        keywords.put("false", TokenType.BOOLEAN);
        keywords.put("nil", TokenType.NIL);
        keywords.put("dummy", TokenType.DUMMY);

        keywords.put("within", TokenType.KEYWORD_WITHIN);
        keywords.put("and", TokenType.AND_SIMULTANEOUS_DEFINITION);
        keywords.put("rec", TokenType.REC);
    }

    @Override
    public List<Token> screen(List<Token> tokens) {
        List<Token> out = new ArrayList<>();
        for (Token token : tokens) {
            // skip comments and whitespaces
            if (token.type() == TokenType.DELETE) {
                continue;
            }

            // handle keywords
            TokenType keywordType = keywords.get(token.lexeme());
            if (keywordType != null && token.type() == TokenType.IDENTIFIER) {
                out.add(new Token(keywordType, token.lexeme(), token.line(), token.column()));
            } else {
                out.add(token);
            }
        }
        // add eof only if there is comment or eol at the end
        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).type() == TokenType.DELETE) {
            Token last = tokens.get(tokens.size() - 1);
            out.add(new Token(TokenType.EOF, "EOF", last.line(), last.column()));
        }
        return out;
    }
}
