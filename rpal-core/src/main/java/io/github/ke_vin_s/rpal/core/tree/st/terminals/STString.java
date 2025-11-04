package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.StringControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;
import io.github.ke_vin_s.rpal.core.utils.StringUtils;

public class STString extends STNode {
    private final String value;

    public STString(String value) {
        super("<STR:'" + StringUtils.escape(value) + "'>");
        this.value = value;
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new StringControlElement(value));
    }
}
