package compiler;

import cse_machine.Environment;
import cse_machine.elements.stack.BinOpStackElement;
import cse_machine.elements.stack.CurriedOperationStackElement;
import cse_machine.elements.stack.UnOpStackElement;
import cse_machine.io.OutputWriter;
import cse_machine.operations.binary.*;
import cse_machine.operations.two_step.ConcatenationOperation;
import cse_machine.operations.unary.*;

public class PrimitiveEnvironmentFactory {
    public static Environment create() {

        Environment env = new Environment();

        // Int
        env.setVariable("+", new BinOpStackElement("+", new IntAddition()));
        env.setVariable("-", new BinOpStackElement("-", new IntSubtraction()));
        env.setVariable("*", new BinOpStackElement("*", new IntMultiplication()));
        env.setVariable("/", new BinOpStackElement("/", new IntDivision()));
        env.setVariable("**", new BinOpStackElement("**", new IntExponentiation()));
        env.setVariable("le", new BinOpStackElement("le", new IntLessThanOrEqual()));
        env.setVariable("ge", new BinOpStackElement("ge", new IntGreaterThanOrEqual()));
        env.setVariable("ls", new BinOpStackElement("ls", new IntLessThan()));
        env.setVariable("gr", new BinOpStackElement("gr", new IntGreaterThan()));
        env.setVariable("neg", new UnOpStackElement("neg", new NegateOperation()));

        // Type checking
        env.setVariable("Isinteger", new UnOpStackElement("Isinteger", new IsIntegerOperation()));
        env.setVariable("Istruthvalue", new UnOpStackElement("Istruthvalue", new IsTruthValueOperation()));
        env.setVariable("Isstring", new UnOpStackElement("Isstring", new IsStringOperation()));
        env.setVariable("Istuple", new UnOpStackElement("Istuple", new IsTupleOperation()));
        env.setVariable("Isdummy", new UnOpStackElement("Isdummy", new IsDummyOperation()));

        // String
        env.setVariable("Stem", new UnOpStackElement("Stem", new StemOperation()));
        env.setVariable("Stern", new UnOpStackElement("Stern", new SternOperation()));
        env.setVariable("Conc", new CurriedOperationStackElement<>(new ConcatenationOperation()));

        // Bool
        env.setVariable("eq", new BinOpStackElement("eq", new EqualOperation()));
        env.setVariable("ne", new BinOpStackElement("ne", new NotEqualOperation()));
        env.setVariable("not", new UnOpStackElement("not", new NotOperation()));
        env.setVariable("&", new BinOpStackElement("&", new AndOperation()));
        env.setVariable("or", new BinOpStackElement("or", new OrOperation()));

        // Tuple
        env.setVariable("aug", new BinOpStackElement("aug", new AugOperation()));
        env.setVariable("Order", new UnOpStackElement("aug", new OrderOperation()));
        env.setVariable("Null", new UnOpStackElement("aug", new TupleNullOperation()));

        env.setVariable("ItoS", new UnOpStackElement("ItoS", new ItoSOperation()));

        return env;
    }

    public static void injectOutputWriter(Environment env, OutputWriter outputWriter) {
        PrintOperation printOp = new PrintOperation(outputWriter);
        env.setVariable("Print", new UnOpStackElement("Print", printOp));
        env.setVariable("print", new UnOpStackElement("print", printOp));
    }
}