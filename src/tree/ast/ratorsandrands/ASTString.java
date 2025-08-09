package tree.ast.ratorsandrands;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.STNode;
import tree.st.terminals.STString;
import utils.FCNSNode;
import utils.StringUtils;

public class ASTString extends ASTNode {
    private final String value;

    public ASTString(String value) {
        super("<STR:'" + StringUtils.escape(value) + "'>");
        this.value = value;
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STString(value)); // Use raw value in ST
    }
}
