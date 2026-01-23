package io.github.ke_vin_s.rpal.core.scanner;

import java.util.List;

public interface Scanner {
    boolean hasNext();

    Token nextToken();

    List<Token> tokenize();
}
