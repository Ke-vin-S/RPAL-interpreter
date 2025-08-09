package tree.st.nonterminals;

import cse_machine.elements.control.BinOpControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public class STAug extends STNode {
    public STAug() {
        super("aug");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STAug node's tree node is not set");
        }

        FCNSNode<STNode> leftNode = currentNode.getFirstChild();
        FCNSNode<STNode> rightNode = (leftNode != null) ? leftNode.getNextSibling() : null;

        if (leftNode == null || rightNode == null) {
            throw new IllegalStateException("STAug must have exactly two children: left and right operands");
        }

        // Add binary operation control element for 'aug'
        helper.addControlElement(new BinOpControlElement("aug"));

        // Build control structure for left operand
        leftNode.getData().buildControlStructure(leftNode, helper);

        // Build control structure for right operand
        rightNode.getData().buildControlStructure(rightNode, helper);
    }
}
