package automaton;

import java.util.*;

public class FiniteAutomaton {
    private final List<State> states;
    private final Set<State> acceptingStates;
    private final Set<Character> language;
    private final State initialState;
    private State currentState;

    FiniteAutomaton(List<State> states, State initialState,
                    Set<State> acceptingStates, Set<Character> language) {
        if (states == null || states.isEmpty()) {
            throw new IllegalArgumentException("States list cannot be null or empty");
        }
        if (initialState == null) {
            throw new IllegalArgumentException("Initial state cannot be null");
        }
        if (acceptingStates == null) {
            throw new IllegalArgumentException("Accepting states set cannot be null");
        }
        if (language == null) {
            throw new IllegalArgumentException("Language set cannot be null");
        }

        this.states = new ArrayList<>(states);
        this.initialState = initialState;
        this.currentState = initialState;
        this.acceptingStates = new HashSet<>(acceptingStates);
        this.language = new HashSet<>(language);

        // initialState and acceptingStates are in states
        validateStatesConsistency(states, initialState, acceptingStates);
    }

    private static void validateStatesConsistency(List<State> states, State initialState, Set<State> acceptingStates) {
        if (!states.contains(initialState)) {
            throw new IllegalArgumentException("Initial state must be in states list");
        }
        for (State state : acceptingStates) {
            if (!states.contains(state)) {
                throw new IllegalArgumentException(
                        "Accepting state " + state.getName() + " is not in states list");
            }
        }
    }

    public State getState(String stateName) {
        for (State state : states) {
            if (state.getName().equals(stateName)) {
                return state;
            }
        }
        return null;
    }

    public void transition(char symbol) {
        if (!language.contains(symbol)) {
            throw new IllegalArgumentException("Symbol not in language: " + symbol);
        }
        State nextState = currentState.getTransition(symbol);
        if (nextState == null) {
            throw new IllegalStateException("No transition for symbol: " + symbol);
        }
        if (!states.contains(nextState)) {
            throw new IllegalStateException(
                    "Transition leads to invalid state: " + nextState.getName() +
                            ". scanner.State is not part of the automaton."
            );
        }
        currentState = nextState;
    }

    public boolean hasTransition(char symbol) {
        return states.contains(currentState.getTransition(symbol));
    }

    public void reset() {
        currentState = initialState;
    }

    public State getCurrentState(){
        return this.currentState;
    }
    public boolean isAcceptingState(){
        return acceptingStates.contains(currentState);
    }
}
