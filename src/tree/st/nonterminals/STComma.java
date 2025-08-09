package tree.st.nonterminals;

import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

public class STComma extends STNode {
    public STComma() {
        super(",");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {

    }
}
