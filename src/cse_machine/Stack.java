package cse_machine;

import cse_machine.elements.stack.EnvironmentStackElement;
import cse_machine.elements.stack.StackElement;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Stack {

    private final Deque<StackElement> elements;

    public Stack() {
        this.elements = new ArrayDeque<>();
    }

    public void initialize(Environment primitiveEnv) {
        elements.clear();
        elements.addLast(new EnvironmentStackElement(0, primitiveEnv));
    }

    public void push(StackElement element) {
        elements.addLast(element); // Add to top
    }

    public StackElement pop() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack.");
        }
        return elements.removeLast(); // Remove from top
    }

    public StackElement peek() {
        return elements.peekLast(); // Peek at top
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public void clear() {
        elements.clear();
    }

    public Deque<StackElement> getElements() {
        return elements;
    }

    public int findNearestEnvironmentNumber() {
        Iterator<StackElement> it = elements.descendingIterator();
        while (it.hasNext()) {
            StackElement element = it.next();
            if (element instanceof EnvironmentStackElement) {
                return ((EnvironmentStackElement) element).getNumber();
            }
        }
        throw new IllegalStateException("Cannot find environments.");
    }

    @Override
    public String toString() {
        return "Stack" + elements.toString();
    }
}
