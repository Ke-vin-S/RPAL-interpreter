package tree.ast.ratorsandrands;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.terminals.STNil;
import tree.st.STNode;
import utils.FCNSNode;

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
