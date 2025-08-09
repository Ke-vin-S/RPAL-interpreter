package tree.ast.definitions;

import tree.ast.ASTNode;
import tree.st.nonterminals.STAssign;
import standardizer.STBuilder;
import tree.st.STNode;
import utils.FCNSNode;

public class ASTAssign extends ASTNode {
    public ASTAssign() {
        super("=");
    }

    /**
     * <p>Input AST structure:</p>
     * <pre>
     *    ASTAssign ("=")
     *       /      \
     *   Variable   Expression
     * </pre>
     *
     * <p>After standardization, it transforms into an STAssign node:</p>
     * <pre>
     *    STAssign
     *      /    \
     * Variable Expression
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        if (currentNode.getFirstChild() == null || currentNode.getFirstChild().getNextSibling() == null) {
            throw new IllegalStateException("Assign node must have two children: a variable and an expression.");
        }

        // Get the two children: variable and expression
        FCNSNode<ASTNode> varNode = currentNode.getFirstChild();
        FCNSNode<ASTNode> exprNode = varNode.getNextSibling();

        // Standardize both
        FCNSNode<STNode> stdVar = helper.standardizeChild(varNode);
        FCNSNode<STNode> stdExpr = helper.standardizeChild(exprNode);

        // Create STAssign node and link children
        FCNSNode<STNode> assignNode = new FCNSNode<>(new STAssign());
        assignNode.setFirstChild(stdVar);
        stdVar.setNextSibling(stdExpr);

        return assignNode;
    }
}
