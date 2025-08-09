package tree.ast.ratorsandrands;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.terminals.STDummy;
import tree.st.STNode;
import utils.FCNSNode;

public class ASTDummy extends ASTNode {
    public ASTDummy() {
        super("<dummy>");
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STDummy());
    }
}
