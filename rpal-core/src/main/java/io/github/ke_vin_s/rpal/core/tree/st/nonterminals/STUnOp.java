package io.github.ke_vin_s.rpal.core.tree.st.nonterminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.UnOpControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STUnOp extends STNode {
    public STUnOp(String label) {
        super(label);
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STUnOp node's tree node is not set");
        }

        FCNSNode<STNode> operandNode = currentNode.getFirstChild();
        if (operandNode == null) {
            throw new IllegalStateException("STUnOp must have exactly one child operand");
        }

        // Then add the unary operator element itself
        helper.addControlElement(new UnOpControlElement(this.getLabel()));

        // Build control structure for operand first
        operandNode.getData().buildControlStructure(operandNode, helper);
    }
}
