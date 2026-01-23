package io.github.ke_vin_s.rpal.core.automaton;

import java.util.*;
import java.util.function.Predicate;

public final class FiniteAutomatonBuilder {
    private final Map<String, State> states = new HashMap<>();
    private State initialState;
    private final Set<State> acceptingStates = new HashSet<>();
    private final Set<Character> language = new HashSet<>();

    private FiniteAutomatonBuilder() {
    }

    public static FiniteAutomatonBuilder builder() {
        return new FiniteAutomatonBuilder();
    }

    public FiniteAutomatonBuilder withTransition(String fromName, Predicate<Character> condition, String toName) {
        State source = getOrCreateState(fromName);
        State target = getOrCreateState(toName);
        source.addTransition(condition, target);
        return this;
    }

    public FiniteAutomatonBuilder withTransition(String fromName, Collection<Character> symbols, String toName) {
        language.addAll(symbols);
        // We use a Set for O(1) lookup inside the predicate
        final Set<Character> symbolSet = new HashSet<>(symbols);
        return withTransition(fromName, symbolSet::contains, toName);
    }

    public FiniteAutomatonBuilder withTransition(String fromName, char symbol, String toName) {
        language.add(symbol);
        return withTransition(fromName, c -> c == symbol, toName);
    }

    public FiniteAutomatonBuilder withAcceptingStates(String... stateNames) {
        for (String name : stateNames) {
            State s = getOrCreateState(name);
            acceptingStates.add(s);
        }
        return this;
    }

    public FiniteAutomatonBuilder withInitialState(String stateName) {
        this.initialState = states.computeIfAbsent(stateName, State::new);
        return this;
    }

    private void addAcceptingStateByName(String stateName) {
        State state = states.get(stateName);
        if (state == null) {
            throw new IllegalArgumentException("State '" + stateName + "' does not exist");
        }
        this.acceptingStates.add(state);
    }

    private State getOrCreateState(String name) {
        return states.computeIfAbsent(name, State::new);
    }

    public FiniteAutomaton build() {
        if (initialState == null) throw new IllegalStateException("Initial state required");

        return new FiniteAutomaton(
                List.copyOf(states.values()),
                initialState,
                Set.copyOf(acceptingStates)
        );
    }
}
