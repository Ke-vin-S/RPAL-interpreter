package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.DummyControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STDummy extends STNode {
    public STDummy() {
        super("<dummy>");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new DummyControlElement());
    }
}
