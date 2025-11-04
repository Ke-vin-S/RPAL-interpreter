package io.github.ke_vin_s.rpal.core.tree.ast.ratorsandrands;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.terminals.STInteger;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class ASTInteger extends ASTNode {
    private final int value;
    public ASTInteger(int value) {
        super("<INT:" + value + ">");
        this.value = value;
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STInteger(value));
    }
}
