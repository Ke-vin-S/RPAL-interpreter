package tree.ast.ratorsandrands;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.terminals.STInteger;
import tree.st.STNode;
import utils.FCNSNode;

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
