package tree.ast.tuples;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.STNode;
import tree.st.nonterminals.STTau;
import utils.FCNSNode;

/**
 * Represents a tuple node in the AST, corresponding to the 'tau' construct.
 */
public class ASTTau extends ASTNode {
    public ASTTau() {
        super("tau");
    }

    /**
     * <p>Conceptually, the input AST tree looks like:
     * <pre>
     *    ASTTau (tau)
     *       /  |  \
     *     e1  e2  e3 ...
     * </pre>
     *
     * And after standardization, it becomes:
     * <pre>
     *     STTau
     *      /  |  \
     *    e1  e2  e3 ...
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        return standardizeListLike(currentNode, new STTau(), helper);
    }
}
