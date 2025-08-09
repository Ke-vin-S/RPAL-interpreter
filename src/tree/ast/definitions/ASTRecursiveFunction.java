package tree.ast.definitions;

import standardizer.STBuilder;
import tree.ast.ASTNode;
import tree.st.*;
import tree.st.nonterminals.STAssign;
import tree.st.nonterminals.STGamma;
import tree.st.nonterminals.STLambda;
import tree.st.terminals.STYStar;
import utils.FCNSNode;

public class ASTRecursiveFunction extends ASTNode {
    public ASTRecursiveFunction() {
        super("rec");
    }

    /**
     * <p>Example input AST under "rec":
     * <pre>
     * rec
     *  |
     *  =
     * / \
     * X  E
     * </pre>
     *
     * <p>Standardization transforms this into:
     * <pre>
     * X = gamma(Y*, lambda(X, E))
     * </pre>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // Step 1: Get child (should be '=' node under 'rec')
        FCNSNode<ASTNode> assignChild = currentNode.getFirstChild();
        if (assignChild == null) {
            throw new IllegalStateException("Expected '=' under 'rec'.");
        }

        // Step 2: Standardize '=' node
        FCNSNode<STNode> assignNode = helper.standardizeChild(assignChild);
        if (!(assignNode.getData() instanceof STAssign)) {
            throw new IllegalStateException("Expected standardized '=' node.");
        }

        // Step 3: Extract LHS and RHS from standardized '='
        FCNSNode<STNode> lhs = assignNode.getFirstChild();           // X
        FCNSNode<STNode> rhs = lhs.getNextSibling();                 // E

        // Step 4: Re-standardize LHS identifier as lambda parameter
        FCNSNode<ASTNode> lhsAst = assignChild.getFirstChild();
        FCNSNode<STNode> paramX = helper.standardizeChild(lhsAst);   // X again

        // Step 5: Build STLambda node: lambda(X, E)
        FCNSNode<STNode> lambda = new FCNSNode<>(new STLambda());
        lambda.setFirstChild(paramX);
        paramX.setNextSibling(rhs);

        // Step 6: Build STYStar node
        FCNSNode<STNode> yStar = new FCNSNode<>(new STYStar());

        // Step 7: Build STGamma node: gamma(Y*, lambda(X, E))
        FCNSNode<STNode> gamma = new FCNSNode<>(new STGamma());
        gamma.setFirstChild(yStar);
        yStar.setNextSibling(lambda);

        // Step 8: Reattach new RHS to assign
        lhs.setNextSibling(gamma);
        return assignNode;
    }
}
