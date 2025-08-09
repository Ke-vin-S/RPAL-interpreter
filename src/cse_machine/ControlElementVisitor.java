package cse_machine;

import cse_machine.elements.control.*;

public interface ControlElementVisitor {
    void visitGamma(GammaControlElement element);
    void visitLambda(LambdaControlElement element);
    void visitData(IdentifierControlElement element);
    void visitInteger(IntegerControlElement element);
    void visitBoolean(BooleanControlElement element);
    void visitString(StringControlElement element);
    void visitNil(NilControlElement element);
    void visitDummy(DummyControlElement element);
    void visitEnvironment(EnvironmentControlElement element);
    void visitBinOp(BinOpControlElement element);
    void visitUnOp(UnOpControlElement element);
    void visitBeta(BetaControlElement element);
    void visitDelta(DeltaControlElement element);
    void visitTau(TauControlElement element);
    void visitYStar(YStarControlElement element);
}
