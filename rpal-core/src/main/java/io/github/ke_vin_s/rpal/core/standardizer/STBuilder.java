package io.github.ke_vin_s.rpal.core.standardizer;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STBuilder {

    public interface StandardizationHelper {
        FCNSNode<STNode> standardizeChild(FCNSNode<ASTNode> child);
    }

    public static FCNSNode<STNode> build(FCNSNode<ASTNode> astNode) {
        if (astNode == null) {
            throw new IllegalArgumentException("AST node cannot be null");
        }

        return standardizeNode(astNode, new StandardizationHelper() {
            @Override
            public FCNSNode<STNode> standardizeChild(FCNSNode<ASTNode> child) {
                return standardizeNode(child, this);
            }
        });
    }

    private static FCNSNode<STNode> standardizeNode(FCNSNode<ASTNode> astNode, StandardizationHelper helper) {
        if (astNode == null) {
            return null;
        }

        // Delegate standardization to the ASTNode, passing the helper
        return astNode.getData().standardize(astNode, helper);
    }
}
