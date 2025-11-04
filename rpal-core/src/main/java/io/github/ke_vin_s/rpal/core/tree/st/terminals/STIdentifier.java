package io.github.ke_vin_s.rpal.core.tree.st.terminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.IdentifierControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STIdentifier extends STNode {
    private final String name;
    public STIdentifier(String name) {
        super("<ID:" + name + ">");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new IdentifierControlElement(name));
    }
}
