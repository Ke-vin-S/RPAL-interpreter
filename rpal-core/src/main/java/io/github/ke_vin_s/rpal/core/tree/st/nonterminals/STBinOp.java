package io.github.ke_vin_s.rpal.core.tree.st.nonterminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.BinOpControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STBinOp extends STNode {
    public STBinOp(String label) {
        super(label);
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STBinOp node's tree node is not set");
        }

        FCNSNode<STNode> leftNode = currentNode.getFirstChild();
        FCNSNode<STNode> rightNode = (leftNode != null) ? leftNode.getNextSibling() : null;

        if (leftNode == null || rightNode == null) {
            throw new IllegalStateException("STBinOp must have exactly two children: left and right operands");
        }

        // Push binary operation control element first so it executes last
        helper.addControlElement(new BinOpControlElement(this.getLabel()));

        // Build control structure for left operand (executed first)
        leftNode.getData().buildControlStructure(leftNode, helper);

        // Build control structure for right operand (executed second)
        rightNode.getData().buildControlStructure(rightNode, helper);
    }
}
