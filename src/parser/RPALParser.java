package parser;

import tree.ast.definitions.*;
import tree.ast.expressions.ASTLambda;
import tree.ast.expressions.ASTLet;
import tree.ast.expressions.ASTWhere;
import tree.ast.ASTNode;
import tree.ast.operators.*;
import tree.ast.ratorsandrands.*;
import tree.ast.tuples.ASTAug;
import tree.ast.tuples.ASTCondition;
import tree.ast.tuples.ASTTau;
import tree.ast.variables.ASTEmpty;
import tree.ast.variables.ASTList;
import scanner.Token;
import scanner.TokenType;
import utils.FCNSNode;

import java.util.Iterator;
import java.util.List;

public class RPALParser implements Parser {
    private final Iterator<Token> tokenIterator;
    private Token nextToken;
    private TokenType nextTokenType;
    private final ASTBuilder astBuilder;

    public RPALParser(List<Token> tokens, ASTBuilder astBuilder) {
        this.tokenIterator = tokens.iterator();
        nextTokenType = getNextTokenType();
        this.astBuilder = astBuilder;
    }

    @Override
    public void parse() {
            E();
    }

    @Override
    public FCNSNode<ASTNode> getAST() {
        return astBuilder.get();
    }

    private void read(TokenType tokenType) {
        if (tokenType != nextTokenType) {
            throw new ParserException("Expected " + tokenType + " but got " + nextTokenType);
        }
        switch (nextTokenType) {
            case IDENTIFIER:
                astBuilder.buildTree(new ASTIdentifier(nextToken.lexeme()), 0);
                break;
            case INTEGER:
                astBuilder.buildTree(new ASTInteger(Integer.parseInt(nextToken.lexeme())), 0);
                break;
            case STRING:
                astBuilder.buildTree(new ASTString(nextToken.lexeme()), 0);
                break;
            case BOOLEAN:
                astBuilder.buildTree(new ASTBoolean(Boolean.parseBoolean(nextToken.lexeme())), 0);
                break;
        }
        this.nextTokenType = getNextTokenType();
    }

    private TokenType getNextTokenType(){
        nextToken = tokenIterator.next();
        return nextToken.type();
    }

    // recursion
    private void E(){
        switch (nextTokenType){
            case KEYWORD_LET:
                read(TokenType.KEYWORD_LET);
                D();
                read(TokenType.KEYWORD_IN);
                E();
                astBuilder.buildTreeOrdered(new ASTLet(), 2);
                break;
            case KEYWORD_FN:
                read(TokenType.KEYWORD_FN);
                int n = 1;
                Vb();
                // Here use available symbols for Vb: is it not?
                // in ast, as it seems, lambda goes for more than two symbols, function definition
                while(nextTokenType == TokenType.IDENTIFIER || nextTokenType == TokenType.OPEN_BRACKET){
                    Vb();
                    n++;
                }
                read(TokenType.PERIOD);
                E();
                astBuilder.buildTreeOrdered(new ASTLambda(), n + 1);
                break;
            default:
                Ew();
                break;
        }
    }

    private void Ew(){
        T();
        if(nextTokenType == TokenType.KEYWORD_WHERE){
            read(TokenType.KEYWORD_WHERE);
            Dr();
            astBuilder.buildTreeOrdered(new ASTWhere(), 2);
        }
    }

    private void T(){
        Ta();
        int n = 1;
        while(nextTokenType == TokenType.COMMA){
            read(TokenType.COMMA);
            Ta();
            n++;
        }
        if (n > 1){
            astBuilder.buildTreeOrdered(new ASTTau(), n);
        }
    }

    // Aug is a binary node, so need a different way of managing
    private void Ta(){
        Tc();
        while (nextTokenType == TokenType.KEYWORD_AUG){
            read(TokenType.KEYWORD_AUG);
            Tc();
            astBuilder.buildTreeOrdered(new ASTAug(), 2);
        }
    }

    // recursion
    private void Tc(){
        B();
        if (nextTokenType == TokenType.CONDITION_SIGN){
            read(TokenType.CONDITION_SIGN);
            Tc();
            read(TokenType.VERTICAL_BAR);
            Tc();
            astBuilder.buildTreeOrdered(new ASTCondition(), 3);
        }
    }

    private void B(){
        Bt();
        while (nextTokenType == TokenType.OR){
            read(TokenType.OR);
            Bt();
            astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.OR), 2);
        }
    }

    private void Bt(){
        Bs();
        while (nextTokenType == TokenType.AND){
            read(TokenType.AND);
            Bs();
            astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.AND), 2);
        }
    }

    // Here, different because not should be a parent
    private void Bs(){
        if (nextTokenType == TokenType.NOT){
            read(TokenType.NOT);
            Bp();
            astBuilder.buildTree(new ASTUnOp(UnaryOperator.NOT), 1);
        }
        else {
            Bp();
        }
    }

    private void Bp(){
        A();
        switch (nextTokenType){
            case GREATER_THAN:
                read(TokenType.GREATER_THAN);
                A();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.GREATER_THAN), 2);
                break;
            case GREATER_THAN_EQUAL:
                read(TokenType.GREATER_THAN_EQUAL);
                A();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.GREATER_THAN_OR_EQUAL), 2);
                break;
            case LESS_THAN:
                read(TokenType.LESS_THAN);
                A();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.LESS_THAN), 2);
                break;
            case LESS_THAN_EQUAL:
                read(TokenType.LESS_THAN_EQUAL);
                A();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.LESS_THAN_OR_EQUAL), 2);
                break;
            case EQUAL:
                read(TokenType.EQUAL);
                A();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.EQUALS), 2);
                break;
            case NOT_EQUAL:
                read(TokenType.NOT_EQUAL);
                A();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.NOT_EQUALS), 2);
                break;
            default:
                break;
        }
    }

    private void A(){
        if (nextTokenType == TokenType.PLUS){
            read(TokenType.PLUS);
            At();
        }
        else if (nextTokenType == TokenType.MINUS){
            read(TokenType.MINUS);
            At();
            astBuilder.buildTree(new ASTUnOp(UnaryOperator.NEGATE), 1);
        }
        else {
            At();
        }
        while (nextTokenType == TokenType.PLUS || nextTokenType == TokenType.MINUS){
            if (nextTokenType == TokenType.PLUS){
                read(TokenType.PLUS);
                At();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.ADD), 2);
            }
            else {
                read(TokenType.MINUS);
                At();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.SUBTRACT), 2);
            }
        }
    }

    private void At(){
        Af();
        while (nextTokenType == TokenType.DIVIDE || nextTokenType == TokenType.MULTIPLY){
            if (nextTokenType == TokenType.DIVIDE){
                read(TokenType.DIVIDE);
                Af();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.DIVIDE), 2);
            }
            else {
                read(TokenType.MULTIPLY);
                Af();
                astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.MULTIPLY), 2);
            }
        }
    }

    private void Af(){
        Ap();
        if (nextTokenType == TokenType.EXPONENT){
            read(TokenType.EXPONENT);
            Af();
            astBuilder.buildTreeOrdered(new ASTBinOp(BinaryOperator.EXPONENT), 2);
        }
    }

    private void Ap(){
        R();
        while (nextTokenType == TokenType.INFIX_FUNCTION){
            read(TokenType.INFIX_FUNCTION);
            read(TokenType.IDENTIFIER);
            R();
            astBuilder.buildTreeOrdered(new ASTInfixFunction(), 3);
        }
    }

    // revise this
    private void R(){
        Rn();
        while(nextTokenType == TokenType.IDENTIFIER || nextTokenType == TokenType.INTEGER
                || nextTokenType == TokenType.STRING || nextTokenType == TokenType.BOOLEAN
                || nextTokenType ==TokenType.NIL || nextTokenType == TokenType.OPEN_BRACKET
                || nextTokenType == TokenType.DUMMY){
            Rn();
            // gamma
            astBuilder.buildTreeOrdered(new ASTFunctionApplication(), 2);
        }
    }

    private void Rn(){
        switch (nextTokenType){
            case IDENTIFIER:
                read(TokenType.IDENTIFIER);
                break;
            case INTEGER:
                read(TokenType.INTEGER);
                break;
            case STRING:
                read(TokenType.STRING);
                break;
            case BOOLEAN:
                read(TokenType.BOOLEAN);
                break;
            case NIL:
                read(TokenType.NIL);
                astBuilder.buildTree(new ASTNil(), 0);
                break;
            case OPEN_BRACKET:
                read(TokenType.OPEN_BRACKET);
                E();
                read(TokenType.CLOSE_BRACKET);
                break;
            case DUMMY:
                read(TokenType.DUMMY);
                astBuilder.buildTree(new ASTDummy(), 0);
                break;
            default:
                break;
        }
    }

    private void D(){
        Da();
        if (nextTokenType == TokenType.KEYWORD_WITHIN){
            read(TokenType.KEYWORD_WITHIN);
            D();
            astBuilder.buildTreeOrdered(new ASTWithin(), 2);
        }
    }

    private void Da(){
        Dr();
        int n = 1;
        while (nextTokenType == TokenType.AND_SIMULTANEOUS_DEFINITION){
            read(TokenType.AND_SIMULTANEOUS_DEFINITION);
            Dr();
            n++;
        }
        if (n > 1){
            astBuilder.buildTreeOrdered(new ASTSimultaneousDefinition(), n);
        }
    }

    // is this the way?
    private void Dr(){
        if (nextTokenType == TokenType.REC){
            read(TokenType.REC);
            Db();
            astBuilder.buildTree(new ASTRecursiveFunction(), 1);
        }
        else {
            Db();
        }
    }

    private void Db(){
        switch (nextTokenType){
            // If open bracket, no doubt
            case OPEN_BRACKET:
                read(TokenType.OPEN_BRACKET);
                D();
                read(TokenType.CLOSE_BRACKET);
                break;
            // 2 steps look ahead needed here
            case IDENTIFIER:
                read(TokenType.IDENTIFIER);
                int n = 1;
                if (nextTokenType == TokenType.IDENTIFIER || nextTokenType == TokenType.OPEN_BRACKET){
                    int VbCount = 0;
                    do {
                        Vb();
                        VbCount++;
                    } while (nextTokenType != TokenType.EQUAL);
                    read(TokenType.EQUAL);
                    E();
                    astBuilder.buildTreeOrdered(new ASTFunctionForm(), n + VbCount + 1);
                    break;
                }
                // already read one identifier
                if (nextTokenType == TokenType.COMMA){
                    Vl();
                }
                read(TokenType.EQUAL);
                E();
                astBuilder.buildTreeOrdered(new ASTAssign(), 2);
                break;
            default:
                break;
        }
    }

    private void Vb(){
        switch (nextTokenType){
            case IDENTIFIER:
                read(TokenType.IDENTIFIER);
                break;
            case OPEN_BRACKET:
                read(TokenType.OPEN_BRACKET);
                if (nextTokenType == TokenType.CLOSE_BRACKET){
                    read(TokenType.CLOSE_BRACKET);
                    // terminal node
                    astBuilder.buildTree(new ASTEmpty(), 0);
                    break;
                }
                // need to add one identifier read because the first one is not read in Vl()
                read(TokenType.IDENTIFIER);
                Vl();
                read(TokenType.CLOSE_BRACKET);
                break;
            default:
                break;
        }
    }

    private void Vl(){
        // one identifier is already read, need to read the next set
        int n = 1;
        while (nextTokenType == TokenType.COMMA){
            read(TokenType.COMMA);
            read(TokenType.IDENTIFIER);
            n++;
        }
        if (n > 1){
            astBuilder.buildTreeOrdered(new ASTList(), n);
        }
    }
}

class ParserException extends RuntimeException {
    public ParserException(String message) {
        super(message);
    }
}