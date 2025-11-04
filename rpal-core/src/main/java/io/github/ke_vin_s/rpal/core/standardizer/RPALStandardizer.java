package io.github.ke_vin_s.rpal.core.standardizer;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class RPALStandardizer implements Standardizer {

    public RPALStandardizer() {
    }

    @Override
    public FCNSNode<STNode> getST(FCNSNode<ASTNode> tree) {
        return STBuilder.build(tree);
    }
}
