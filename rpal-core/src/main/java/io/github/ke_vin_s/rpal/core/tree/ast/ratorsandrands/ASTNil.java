package io.github.ke_vin_s.rpal.core.tree.ast.ratorsandrands;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.terminals.STNil;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class ASTNil extends ASTNode {
    public ASTNil() {
        super("nil");
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STNil());
    }

    @Override
    public String toString() {
        return "<nil>";
    }
}
