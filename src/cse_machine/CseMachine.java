package cse_machine;

import cse_machine.elements.control.*;
import cse_machine.elements.stack.*;

import java.util.ArrayList;
import java.util.List;

public class CseMachine implements ControlElementVisitor{
    private final Control control;
    private final Stack stack;
    private final EnvironmentManager environmentManager;

    public CseMachine(Control control, Stack stack, Environment environment) {
        control.initialize();
        this.control = control;
        this.stack = stack;
        this.environmentManager = new EnvironmentManager();
        this.environmentManager.initializePrimitiveEnvironment(environment);
        stack.initialize(environment);
    }

    public boolean executeNextStep() {
        if (!control.hasNext()) {
            return false; // No more steps
        }

        ControlElement nextElement = control.next();
        nextElement.accept(this);
        return control.hasNext();
    }

    public void execute() {
        while (executeNextStep());
    }

    public Control getControl() {
        return control;
    }

    public Stack getStack() {
        return stack;
    }

    public Environment getEnvironment() {
        return environmentManager.getCurrent().getEnvironment();
    }


    @Override
    public void visitGamma(GammaControlElement element) {
        StackElement callable = stack.pop();

        if (!(callable instanceof CallableElement function)) {
            throw new IllegalStateException("Expected CallableStackElement on top of stack.");
        }

        int arity = function.getArity();
        List<StackElement> args = new ArrayList<>();
        for (int i = 0; i < arity; i++) {
            // Order doesn't matter, gamma is only called where there is only one argument
            args.add(stack.pop());
        }
        function.apply(stack, control, environmentManager, args);
    }

    @Override
    public void visitBinOp(BinOpControlElement element) {
        String op = element.getOperator();

        // Get function from environment
        StackElement opFunc = this.environmentManager.getCurrent().getEnvironment().getVariable(op);

        if (!(opFunc instanceof CallableElement callable)) {
            throw new IllegalStateException("Operator '" + op + "' is not callable.");
        }

        int arity = callable.getArity();
        if (stack.size() < arity) {
            throw new IllegalStateException("Not enough arguments on the stack for operator '" + op + "'");
        }

        // Collect arguments (in reverse order)
        List<StackElement> args = new ArrayList<>();
        for (int i = 0; i < arity; i++) {
            args.add(stack.pop());
        }

        // Apply and let function push result to stack
        callable.apply(stack, control, environmentManager, args);
    }

    @Override
    public void visitUnOp(UnOpControlElement element) {
        String op = element.getOperator();

        StackElement opFunc = this.environmentManager.getCurrent().getEnvironment().getVariable(op);

        if (!(opFunc instanceof CallableElement callable)) {
            throw new IllegalStateException("Operator '" + op + "' is not callable.");
        }

        if (stack.isEmpty()) {
            throw new IllegalStateException("Not enough arguments on the stack for unary operator '" + op + "'");
        }

        StackElement arg = stack.pop();
        callable.apply(stack, control, environmentManager, List.of(arg));
    }


    @Override
    public void visitLambda(LambdaControlElement element) {
        int nearestEnvNumber = this.stack.findNearestEnvironmentNumber();
        LambdaClosureStackElement lambdaClosureStackElement = new LambdaClosureStackElement(
                element.getBoundVariables(),
                element.getNewIndex(),
                nearestEnvNumber
        );
        stack.push(lambdaClosureStackElement);
    }

    @Override
    public void visitData(IdentifierControlElement element) {
        String name = element.getName();

        StackElement value = this.environmentManager.getCurrent().getEnvironment().getVariable(name);

        if (value == null) {
            throw new IllegalStateException("Variable '" + name + "' not found in any environment.");
        }

        // Push it to the stack directly
        stack.push(value);
    }

    @Override
    public void visitBeta(BetaControlElement element) {
        // Check the top of the stack
        var top = stack.pop();
        if (!(top instanceof DataStackElement dataElem) || dataElem.getDataType() != DataStackElement.Type.BOOL) {
            throw new IllegalStateException("Beta expects a boolean at the top of the stack.");
        }

        // 2. Pop the two DeltaControlElements (true and false branches)
        if (!control.hasNext()) {
            throw new IllegalStateException("Missing Delta control elements after Beta.");
        }
        ControlElement falseBranch = control.next(); // false branch comes first on the stack (top)
        if (!control.hasNext()) {
            throw new IllegalStateException("Missing second Delta control element after Beta.");
        }
        ControlElement trueBranch = control.next(); // true branch is below it


        if (!(falseBranch instanceof DeltaControlElement deltaFalse) || !(trueBranch instanceof DeltaControlElement deltaTrue)) {
            throw new IllegalStateException("Expected two DeltaControlElements after Beta.");
        }

        // Choose which delta to follow
        int chosenLevel = (Boolean) dataElem.getValue() ? deltaTrue.getTargetLevel() : deltaFalse.getTargetLevel();

        // Replace with the corresponding control structure
        control.appendControlStructureByLevel(chosenLevel);
    }

    @Override
    public void visitDelta(DeltaControlElement element) {
        // will not be used, only a marker to control structure
    }

    @Override
    public void visitTau(TauControlElement element) {
        int count = element.getNumberOfElements();
        List<StackElement> tupleElements = new ArrayList<>();
        List<StackElement> preservedEnvironmentElements = new ArrayList<>();

        while (tupleElements.size() < count) {
            if (stack.isEmpty()) {
                throw new IllegalStateException("Not enough elements on the stack for tau(" + count + ")");
            }

            StackElement top = stack.pop();

            if (top instanceof EnvironmentStackElement) {
                preservedEnvironmentElements.add(top); // Store to re-add later
            } else {
                tupleElements.add(top); // This is a data element for the tuple
            }
        }

        // Restore preserved environment elements to the stack in correct order (top-most last)
        for (int i = preservedEnvironmentElements.size() - 1; i >= 0; i--) {
            stack.push(preservedEnvironmentElements.get(i));
        }

        TupleStackElement tuple = new TupleStackElement(tupleElements);
        stack.push(tuple);
    }

    @Override
    public void visitYStar(YStarControlElement element) {
        stack.push(new YStarStackElement());
    }


    @Override
    public void visitInteger(IntegerControlElement element) {
        stack.push(new DataStackElement(DataStackElement.Type.INT, element.getValue()));
    }

    @Override
    public void visitBoolean(BooleanControlElement element) {
        stack.push(new DataStackElement(DataStackElement.Type.BOOL, element.getValue()));
    }

    @Override
    public void visitString(StringControlElement element) {
        stack.push(new DataStackElement(DataStackElement.Type.STRING, element.getValue()));
    }

    @Override
    public void visitNil(NilControlElement element) {
        stack.push(new DataStackElement(DataStackElement.Type.NIL, null));
    }

    @Override
    public void visitDummy(DummyControlElement element) {
        stack.push(new DataStackElement(DataStackElement.Type.DUMMY, null));
    }

    @Override
    public void visitEnvironment(EnvironmentControlElement element) {
        int targetNumber = element.getNumber();

        // Search stack from top down
        var iterator = stack.getElements().descendingIterator();

        while (iterator.hasNext()) {
            StackElement se = iterator.next();
            if (se instanceof EnvironmentStackElement envElement) {
                if (envElement.getNumber() == targetNumber) {
                    // Remove from stack
                    iterator.remove();

                    // Only switch if it's not the primitive environment
                    if (targetNumber != 0) {
                        // Only switch if the one removed was the current
                        if (environmentManager.getCurrent().getNumber() == targetNumber) {
                            environmentManager.switchToEnvironment(stack.findNearestEnvironmentNumber());
                        }
                    }

                    return;
                }
            }
        }

        throw new IllegalStateException("EnvironmentStackElement with number " + targetNumber + " not found in stack");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CSE Machine State ===\n");

        sb.append("\n-- Control --\n");
        sb.append(control.toString());

        sb.append("\n-- Stack --\n");
        sb.append(stack.toString());

        sb.append("\n-- Environment --\n");
        var currentEnv = environmentManager.getCurrent();
        sb.append("  Current Environment: ")
                .append(currentEnv != null ? currentEnv.toString() : "None")
                .append("\n");
        return sb.toString();
    }
}
