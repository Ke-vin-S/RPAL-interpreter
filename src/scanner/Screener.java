package scanner;

import java.util.List;

public interface Screener {
    List<Token> screen(List<Token> tokens);
}
