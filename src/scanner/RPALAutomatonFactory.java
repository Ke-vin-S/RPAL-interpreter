package scanner;

import automaton.FiniteAutomaton;
import automaton.FiniteAutomatonBuilder;

import java.util.List;
import java.util.Set;

import static utils.SymbolUtils.*;

public class RPALAutomatonFactory {    public static FiniteAutomaton createRPALAutomaton() {
    return FiniteAutomatonBuilder.builder()
            .withStateNames(List.of("q0", "q1", "q2", "q3", "q4", "q5", "q6", "q7",
                    "q8", "q9", "q10", "q11", "q12", "q13", "q14", "q15", "q16", "q17", "q18",
                    "q19", "q20", "q21", "q22", "q23", "q24", "q25"))
            .withInitialState("q0")
            .withAcceptingStateNames(List.of(
                    "q1", "q2", "q4", "q5", "q6", "q8", "q9", "q10",
                    "q11", "q12", "q13", "q14", "q15", "q16", "q17", "q18",
                    "q19", "q20", "q21", "q22", "q23", "q24", "q25"
            ))
            .withLanguage(Set.of('a', 'b', 'c')) // Add all RPAL language symbols
            // identifier
            .withTransition("q0", getLetters(), "q1")
            .withTransition("q1", getLetters(), "q1")
            .withTransition("q1", getDigits(), "q1")
            .withTransition("q1", '_', "q1")
            // digit
            .withTransition("q0", getDigits(), "q2")
            .withTransition("q2", getDigits(), "q2")
            // strings
            .withTransition("q0", '\'', "q3")
            .withTransition("q3", getLetters(), "q3")
            .withTransition("q3", getDigits(), "q3")
            .withTransition("q3", getOperatorSymbols(), "q3")
            .withTransition("q3", getStringSymbols(), "q3")
            .withTransition("q3", '\'', "q4")
            // spaces ? is /r need to handle
            .withTransition("q0", ' ', "q5")
            .withTransition("q0", '\t', "q5")
            .withTransition("q0", '\n', "q5")
            .withTransition("q0", '\r', "q5")
            .withTransition("q5", ' ', "q5")
            .withTransition("q5", '\t', "q5")
            .withTransition("q5", '\n', "q5")
            .withTransition("q5", '\r', "q5")
            // comments
            .withTransition("q0", '/', "q6")
            .withTransition("q6", '/', "q7")
            .withTransition("q7", getLetters(), "q7")
            .withTransition("q7", getDigits(), "q7")
            .withTransition("q7", getOperatorSymbols(), "q7")
            .withTransition("q7", getCommentSymbols(), "q7")
            .withTransition("q7", '\n', "q8")
            .withTransition("q7", '\r', "q8")
            // operators: two steps
            .withTransition("q0", '-', "q13")
            .withTransition("q13", '>', "q14")
            .withTransition("q0", '>', "q15")
            .withTransition("q15", '=', "q16")
            .withTransition("q0", '<', "q17")
            .withTransition("q17", '=', "q18")
            .withTransition("q0", '*', "q20")
            .withTransition("q20", '*', "q21")
            // operators: one step
            .withTransition("q0", '.', "q9")
            .withTransition("q0", ',', "q10")
            .withTransition("q0", '|', "q11")
            .withTransition("q0", '&', "q12")
            .withTransition("q0", '+', "q19")
            .withTransition("q0", '@', "q22")
            .withTransition("q0", '(', "q23")
            .withTransition("q0", ')', "q24")
            .withTransition("q0", '=', "q25")
            // semicolon not included because i dont see any use of it in lex
            // TODO: manage rest of the operation symbols
            // Add more transitions as needed for RPAL
            .build();
}
}
