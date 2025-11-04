package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

// TODO; check proper execution
public class STEmpty extends STNode {
    public STEmpty() {
        super("()");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        // TODO
    }
}
