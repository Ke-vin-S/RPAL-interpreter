package tree.st.nonterminals;

import cse_machine.elements.control.TauControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public class STTau extends STNode {
    public STTau() {
        super("tau");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STTau node's tree node is not set");
        }

        // 1. Add Tau control element with 0 initially
        TauControlElement tauElement = new TauControlElement(0);
        helper.addControlElement(tauElement);

        // 2. Build control structure for all children
        int count = 0;
        FCNSNode<STNode> child = currentNode.getFirstChild();
        while (child != null) {
            child.getData().buildControlStructure(child, helper);
            count++;
            child = child.getNextSibling();
        }

        // 3. Update the number of elements in TauControlElement
        tauElement.setNumberOfElements(count);
    }
}
