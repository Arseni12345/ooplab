package functions.meta;

import functions.Function;

public class Shift implements Function{
    private Function function;

    private double x, y;

    public Shift(Function function, double x, double y){
        this.function = function;
        this.x = x;
        this.y = y;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder() + x;
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder() + x;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x + this.x) + y;
    }
}
