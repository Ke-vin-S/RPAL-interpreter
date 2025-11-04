package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.BooleanControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STBoolean extends STNode {
    private final boolean value;

    public STBoolean(Boolean value) {
        super(value != null ? "<" + value + ">" : throwIllegalArgumentException());
        this.value = value;
    }

    private static String throwIllegalArgumentException() {
        throw new IllegalArgumentException("value cannot be null");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new BooleanControlElement(value));
    }
}
