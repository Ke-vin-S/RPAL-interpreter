package tree.ast.operators;

import tree.ast.ASTNode;
import tree.st.nonterminals.STBinOp;
import standardizer.STBuilder;
import tree.st.STNode;
import utils.FCNSNode;

/**
 * Represents a binary operator node in the AST.
 * <p>The input AST structure looks like:
 * <pre>
 *    ASTBinOp (operator)
 *       /       \
 *    left       right
 * </pre>
 *
 * After standardization, it becomes:
 * <pre>
 *    STBinOp (operator)
 *       /       \
 *    left       right
 * </pre>
 */
public class ASTBinOp extends ASTNode {
    public ASTBinOp(BinaryOperator operator) {
        super(operator.toString());
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return standardizeAndLinkChildren(currentNode, new STBinOp(getLabel()), helper, 2);
    }
}
