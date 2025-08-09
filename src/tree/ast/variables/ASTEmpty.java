package tree.ast.variables;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.terminals.STEmpty;
import tree.st.STNode;
import utils.FCNSNode;

/**
 * Represents an empty expression in the AST, corresponding to {@code ()}.
 *
 * <p>This node standardizes into an {@link STEmpty} node.
 */
public class ASTEmpty extends ASTNode {
    public ASTEmpty() {
        super("()");
    }

    /**
     * Standardizes this empty AST node into an {@link STEmpty} node.
     *
     * <p>Conceptually, the transformation looks like:
     * <pre>
     *     ASTEmpty
     *        ()
     *         ↓
     *      STEmpty
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STEmpty());
    }
}
