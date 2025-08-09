package tree.transform;

import cse_machine.elements.control.ControlElement;

import java.util.ArrayList;
import java.util.List;

public class ControlStructureBuilderHelper {
    private final List<ControlStructure> controlStructures = new ArrayList<>();
    private int currentLevel = 0;

    public ControlStructureBuilderHelper() {
        controlStructures.add(new ControlStructure(currentLevel));
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        if (level < 0 || level >= controlStructures.size()) {
            throw new IllegalArgumentException("Invalid level");
        }
        currentLevel = level;
    }

    public ControlStructure getCurrentControlStructure() {
        return controlStructures.get(currentLevel);
    }

    public List<ControlStructure> getAllControlStructures() {
        return controlStructures;
    }

    public void addControlElement(ControlElement element) {
        getCurrentControlStructure().addElement(element);
    }

    public int addNewLevel() {
        int newLevel = controlStructures.size();
        controlStructures.add(new ControlStructure(newLevel));
        return newLevel;
    }

    public int addNewLevelAndGoToIt() {
        int newLevel = addNewLevel();
        setCurrentLevel(newLevel);
        return newLevel;
    }

    public ControlStructure getControlStructure(int level) {
        if (level < 0 || level >= controlStructures.size()) {
            throw new IllegalArgumentException("Invalid level");
        }
        return controlStructures.get(level);
    }
}
