package tree.st.terminals;

import cse_machine.elements.control.DummyControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public class STDummy extends STNode {
    public STDummy() {
        super("<dummy>");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new DummyControlElement());
    }
}
