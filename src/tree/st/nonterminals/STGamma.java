package tree.st.nonterminals;

import cse_machine.elements.control.GammaControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

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
