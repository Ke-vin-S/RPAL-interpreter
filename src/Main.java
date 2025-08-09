import cli.CLIArguments;
import compiler.RPALCompiler;
import compiler.RPALInterpreter;
import cse_machine.CseMachine;
import cse_machine.io.ConsoleOutputWriter;
import tree.transform.ControlStructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        CLIArguments cliArgs = parseArguments(args);
        String fileContent = readFile(cliArgs.getFilename());

        RPALCompiler compiler = new RPALCompiler(fileContent);
        compiler.compile();

        printOptionalOutputs(cliArgs, compiler);

        RPALInterpreter interpreter = new RPALInterpreter(compiler.getControlStructures(), new ConsoleOutputWriter());
        runInterpreter(cliArgs, interpreter);
    }

    private static CLIArguments parseArguments(String[] args) {
        try {
            return CLIArguments.parse(args);
        } catch (IllegalArgumentException e) {
            System.out.println("Usage: java Main <filename> [-ast] [-st] [-control] [-cse]");
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
            return null; // unreachable
        }
    }

    private static String readFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.exit(1);
            return null; // unreachable
        }
    }

    private static void printOptionalOutputs(CLIArguments cliArgs, RPALCompiler compiler) {
        if (cliArgs.shouldPrintAST()) {
            System.out.println(compiler.getAST());
        }

        if (cliArgs.shouldPrintST()) {
            System.out.println(compiler.getST());
        }

        if (cliArgs.shouldPrintControlStructures()) {
            for (ControlStructure cs : compiler.getControlStructures()) {
                System.out.println(cs);
            }
        }
    }

    private static void runInterpreter(CLIArguments cliArgs, RPALInterpreter interpreter) {
        CseMachine machine = interpreter.getCseMachine();

        if (cliArgs.shouldPrintCSEStates()) {
            while (true) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Before step:\n" + machine);

                boolean hasMore = machine.executeNextStep();
                if (!hasMore) break;
            }
        } else {
            interpreter.run();
        }
    }
}

