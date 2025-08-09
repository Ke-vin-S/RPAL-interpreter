package parser;

import tree.ast.ASTNode;
import utils.FCNSNode;

public interface Parser {
    void parse();
    FCNSNode<ASTNode> getAST();
}
