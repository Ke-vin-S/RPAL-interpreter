package tree.st.terminals;

import cse_machine.elements.control.IntegerControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

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
