package cli;

import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class FileLoader {
    public static String load(String filename) {
        try {
            return new String(readAllBytes(get(filename)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filename, e);
        }
    }
}
