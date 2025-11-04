package io.github.ke_vin_s.rpal.core.tree.ast.ratorsandrands;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.nonterminals.STGamma;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

/**
 * Standardizes this function application (gamma) AST node into an STGamma node.
 */

public class ASTFunctionApplication extends ASTNode {
    public ASTFunctionApplication() {
        super("gamma");
    }

    /**
     * <p>Example structure before standardization:
     * <pre>
     *     ASTFunctionApplication (gamma)
     *        /          \
     *    function     argument
     * </pre>
     *
     * <p>After standardization:
     * <pre>
     *     STGamma
     *      /    \
     *  function argument
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        if (currentNode == null || currentNode.getData() == null) {
            throw new IllegalStateException("Gamma node is not properly linked to the AST.");
        }

        FCNSNode<STNode> stGamma = new FCNSNode<>(new STGamma());

        FCNSNode<ASTNode> astChild = currentNode.getFirstChild();
        if (astChild != null) {
            // First child: function
            FCNSNode<STNode> stFunction = helper.standardizeChild(astChild);
            stGamma.setFirstChild(stFunction);

            // Second child: argument (if exists)
            FCNSNode<ASTNode> astArg = astChild.getNextSibling();
            if (astArg != null) {
                FCNSNode<STNode> stArg = helper.standardizeChild(astArg);
                stFunction.setNextSibling(stArg);
            }
        }

        return stGamma;
    }
}
