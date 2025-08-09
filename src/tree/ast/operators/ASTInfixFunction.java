package tree.ast.operators;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.nonterminals.STGamma;
import tree.st.STNode;
import utils.FCNSNode;

/**
 * Represents an infix function application node in the AST.
 *
 * <p>This node models expressions of the form {@code E1 @ F E2}, where
 * {@code E1} and {@code E2} are operands and {@code F} is a function applied infix.
 */

public class ASTInfixFunction extends ASTNode {
    public ASTInfixFunction() {
        super("@");
    }

    /**
     * <p>The input AST looks like:
     * <pre>
     *    ASTInfixFunction (@)
     *       /      |       \
     *     E1       F        E2
     * </pre>
     *
     * After standardization, the structure is:
     * <pre>
     *     STGamma
     *      /     \
     *   STGamma   E2
     *    /    \
     *   F      E1
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // Get the three children: left operand, function, right operand
        FCNSNode<ASTNode> leftOperand = currentNode.getFirstChild();
        FCNSNode<ASTNode> functionNode = (leftOperand != null) ? leftOperand.getNextSibling() : null;
        FCNSNode<ASTNode> rightOperand = (functionNode != null) ? functionNode.getNextSibling() : null;

        // Validate structure
        if (leftOperand == null || functionNode == null || rightOperand == null) {
            throw new IllegalStateException("Infix function must have pattern: E1 @ F E2");
        }
        if (rightOperand.getNextSibling() != null) {
            throw new IllegalStateException("Infix function can only have three components");
        }

        // Standardize all components
        FCNSNode<STNode> stLeft = helper.standardizeChild(leftOperand);
        FCNSNode<STNode> stFunction = helper.standardizeChild(functionNode);
        FCNSNode<STNode> stRight = helper.standardizeChild(rightOperand);

        // Build gamma (gamma F E1) E2 structure
        FCNSNode<STNode> innerGamma = new FCNSNode<>(new STGamma());
        innerGamma.setFirstChild(stFunction);
        stFunction.setNextSibling(stLeft);

        FCNSNode<STNode> outerGamma = new FCNSNode<>(new STGamma());
        outerGamma.setFirstChild(innerGamma);
        innerGamma.setNextSibling(stRight);

        return outerGamma;
    }
}