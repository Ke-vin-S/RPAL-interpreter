package tree.st.nonterminals;

import cse_machine.elements.control.BetaControlElement;
import cse_machine.elements.control.DeltaControlElement;
import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public class STCondition extends STNode {
    public STCondition() {
        super("->");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        if (currentNode == null) {
            throw new IllegalStateException("STCondition node's tree node is not set");
        }

        FCNSNode<STNode> conditionNode = currentNode.getFirstChild();
        FCNSNode<STNode> trueBranchNode = (conditionNode != null) ? conditionNode.getNextSibling() : null;
        FCNSNode<STNode> falseBranchNode = (trueBranchNode != null) ? trueBranchNode.getNextSibling() : null;

        if (conditionNode == null || trueBranchNode == null || falseBranchNode == null) {
            throw new IllegalStateException("STCondition must have exactly three children: condition, true branch, and false branch");
        }

        int previousLevel = helper.getCurrentLevel();

        // 1. Add Delta for TRUE branch and create new level
        int trueLevel = helper.addNewLevel();
        helper.addControlElement(new DeltaControlElement(trueLevel));

        // Build control structure for TRUE branch in new level
        helper.setCurrentLevel(trueLevel);
        trueBranchNode.getData().buildControlStructure(trueBranchNode, helper);

        // Return to previous level
        helper.setCurrentLevel(previousLevel);

        // 2. Add Delta for FALSE branch and create new level
        int falseLevel = helper.addNewLevel();
        helper.addControlElement(new DeltaControlElement(falseLevel));

        // Build control structure for FALSE branch in new level
        helper.setCurrentLevel(falseLevel);
        falseBranchNode.getData().buildControlStructure(falseBranchNode, helper);

        // Return to previous level
        helper.setCurrentLevel(previousLevel);

        // 3. Add Beta control element
        helper.addControlElement(new BetaControlElement());

        // 4. Build control structure for condition in current level
        conditionNode.getData().buildControlStructure(conditionNode, helper);
    }
}
