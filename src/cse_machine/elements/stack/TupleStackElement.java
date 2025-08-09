package cse_machine.elements.stack;

import cse_machine.CallableElement;
import cse_machine.Control;
import cse_machine.EnvironmentManager;
import cse_machine.Stack;

import java.util.List;
import java.util.Objects;

public class TupleStackElement extends StackElement implements CallableElement {
    private final List<StackElement> elements;

    public TupleStackElement(List<StackElement> elements) {
        this.elements = elements;
    }

    public List<StackElement> getElements() {
        return elements;
    }

    @Override
    public void apply(Stack stack, Control control, EnvironmentManager envManager, List<StackElement> arguments) {
        if (arguments.size() != 1 || !(arguments.get(0) instanceof DataStackElement indexElement)) {
            throw new IllegalArgumentException("Tuple application requires a single integer index.");
        }

        if (indexElement.getDataType() != DataStackElement.Type.INT) {
            throw new IllegalArgumentException("Tuple index must be an integer.");
        }

        int index = indexElement.getIntValue();
        if (index < 1 || index > elements.size()) {
            throw new IndexOutOfBoundsException("Tuple index " + index + " out of bounds.");
        }

        // CSE rule is 1-based indexing
        StackElement selected = elements.get(index - 1);
        stack.push(selected);
    }

    // Only index is needed
    @Override
    public int getArity() {
        return 1;
    }

    @Override
    public String toString() {
        return "(" + elements.stream().map(Object::toString).reduce((a, b) -> a + ", " + b).orElse("") + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TupleStackElement)) return false;
        TupleStackElement that = (TupleStackElement) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}
