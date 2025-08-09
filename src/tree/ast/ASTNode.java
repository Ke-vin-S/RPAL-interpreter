package tree.ast;

import tree.Node;
import standardizer.STBuilder;
import tree.st.STNode;
import utils.FCNSNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all AST nodes.
 * Provides common null checks and delegates actual standardization to subclasses.
 */
public abstract class ASTNode extends Node {

    /**
     * Constructs an AST node with the given lexeme.
     *
     * @param lexeme the token or fragment represented by this node
     */
    public ASTNode(String lexeme) {
        super(lexeme);
    }

    /**
     * Template method that performs common validation and then calls
     * {@link #doStandardize(FCNSNode, STBuilder.StandardizationHelper)} implemented by subclasses.
     *
     * @param currentNode the current AST node wrapper in the FCNS representation
     * @param helper utility class for standardization context
     * @return the standardized ST subtree
     * @throws IllegalArgumentException if currentNode or its data is null
     */
    public final FCNSNode<STNode> standardize(
            FCNSNode<ASTNode> currentNode,
            STBuilder.StandardizationHelper helper) {
        if (currentNode == null || currentNode.getData() == null) {
            throw new IllegalArgumentException("AST node is not properly linked or data is missing.");
        }
        return doStandardize(currentNode, helper);
    }

    /**
     * Actual standardization logic to be implemented by subclasses.
     * This method assumes input validation has been done.
     *
     * @param currentNode the current AST node wrapper
     * @param helper utility class for standardization context
     * @return the standardized ST subtree
     */
    protected abstract FCNSNode<STNode> doStandardize(
            FCNSNode<ASTNode> currentNode,
            STBuilder.StandardizationHelper helper);

    /**
     * Standardizes a list-like AST node by creating a given root {@link STNode},
     * then attaching all standardized children of the current node as its siblings.
     *
     * <pre>{@code
     * Example:
     *   AST:         tau
     *              / |  \
     *             a  b   c
     *
     *   ST:        tau
     *             / |  \
     *           a'  b'  c'
     * }</pre>
     */
    protected FCNSNode<STNode> standardizeListLike(
            FCNSNode<ASTNode> currentNode,
            STNode rootSTNode,
            STBuilder.StandardizationHelper helper) {

        FCNSNode<STNode> root = new FCNSNode<>(rootSTNode);
        FCNSNode<ASTNode> child = currentNode.getFirstChild();
        List<FCNSNode<STNode>> childSTs = new ArrayList<>();

        while (child != null) {
            childSTs.add(helper.standardizeChild(child));
            child = child.getNextSibling();
        }

        linkSiblings(root, childSTs);
        return root;
    }

    /**
     * Links a list of {@code FCNSNode<STNode>} children as siblings
     * under the given parent node using the FCNS representation.
     *
     * @param parent the parent node to attach the children to
     * @param children the list of children to link
     */
    protected void linkSiblings(FCNSNode<STNode> parent, List<FCNSNode<STNode>> children) {
        FCNSNode<STNode> prev = null;
        for (FCNSNode<STNode> child : children) {
            if (prev == null) {
                parent.setFirstChild(child);
            } else {
                prev.setNextSibling(child);
            }
            prev = child;
        }
    }

    /**
     * Standardizes all children of the given AST node and links their standardized
     * versions as siblings under a new standardized parent node.

     * <p>If {@code expectedChildrenCount} is non-negative, the method checks that
     * the number of children matches this value and throws an exception otherwise.
     *
     * <p>Conceptually, the input tree looks like:
     * <pre>
     *     currentNode
     *       / |  | \
     *     c1 c2 c3 ...
     * </pre>
     *
     * And after standardization, it becomes:
     * <pre>
     *    stNode
     *      |
     *     c1' - c2' - c3' ...
     * </pre>
     *
     * @param currentNode the AST node whose children will be standardized
     * @param stNode the standardized node to wrap the children
     * @param helper the standardization helper used to standardize children
     * @param expectedChildrenCount if >= 0, validates that the number of children equals this value
     * @return the root of the standardized subtree, with {@code stNode} as the root node
     * @throws IllegalStateException if the number of children does not match {@code expectedChildrenCount}
     */
    protected FCNSNode<STNode> standardizeAndLinkChildren(
            FCNSNode<ASTNode> currentNode,
            STNode stNode,
            STBuilder.StandardizationHelper helper,
            int expectedChildrenCount
    ) {
        FCNSNode<STNode> head = null;
        FCNSNode<STNode> prev = null;
        int count = 0;
        FCNSNode<ASTNode> child = currentNode.getFirstChild();

        while (child != null) {
            count++;
            FCNSNode<STNode> standardized = helper.standardizeChild(child);
            if (head == null) {
                head = standardized;
            } else {
                prev.setNextSibling(standardized);
            }
            prev = standardized;
            child = child.getNextSibling();
        }

        if (expectedChildrenCount >= 0 && count != expectedChildrenCount) {
            throw new IllegalStateException("Expected exactly " + expectedChildrenCount + " children, found " + count);
        }

        FCNSNode<STNode> stNodeWrapper = new FCNSNode<>(stNode);
        stNodeWrapper.setFirstChild(head);
        return stNodeWrapper;
    }
}
