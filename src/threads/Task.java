package threads;

import functions.Function;

public class Task {
    private Function function;

    private double leftDomain, rightDomain, samplingStep;

    public double getSamplingStep() {
        return samplingStep;
    }

    public void setSamplingStep(double samplingStep) {
        this.samplingStep = samplingStep;
    }

    public double getRightDomain() {
        return rightDomain;
    }

    public void setRightDomain(double rightDomain) {
        this.rightDomain = rightDomain;
    }

    public double getLeftDomain() {
        return leftDomain;
    }

    public void setLeftDomain(double leftDomain) {
        this.leftDomain = leftDomain;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }
}
