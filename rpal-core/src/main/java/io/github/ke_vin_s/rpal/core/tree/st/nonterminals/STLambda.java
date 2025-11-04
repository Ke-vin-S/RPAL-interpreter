package io.github.ke_vin_s.rpal.core.tree.st.nonterminals;

import io.github.ke_vin_s.rpal.core.cse_machine.elements.control.LambdaControlElement;
import io.github.ke_vin_s.rpal.core.tree.st.STNode;
import io.github.ke_vin_s.rpal.core.tree.st.terminals.STIdentifier;
import io.github.ke_vin_s.rpal.core.tree.transform.ControlStructureBuilderHelper;
import io.github.ke_vin_s.rpal.core.utils.FCNSNode;

import java.util.ArrayList;
import java.util.List;

public class STLambda extends STNode {
    public STLambda() {
        super("lambda");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STLambda node's tree node is not set");
        }

        // Assume children order: parameters node, body node
        FCNSNode<STNode> parametersNode = currentNode.getFirstChild();
        FCNSNode<STNode> bodyNode = (parametersNode != null) ? parametersNode.getNextSibling() : null;

        if (parametersNode == null || bodyNode == null) {
            throw new IllegalStateException("STLambda must have exactly two children: parameters and body");
        }

        List<String> boundVariables = extractBoundVariables(parametersNode);

        int previousLevel = helper.getCurrentLevel();
        int newLevel = helper.addNewLevel();

        // Add LambdaControlElement with a list of variables
        helper.addControlElement(new LambdaControlElement(boundVariables, newLevel));
        helper.setCurrentLevel(newLevel);

        // Build control structure for the body
        bodyNode.getData().buildControlStructure(bodyNode, helper);

        helper.setCurrentLevel(previousLevel);
    }

    private List<String> extractBoundVariables(FCNSNode<STNode> node) {
        List<String> vars = new ArrayList<>();
        if (node.getData() instanceof STIdentifier) {
            vars.add(((STIdentifier) node.getData()).getName());
        } else if (node.getData().getLabel().equals(",")) {
            for (FCNSNode<STNode> child : node.getChildren()) {
                vars.addAll(extractBoundVariables(child));
            }
        } else {
            throw new IllegalStateException("Unexpected parameter format in lambda");
        }
        return vars;
    }
}
