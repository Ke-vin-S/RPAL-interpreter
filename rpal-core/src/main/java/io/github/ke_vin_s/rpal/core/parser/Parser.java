package io.github.ke_vin_s.rpal.core.parser;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public interface Parser {
    void parse();
    FCNSNode<ASTNode> getAST();
}
