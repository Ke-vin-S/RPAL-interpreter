package io.github.ke_vin_s.rpal.core.tree.ast.operators;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.st.nonterminals.STUnOp;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

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
