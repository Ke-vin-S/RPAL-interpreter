package standardizer;

import tree.ast.ASTNode;
import tree.st.STNode;
import utils.FCNSNode;

public class RPALStandardizer implements Standardizer {

    public RPALStandardizer() {
    }

    @Override
    public FCNSNode<STNode> getST(FCNSNode<ASTNode> tree) {
        return STBuilder.build(tree);
    }
}
