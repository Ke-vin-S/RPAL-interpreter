package tree.ast.definitions;

import standardizer.STBuilder;
import tree.ast.ASTNode;
import tree.st.*;
import tree.st.nonterminals.STAssign;
import tree.st.nonterminals.STComma;
import tree.st.nonterminals.STTau;
import tree.st.terminals.STIdentifier;
import utils.FCNSNode;

public class ASTSimultaneousDefinition extends ASTNode {
    public ASTSimultaneousDefinition() {
        super("and");
    }

    /**
     * <p>The input AST structure looks like a list of assignment nodes under an "and" node:
     * <pre>
     *    ASTSimultaneousDefinition ("and")
     *       /       |       \
     *   (X1 = E1) (X2 = E2) ... (Xn = En)
     * </pre>
     *
     * <p>After standardization, it transforms into a single STAssign node where:
     * <ul>
     *   <li>The left child is an STComma node holding all the identifiers (X1, X2, ..., Xn).</li>
     *   <li>The right child is an STTau node holding all the expressions (E1, E2, ..., En).</li>
     * </ul>
     *
     * <pre>
     *   STAssign
     *     /     \
     *  STComma  STTau
     *   / | ...  / | ...
     * X1 X2 ... E1 E2 ...
     * </pre>
     * Standardizes the "and" node by:
     * <ol>
     *   <li>Standardizing each child assignment node.</li>
     *   <li>Verifying each child standardizes to an '=' node with an identifier on the left side.</li>
     *   <li>Collecting all identifiers into an STComma node.</li>
     *   <li>Collecting all right-hand side expressions into an STTau node.</li>
     *   <li>Creating an STAssign node with the STComma as left child and STTau as right child.</li>
     * </ol>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        STAssign assign = new STAssign();
        FCNSNode<STNode> assignNode = new FCNSNode<>(assign);

        STComma comma = new STComma();
        FCNSNode<STNode> commaNode = new FCNSNode<>(comma);

        STTau tau = new STTau();
        FCNSNode<STNode> tauNode = new FCNSNode<>(tau);

        FCNSNode<ASTNode> child = currentNode.getFirstChild();

        while (child != null) {
//            child.getData().setTreeNode(child);

            FCNSNode<STNode> standardizedChild = child.getData().standardize(child, helper);

            if (standardizedChild == null || standardizedChild.getData() == null) {
                throw new IllegalStateException("Standardized child is null or has no data.");
            }

            String label = standardizedChild.getData().getLabel();

            if (!label.equals("=")) {
                throw new IllegalStateException("Expected '=' at root of standardized child under 'and', but found: " + label);
            }

            FCNSNode<STNode> idNode = standardizedChild.getFirstChild();
            if (idNode == null) {
                throw new IllegalStateException("Left-hand side (ID) of '=' node is missing");
            }

            FCNSNode<STNode> exprNode = idNode.getNextSibling();
            if (exprNode == null) {
                throw new IllegalStateException("Right-hand side (expression) of '=' node is missing");
            }

            if (!(idNode.getData() instanceof STIdentifier id)) {
                throw new IllegalStateException("Expected STIdentifier in the LHS of '=' under 'and'");
            }

            commaNode.addChildNode(new FCNSNode<>(new STIdentifier(id.getName())));

            tauNode.addChildNode(exprNode);

            child = child.getNextSibling();
        }

        assignNode.setFirstChild(commaNode);
        commaNode.setNextSibling(tauNode);
        return assignNode;
    }

}
