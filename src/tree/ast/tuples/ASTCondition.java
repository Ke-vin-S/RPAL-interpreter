package tree.ast.tuples;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.nonterminals.STCondition;
import tree.st.STNode;
import utils.FCNSNode;

/**
 * Represents a conditional node in the AST, corresponding to a conditional expression
 * with a condition, a true-branch, and a false-branch.
 */
public class ASTCondition extends ASTNode {
    public ASTCondition() {
        super("->");
    }

    /**
     * <p>Conceptually, the input AST tree looks like:
     * <pre>
     *    ASTCondition (->)
     *       /      |      \
     *   condition true   false
     * </pre>
     *
     * And after standardization, it becomes:
     * <pre>
     *    STCondition
     *       /      |      \
     *   condition true   false
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // Get the three children: condition, true-branch, false-branch
        FCNSNode<ASTNode> condition = currentNode.getFirstChild();
        FCNSNode<ASTNode> trueBranch = (condition != null) ? condition.getNextSibling() : null;
        FCNSNode<ASTNode> falseBranch = (trueBranch != null) ? trueBranch.getNextSibling() : null;

        // Standardize all children
        FCNSNode<STNode> stCondition = (condition != null) ? helper.standardizeChild(condition) : null;
        FCNSNode<STNode> stTrue = (trueBranch != null) ? helper.standardizeChild(trueBranch) : null;
        FCNSNode<STNode> stFalse = (falseBranch != null) ? helper.standardizeChild(falseBranch) : null;

        FCNSNode<STNode> stNode = new FCNSNode<>(new STCondition());

        // Rebuild the structure with standardized children
        if (stCondition != null) {
            stNode.setFirstChild(stCondition);
            if (stTrue != null) {
                stCondition.setNextSibling(stTrue);
                if (stFalse != null) {
                    stTrue.setNextSibling(stFalse);
                }
            }
        }

        return stNode;
    }
}
