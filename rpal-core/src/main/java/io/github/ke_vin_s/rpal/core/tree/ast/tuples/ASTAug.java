package io.github.ke_vin_s.rpal.core.tree.ast.tuples;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.tree.st.nonterminals.STAug;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class ASTAug extends ASTNode {
    public ASTAug() {
        super("aug");
    }

    /**
     * <p>Conceptually, the input AST tree looks like:
     * <pre>
     *    ASTAug (aug)
     *       /      \
     *    left     right
     * </pre>
     *
     * And after standardization, it becomes:
     * <pre>
     *    STAug
     *      /   \
     *   left  right
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return standardizeAndLinkChildren(currentNode, new STAug(), helper, 2);
    }
}
