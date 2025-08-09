package tree.st.terminals;

import cse_machine.elements.control.IdentifierControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

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
