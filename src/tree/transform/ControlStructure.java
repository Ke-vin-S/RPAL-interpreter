package tree.transform;

import cse_machine.elements.control.ControlElement;

import java.util.ArrayList;
import java.util.List;

public class ControlStructure {
    private final int level;
    private final List<ControlElement> elements;

    public ControlStructure(int level) {
        this.level = level;
        this.elements = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }

    public List<ControlElement> getElements() {
        return elements;
    }

    public void addElement(ControlElement element) {
        elements.add(element);
    }

    @Override
    public String toString() {
        return "Level " + level + ": " + elements;
    }
}
