package tree.ast.tuples;

import tree.ast.ASTNode;
import tree.st.nonterminals.STAug;
import standardizer.STBuilder;
import tree.st.STNode;
import utils.FCNSNode;

public class ASTAug extends ASTNode {
    public ASTAug() {
        super("aug");
    }

    /**
     * <p>Conceptually, the input AST tree looks like:
     * <pre>
     *    ASTAug (aug)
     *       /      \
     *    left     right
     * </pre>
     *
     * And after standardization, it becomes:
     * <pre>
     *    STAug
     *      /   \
     *   left  right
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return standardizeAndLinkChildren(currentNode, new STAug(), helper, 2);
    }
}
