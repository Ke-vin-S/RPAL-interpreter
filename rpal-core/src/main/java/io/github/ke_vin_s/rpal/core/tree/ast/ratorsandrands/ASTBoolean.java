package io.github.ke_vin_s.rpal.core.tree.ast.ratorsandrands;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.tree.st.terminals.STBoolean;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class ASTBoolean extends ASTNode {
    private final boolean value;
    public ASTBoolean(Boolean value) {
        super(value != null ? "<" + value + ">" : throwIllegalArgumentException());
        this.value = value;
    }

    private static String throwIllegalArgumentException() {
        throw new IllegalArgumentException("value cannot be null");
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // For leaf nodes, just create the corresponding ST node with no children
        return new FCNSNode<>(new STBoolean(value));
    }
}
