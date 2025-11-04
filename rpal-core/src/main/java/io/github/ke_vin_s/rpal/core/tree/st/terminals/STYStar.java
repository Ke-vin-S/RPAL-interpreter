package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.YStarControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STYStar extends STNode {
    public STYStar() {
        super("<Y*>");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        // No special behavior; treated like a regular terminal
        helper.addControlElement(new YStarControlElement());
    }
}
