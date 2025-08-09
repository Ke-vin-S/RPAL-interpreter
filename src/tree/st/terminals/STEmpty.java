package tree.st.terminals;

import tree.st.STNode;
import tree.transform.ControlStructureBuilderHelper;
import utils.FCNSNode;

// TODO; check proper execution
public class STEmpty extends STNode {
    public STEmpty() {
        super("()");
    }

    @Override
    public void buildControlStructure(FCNSNode<STNode> currentNode, ControlStructureBuilderHelper helper) {
        // TODO
    }
}
