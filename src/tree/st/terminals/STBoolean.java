package tree.st.terminals;

import cse_machine.elements.control.BooleanControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

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
