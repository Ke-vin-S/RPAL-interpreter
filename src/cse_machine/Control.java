package cse_machine;

import cse_machine.elements.control.ControlElement;
import cse_machine.elements.control.EnvironmentControlElement;
import tree.transform.ControlStructure;

import java.util.*;

/**
 * Manages the control flow of the CSE machine.
 * Uses a deque to allow appending and popping control elements efficiently.
 */
public class Control {
    private final Map<Integer, ControlStructure> controlStructureMap = new HashMap<>();
    private final Deque<ControlElement> controlElements = new ArrayDeque<>();

    public Control(List<ControlStructure> controlStructures) {
        for (ControlStructure cs : controlStructures) {
            controlStructureMap.put(cs.getLevel(), cs);
        }
    }

    public void initialize(){
        controlElements.clear();
        controlElements.addLast(new EnvironmentControlElement(0));
        appendControlStructureByLevel(0);
    }

    public boolean hasNext() {
        return !controlElements.isEmpty();
    }

    public ControlElement next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more control elements to process.");
        }
        return controlElements.removeLast(); // Remove from end (stack behavior)
    }

    public void append(ControlElement element) {
        this.controlElements.addLast(element);
    }

    public ControlElement peek() {
        return controlElements.peekLast(); // Peek at end
    }

    public void appendControlStructureByLevel(int level) {
        ControlStructure cs = controlStructureMap.get(level);
        if (cs == null) {
            throw new IllegalArgumentException("Control structure level " + level + " not found.");
        }
        for (ControlElement element : cs.getElements()) {
            controlElements.addLast(element); // Add to end
        }
    }

    public int getRemainingSize() {
        return controlElements.size();
    }

    public void clear() {
        controlElements.clear();
    }

    public List<ControlElement> dumpAllElements() {
        return new ArrayList<>(controlElements);
    }

    public Map<Integer, ControlStructure> getAllStructures() {
        return controlStructureMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Control Elements (Top → Bottom):\n");

        List<ControlElement> elements = new ArrayList<>(controlElements);
        Collections.reverse(elements); // So that top of the stack (last) comes first

        for (ControlElement element : elements) {
            sb.append("  ").append(element).append("\n");
        }

        sb.append("\nControl Structures by Level:\n");
        for (Map.Entry<Integer, ControlStructure> entry : controlStructureMap.entrySet()) {
            sb.append("  Level ").append(entry.getKey()).append(": ");
            sb.append(entry.getValue().getElements()).append("\n");
        }

        return sb.toString();
    }
}
