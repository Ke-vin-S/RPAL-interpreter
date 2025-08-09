package tree.ast.variables;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.nonterminals.STComma;
import tree.st.STNode;
import utils.FCNSNode;

/**
 * Represents a list node in the AST, Corresponding to comma-separated values
 * such as function parameters, bindings, and tuples.
 *
 * <p>This node standardizes into an {@link STComma} node where each child
 * is a standardized version of the original AST list elements.
 */
public class ASTList extends ASTNode {
    public ASTList() {
        super(",");
    }

    /**
     * Standardizes this {@code ASTList} node into an {@link STComma} node.
     *
     * <p>This method transforms a comma-separated list in the AST into a standardized
     * comma node where each element of the list becomes a child node linked as siblings.
     *
     * <p>Conceptually, the input tree looks like:
     * <pre>
     *    ASTList (,)
     *       /  |  \
     *    c1  c2  c3 ...
     * </pre>
     *
     * And after standardization, it becomes:
     * <pre>
     *     STComma
     *      /  |  \
     *    c1  c2  c3 ...
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return standardizeListLike(currentNode, new STComma(), helper);
    }
}
