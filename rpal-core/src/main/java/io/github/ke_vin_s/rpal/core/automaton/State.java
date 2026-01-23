package io.github.ke_vin_s.rpal.core.automaton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class State {
    private final String name;

    private record Transition(Predicate<Character> condition, State target) {}

    private final List<Transition> transitions = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }

    public State getNextState(char c) {
        for (Transition transition : transitions) {
            if (transition.condition.test(c)) {
                return transition.target;
            }
        }
        return null;
    }

    public void addTransition(Predicate<Character> condition, State target) {
        this.transitions.add(new Transition(condition, target));
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
