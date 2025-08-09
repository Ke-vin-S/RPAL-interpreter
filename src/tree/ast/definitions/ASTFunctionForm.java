package tree.ast.definitions;

import tree.ast.ASTNode;
import standardizer.STBuilder;
import tree.st.STNode;
import tree.st.nonterminals.STAssign;
import tree.st.nonterminals.STLambda;
import utils.FCNSNode;
import java.util.Stack;

public class ASTFunctionForm extends ASTNode {
    public ASTFunctionForm() {
        super("function_form");
    }

    /**
     * <p>Input AST structure:</p>
     * <pre>
     *    ASTFunctionForm ("function_form")
     *         |
     *         +-- FunctionName
     *         +-- Param1
     *         +-- Param2
     *         +-- ...
     *         \-- BodyExpression
     * </pre>
     *
     * <p>The function is standardized by converting parameters and body into nested lambda expressions,
     * and finally assigning the lambda expression to the function name.</p>
     *
     * <p>Output standardized tree (ST) structure:</p>
     * <pre>
     *    STAssign
     *       /     \
     * FunctionName  STLambda
     *               /      \
     *          Param1    STLambda
     *                    /      \
     *               Param2    ...
     *                         \
     *                          BodyExpression
     *
     * <p>The lambdas are nested from left to right, corresponding to the function parameters.</p>
     */
    @Override
    public FCNSNode<STNode> doStandardize(FCNSNode<ASTNode> currentNode, STBuilder.StandardizationHelper helper) {
        // Get the function name (first child)
        FCNSNode<ASTNode> functionNameNode = currentNode.getFirstChild();
        if (functionNameNode == null) {
            throw new IllegalStateException("Function must have a name");
        }

        // Collect parameters (middle children) and body (last child)
        FCNSNode<ASTNode> current = functionNameNode.getNextSibling();
        if (current == null) {
            throw new IllegalStateException("Function must have a body");
        }

        // Use a stack to maintain parameter order
        Stack<FCNSNode<ASTNode>> paramStack = new Stack<>();
        FCNSNode<ASTNode> bodyNode;

        // Push all parameters to stack (except last node which is body)
        while (current.getNextSibling() != null) {
            paramStack.push(current);
            current = current.getNextSibling();
        }
        bodyNode = current; // Last node is the body

        // Standardize the body first

        // Build lambdas from left to right using the stack
        FCNSNode<STNode> currentLambda = helper.standardizeChild(bodyNode);
        while (!paramStack.empty()) {
            FCNSNode<ASTNode> param = paramStack.pop();
            FCNSNode<STNode> stParam = helper.standardizeChild(param);
            FCNSNode<STNode> newLambda = new FCNSNode<>(new STLambda());
            newLambda.setFirstChild(stParam);
            stParam.setNextSibling(currentLambda);
            currentLambda = newLambda;
        }

        // Create the assignment node
        FCNSNode<STNode> stAssign = new FCNSNode<>(new STAssign());
        FCNSNode<STNode> stFunctionName = helper.standardizeChild(functionNameNode);

        stAssign.setFirstChild(stFunctionName);
        stFunctionName.setNextSibling(currentLambda);

        return stAssign;
    }
}