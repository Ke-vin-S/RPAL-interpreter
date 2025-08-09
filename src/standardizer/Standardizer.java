package standardizer;

import tree.ast.ASTNode;
import tree.st.STNode;
import utils.FCNSNode;


public interface Standardizer {
    public FCNSNode<STNode> getST(FCNSNode<ASTNode> tree);
}
