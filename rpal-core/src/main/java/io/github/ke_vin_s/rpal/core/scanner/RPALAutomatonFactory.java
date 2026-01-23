package io.github.ke_vin_s.rpal.core.scanner;

import io.github.ke_vin_s.rpal.core.automaton.FiniteAutomaton;
import io.github.ke_vin_s.rpal.core.automaton.FiniteAutomatonBuilder;
import static io.github.ke_vin_s.rpal.core.utils.SymbolUtils.*;

import java.util.Set;

public class RPALAutomatonFactory {
    public static FiniteAutomaton createRPALAutomaton() {
        return FiniteAutomatonBuilder.builder()
                .withInitialState("START")

                // --- Identifiers ---
                .withTransition("START", Character::isLetter, "IN_ID")
                .withTransition("IN_ID", c -> Character.isLetterOrDigit(c) || c == '_', "IN_ID")

                // --- Integers ---
                .withTransition("START", Character::isDigit, "IN_INT")
                .withTransition("IN_INT", Character::isDigit, "IN_INT")

                // --- Strings ---
                .withTransition("START", '\'', "IN_STR")
                .withTransition("IN_STR", c -> c != '\'', "IN_STR") // Predicate: anything but '
                .withTransition("IN_STR", '\'', "END_STR")

                // --- Spaces / Whitespace ---
                .withTransition("START", Character::isWhitespace, "IN_SPACE")
                .withTransition("IN_SPACE", Character::isWhitespace, "IN_SPACE")

                // --- Comments ---
                .withTransition("START", '/', "IN_COMMENT_SLASH")
                .withTransition("IN_COMMENT_SLASH", '/', "IN_COMMENT_BODY")
                .withTransition("IN_COMMENT_BODY", c -> c != '\n' && c != '\r', "IN_COMMENT_BODY")
                .withTransition("IN_COMMENT_BODY", c -> c == '\n' || c == '\r', "END_COMMENT")

                // --- Multi-Step Operators (Priority handling) ---
                .withTransition("START", '-', "OP_MINUS")
                .withTransition("OP_MINUS", '>', "OP_ARROW")

                .withTransition("START", '>', "OP_GT")
                .withTransition("OP_GT", '=', "OP_GE")

                .withTransition("START", '<', "OP_LT")
                .withTransition("OP_LT", '=', "OP_LE")

                .withTransition("START", '*', "OP_MUL")
                .withTransition("OP_MUL", '*', "OP_EXP")

                // --- Single-Step Operators ---
                .withTransition("START", Set.of('.', ',', '|', '&', '+', '@', '(', ')', '=', ';'), "OP_SINGLE")

                // --- Accepting States ---
                .withAcceptingStates(
                        "IN_ID", "IN_INT", "END_STR", "IN_SPACE", "IN_COMMENT_SLASH",
                        "END_COMMENT", "OP_MINUS", "OP_ARROW", "OP_GT", "OP_GE",
                        "OP_LT", "OP_LE", "OP_MUL", "OP_EXP", "OP_SINGLE"
                )
                .build();
    }
}