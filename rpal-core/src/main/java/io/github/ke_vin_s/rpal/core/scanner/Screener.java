package io.github.ke_vin_s.rpal.core.scanner;

import java.util.List;

public interface Screener {
    List<Token> screen(List<Token> tokens);
}
