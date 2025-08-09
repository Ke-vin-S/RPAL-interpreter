package parser;

import tree.ast.ASTNode;
import utils.FCNSNode;
import java.util.Stack;

public class ASTBuilder {
    private final Stack<FCNSNode<ASTNode>> stack;
    private final Stack<FCNSNode<ASTNode>> inverseStack;

    public ASTBuilder() {
        this.stack = new Stack<>();
        this.inverseStack = new Stack<>();
    }

    public void buildTree(ASTNode token, int childrenCount) {
        FCNSNode<ASTNode> newNode = new FCNSNode<>(token);
        try {
            for (int i = 0; i < childrenCount; i++) {
                newNode.addChildNode(stack.pop());
            }
            stack.push(newNode);
        } catch (Exception e) {
            System.out.println("Error building AST: " + e.getMessage());
        }
    }

    // Ensures left-to-right child ordering
    public void buildTreeOrdered(ASTNode token, int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            if (!stack.isEmpty()) {
                inverseStack.push(stack.pop());
            }
        }

        FCNSNode<ASTNode> newNode = new FCNSNode<>(token);

        while (!inverseStack.isEmpty()) {
            newNode.addChildNode(inverseStack.pop());
        }

        stack.push(newNode);
    }

    public FCNSNode<ASTNode> get() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.peek();
    }

    public void clear() {
        stack.clear();
        inverseStack.clear();
    }
}
