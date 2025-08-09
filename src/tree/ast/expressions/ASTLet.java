package tree.ast.expressions;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.nonterminals.STGamma;
import tree.st.STNode;
import tree.st.nonterminals.STLambda;
import utils.FCNSNode;

public class ASTLet extends ASTNode {
    public ASTLet() {
        super("let");
    }

    /**
     * <p>The input AST structure looks like:
     * <pre>
     *    ASTLet ("let")
     *       /        \
     *   (X = E)       P
     * </pre>
     *
     * After standardization, it becomes:
     * <pre>
     *    STGamma
     *      /    \
     *  STLambda   E
     *     / \
     *    X   P
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // Get the binding and body parts
        FCNSNode<ASTNode> bindingNode = currentNode.getFirstChild();
        FCNSNode<ASTNode> bodyNode = (bindingNode != null) ? bindingNode.getNextSibling() : null;

        if (bindingNode == null || bodyNode == null) {
            throw new IllegalStateException("Let node must have both a binding and a body");
        }

        // Standardize the binding node first - it should become an '=' node
        FCNSNode<STNode> stBinding = helper.standardizeChild(bindingNode);

        // Verify the standardized binding is an '=' node
        if (!stBinding.getData().getLabel().equals("=")) {
            throw new IllegalStateException("Let binding must standardize to '=' node");
        }

        // Get X and E from the standardized binding
        FCNSNode<STNode> stX = stBinding.getFirstChild();
        FCNSNode<STNode> stE = (stX != null) ? stX.getNextSibling() : null;

        if (stX == null || stE == null) {
            throw new IllegalStateException("Let binding must have pattern X = E");
        }

        // Standardize the body
        FCNSNode<STNode> stP = helper.standardizeChild(bodyNode);

        // Build lambda X.P
        FCNSNode<STNode> lambdaNode = new FCNSNode<>(new STLambda());
        lambdaNode.setFirstChild(stX);
        stX.setNextSibling(stP);

        // Build gamma (lambda X.P) E
        FCNSNode<STNode> gammaNode = new FCNSNode<>(new STGamma());
        gammaNode.setFirstChild(lambdaNode);
        lambdaNode.setNextSibling(stE);

        return gammaNode;
    }
}