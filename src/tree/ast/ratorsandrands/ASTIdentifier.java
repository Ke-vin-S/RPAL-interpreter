package tree.ast.ratorsandrands;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.terminals.STIdentifier;
import tree.st.STNode;
import utils.FCNSNode;

public class ASTIdentifier extends ASTNode {
    private final String name;
    public ASTIdentifier(String name) {
        super("<ID:" + name + ">");
        this.name = name;
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STIdentifier(name));
    }

}
