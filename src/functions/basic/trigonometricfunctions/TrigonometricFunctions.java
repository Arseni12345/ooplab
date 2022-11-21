package functions.basic.trigonometricfunctions;

import functions.Function;

public abstract class TrigonometricFunctions implements Function {
    @Override
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }
}
