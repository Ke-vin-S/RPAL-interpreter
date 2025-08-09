package tree.ast.operators;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.STNode;
import tree.st.nonterminals.STUnOp;
import utils.FCNSNode;

/**
 * Represents unary operator node in the AST.
 *
 * <p>The input AST structure looks like:
 * <pre>
 *    ASTUnOp (operator)
 *        |
 *      operand
 * </pre>
 *
 * After standardization, it becomes:
 * <pre>
 *    STUnOp (operator)
 *        |
 *      standardized operand
 * </pre>
 */
public class ASTUnOp extends ASTNode {
    public ASTUnOp(UnaryOperator operator) {
        super(operator.toString());
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return standardizeAndLinkChildren(currentNode, new STUnOp(getLabel()), helper, 1);
    }
}
