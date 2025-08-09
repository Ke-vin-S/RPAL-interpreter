package tree.ast.expressions;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.nonterminals.STGamma;
import tree.st.nonterminals.STLambda;
import tree.st.STNode;
import utils.FCNSNode;


public class ASTWhere extends ASTNode {
    public ASTWhere() {
        super("where");
    }

    /**
     * <p>The input AST structure looks like:
     * <pre>
     *    ASTWhere ("where")
     *       /        \
     *      E       (X = F)
     * </pre>
     *
     * After standardization, it becomes:
     * <pre>
     *     STGamma
     *       /     \
     *   STLambda   F
     *      / \
     *     X    E
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // Get the two main children: E and the definition (X = F)
        FCNSNode<ASTNode> bodyNode = currentNode.getFirstChild();
        FCNSNode<ASTNode> bindingNode = (bodyNode != null) ? bodyNode.getNextSibling() : null;

        if (bodyNode == null || bindingNode == null) {
            throw new IllegalStateException("Where node must have pattern 'E where X = F'");
        }

        FCNSNode<STNode> stBinding = helper.standardizeChild(bindingNode);

        // Verify the standardized binding is an '=' node
        if (!stBinding.getData().getLabel().equals("=")) {
            throw new IllegalStateException("Let binding must standardize to '=' node");
        }

        // Get X and F from the equals node
        FCNSNode<STNode> stX = stBinding.getFirstChild();
        FCNSNode<STNode> stF = (stX != null) ? stX.getNextSibling() : null;

        if (stX == null || stF == null) {
            throw new IllegalStateException("Where definition must have pattern 'X = F'");
        }

        // Standardize all components
        FCNSNode<STNode> stE = helper.standardizeChild(bodyNode);

        // Build the equivalent let structure: let X = F in E
        // First create lambda X.E
        FCNSNode<STNode> lambdaNode = new FCNSNode<>(new STLambda());
        lambdaNode.setFirstChild(stX);
        stX.setNextSibling(stE);

        // Then create gamma (lambda X.E) F
        FCNSNode<STNode> gammaNode = new FCNSNode<>(new STGamma());
        gammaNode.setFirstChild(lambdaNode);
        lambdaNode.setNextSibling(stF);

        return gammaNode;
    }
}