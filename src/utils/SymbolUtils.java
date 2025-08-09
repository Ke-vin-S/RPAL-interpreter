package utils;

import java.util.ArrayList;
import java.util.List;

public class SymbolUtils {

    // all uppercase and lowercase letters (A-Z, a-z)
    public static List<Character> getLetters() {
        List<Character> letters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            letters.add(c);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            letters.add(c);
        }
        return letters;
    }

    // (0-9)
    public static List<Character> getDigits() {
        List<Character> digits = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) {
            digits.add(c);
        }
        return digits;
    }

    // list of operator symbols
    public static List<Character> getOperatorSymbols() {
        return List.of(
                '+', '-', '*', '<', '>', '&', '.',
                '@', ':', '=', '~', '|', '$',
                '/',
                '!', '#', '%', '^', '_', ']', '[',
                '{', '}', '"', '‘', '?'
        );
    }

    // symbols allowed in strings rather than digits, letters and operator symbols
    // change between single and double quotes when using another
    public static List<Character> getStringSymbols() {
        return List.of(
                '\t', '\n', '\\', '"',
                '(', ')', ';', ',',
                ' '
        );
    }

    public static List<Character> getCommentSymbols() {
        return List.of(
                '"', '\'', '(', ')', ';', ',', '\\', ' ',
                '\t'
        );
    }
}