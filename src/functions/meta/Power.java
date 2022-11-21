package functions.meta;

import functions.Function;

public class Power implements Function{
    private Function function;

    private double power;

    public Power(Function function1, double power){
        this.function = function1;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(function.getFunctionValue(x), power);
    }
}
