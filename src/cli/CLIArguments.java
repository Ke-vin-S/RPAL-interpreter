package cli;

/**
 * Encapsulates command-line arguments for the RPAL interpreter CLI.
 */
public class CLIArguments {
    private final String filename;
    private final boolean printAST;
    private final boolean printST;
    private final boolean printCSEStates;
    private final boolean printControlStructures;

    public CLIArguments(String filename, boolean printAST, boolean printST, boolean printCSEStates, boolean printControlStructures) {
        this.filename = filename;
        this.printAST = printAST;
        this.printST = printST;
        this.printCSEStates = printCSEStates;
        this.printControlStructures = printControlStructures;
    }

    public static CLIArguments parse(String[] args) {
        boolean ast = false;
        boolean st = false;
        boolean cse = false;
        boolean control = false;
        String file = null;

        for (String arg : args) {
            switch (arg) {
                case "-ast":
                    ast = true;
                    break;
                case "-st":
                    st = true;
                    break;
                case "-cse":
                    cse = true;
                    break;
                case "-control":
                    control = true;
                    break;
                default:
                    if (!arg.startsWith("-") && file == null) {
                        file = arg;
                    }
            }
        }

        if (file == null) {
            throw new IllegalArgumentException("Error: Filename must be provided.\n" +
                    "Usage: java -jar rpal.jar <filename> [-ast] [-st] [-control] [-cse]");
        }

        return new CLIArguments(file, ast, st, cse, control);
    }

    public String getFilename() {
        return filename;
    }

    public boolean shouldPrintAST() {
        return printAST;
    }

    public boolean shouldPrintST() {
        return printST;
    }

    public boolean shouldPrintCSEStates() {
        return printCSEStates;
    }

    public boolean shouldPrintControlStructures() {
        return printControlStructures;
    }
}
