package tree.ast.expressions;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.nonterminals.STLambda;
import tree.st.STNode;
import utils.FCNSNode;

public class ASTLambda extends ASTNode {
    public ASTLambda() {
        super("lambda");
    }

    /**
     * <p>The input AST structure looks like:
     * <pre>
     *    ASTLambda ("lambda")
     *       /       |       ...       \
     *    var1     var2      ...       body
     * </pre>
     *
     * <p>Where the first n children are bound variables and the last child is the lambda body.
     *
     * <p>After standardization, it becomes nested STLambda nodes representing the lambda abstractions:
     * <pre>
     *    STLambda
     *       /      \
     *    var1    STLambda
     *              /     \
     *           var2    ... STLambda
     *                            /    \
     *                        varN     body
     * </pre>
     *
     * <p>This method:
     * <ul>
     *   <li>Standardizes each variable child into a corresponding STNode.</li>
     *   <li>Nests lambda abstractions for multiple variables.</li>
     *   <li>Attaches the standardized body as the final child of the innermost lambda.</li>
     * </ul>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        FCNSNode<ASTNode> currentChild = currentNode.getFirstChild();

        if (currentChild == null) {
            throw new IllegalStateException("Lambda must have at least one bound variable and a body");
        }

        FCNSNode<STNode> currentLambda = null;
        FCNSNode<STNode> firstLambda = null;

        // The ASTLambda children: variables... variables... body (last child)
        // So loop until the child before the last, treating them as variables
        while (currentChild.getNextSibling() != null) {
            FCNSNode<STNode> newLambda = new FCNSNode<>(new STLambda());

            // Standardize current variable
            FCNSNode<STNode> stVar = helper.standardizeChild(currentChild);

            newLambda.setFirstChild(stVar);

            if (currentLambda == null) {
                firstLambda = newLambda;
            } else {
                // Attach the new lambda as the body of previous lambda
                currentLambda.getFirstChild().setNextSibling(newLambda);
            }

            currentLambda = newLambda;
            currentChild = currentChild.getNextSibling();
        }

        // Now currentChild is the body expression
        FCNSNode<STNode> stBody = helper.standardizeChild(currentChild);

        if (currentLambda == null) {
            // Only one variable and one body
            // Build lambda node with firstChild = variable and body = stBody
            FCNSNode<STNode> singleLambda = new FCNSNode<>(new STLambda());

            // Standardize variable (which is currentNode.getFirstChild())
            FCNSNode<STNode> stVar = helper.standardizeChild(currentNode.getFirstChild());

            singleLambda.setFirstChild(stVar);
            stVar.setNextSibling(stBody);

            return singleLambda;
        } else {
            // Attach body to innermost lambda
            currentLambda.getFirstChild().setNextSibling(stBody);
            return firstLambda;
        }
    }
}