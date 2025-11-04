package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.IntegerControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STInteger extends STNode {
    private final int value;
    public STInteger(int value) {
        super("<INT:" + value + ">");
        this.value = value;
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new IntegerControlElement(value));
    }
}
