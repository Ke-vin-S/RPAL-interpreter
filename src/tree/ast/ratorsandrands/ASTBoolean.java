package tree.ast.ratorsandrands;

import tree.ast.ASTNode;
import tree.st.terminals.STBoolean;
import standardizer.STBuilder;
import tree.st.STNode;
import utils.FCNSNode;

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
