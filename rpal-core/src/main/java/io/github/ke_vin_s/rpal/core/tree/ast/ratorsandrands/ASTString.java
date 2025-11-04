package io.github.ke_vin_s.rpal.core.tree.ast.ratorsandrands;

import io.github.ke_vin_s.rpal.core.tree.ast.ASTNode;
import io.github.ke_vin_s.rpal.core.standardizer.STBuilder;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.st.terminals.STString;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;
import io.github.ke_vin_s.rpal.core.utils.StringUtils;

public class ASTString extends ASTNode {
    private final String value;

    public ASTString(String value) {
        super("<STR:'" + StringUtils.escape(value) + "'>");
        this.value = value;
    }

    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return new FCNSNode<>(new STString(value)); // Use raw value in ST
    }
}
