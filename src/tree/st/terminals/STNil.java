package tree.st.terminals;

import cse_machine.elements.control.NilControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public class STNil extends STNode {
    public STNil() {
        super("<nil>");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        helper.addControlElement(new NilControlElement());
    }
}
