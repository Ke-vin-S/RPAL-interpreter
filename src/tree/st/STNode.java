package tree.st;

import tree.Node;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public abstract class STNode extends Node {
    public STNode(String label) {
        super(label);
    }

    public abstract void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper);
}
