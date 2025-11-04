package io.github.ke_vin_s.rpal.core.tree.st;

import io.github.ke_vin_s.rpal.core.tree.Node;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public abstract class STNode extends Node {
    public STNode(String label) {
        super(label);
    }

    public abstract void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper);
}
