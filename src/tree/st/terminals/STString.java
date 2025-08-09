package tree.st.terminals;

import cse_machine.elements.control.StringControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;
import utils.StringUtils;

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
