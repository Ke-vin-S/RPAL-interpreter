package io.github.ke_vin_s.rpal.core.automaton;

import java.util.*;

public class FiniteAutomaton {
    private final List<State> states;
    private final Set<State> acceptingStates;
    private final State initialState;
    private State currentState;

    FiniteAutomaton(List<State> states, State initialState, Set<State> acceptingStates) {
        this.states = List.copyOf(states);
        this.initialState = initialState;
        this.currentState = initialState;
        this.acceptingStates = Set.copyOf(acceptingStates);

        validateStatesConsistency();
    }

    private void validateStatesConsistency() {
        if (!states.contains(initialState)) {
            throw new IllegalArgumentException("Initial state must be in the states list.");
        }
        if (!states.containsAll(acceptingStates)) {
            throw new IllegalArgumentException("All accepting states must be in the states list.");
        }
    }

    /**
     * Attempts to transition the automaton using the given character.
     * @return true if the transition was successful, false if no rule matched.
     */
    public boolean step(char symbol) {
        State nextState = currentState.getNextState(symbol);
        if (nextState != null) {
            currentState = nextState;
            return true;
        }
        return false;
    }

    /**
     * Standard transition method that throws an exception on failure.
     * Useful for strict DFA execution.
     */
    public void transition(char symbol) {
        if (!step(symbol)) {
            throw new IllegalStateException("No valid transition from state " +
                    currentState.getName() + " for character: '" + symbol + "'");
        }
    }

    /**
     * Checks if a transition is possible without actually moving the automaton.
     */
    public boolean canTransition(char symbol) {
        return currentState.getNextState(symbol) != null;
    }

    public void reset() {
        this.currentState = initialState;
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public boolean isAccepting() {
        return acceptingStates.contains(currentState);
    }

    public String getCurrentStateName() {
        return currentState.getName();
    }
}