package io.github.ke_vin_s.rpal.core.tree.st.nonterminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.GammaControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

public class STGamma extends STNode {
    public STGamma() {
        super("gamma");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STGamma node's tree node is not set");
        }

        FCNSNode<STNode> functionNode = currentNode.getFirstChild();
        FCNSNode<STNode> argumentNode = (functionNode != null) ? functionNode.getNextSibling() : null;

        if (functionNode == null || argumentNode == null) {
            throw new IllegalStateException("STGamma must have exactly two children: function and argument");
        }

        // Add Gamma control element representing function application
        helper.addControlElement(new GammaControlElement());

        // Build control structure for the function part
        functionNode.getData().buildControlStructure(functionNode, helper);

        // Build control structure for the argument part
        argumentNode.getData().buildControlStructure(argumentNode, helper);
    }
}
