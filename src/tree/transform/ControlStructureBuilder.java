package tree.transform;

import tree.st.STNode;
import utils.FCNSNode;

import java.util.List;

public class ControlStructureBuilder {

    private final ControlStructureBuilderHelper helper = new ControlStructureBuilderHelper();

    public ControlStructureBuilder() {}

    /**
     * Builds control structures from the ST and returns the result.
     */
    public List<ControlStructure> build(FCNSNode<STNode> root) {
        if (root == null) {
            throw new IllegalArgumentException("Root of standardized tree cannot be null.");
        }

        // Start traversal from the root node
        root.getData().buildControlStructure(root, helper);

        // Return the constructed list of control structures
        return helper.getAllControlStructures();
    }
}
