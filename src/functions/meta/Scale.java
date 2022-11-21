package functions.meta;

import functions.Function;

public class Scale implements Function{
    private Function function;

    private double x, y;

    public Scale(Function function, double x, double y){
        this.function = function;
        this.x = x;
        this.y = y;
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
        return function.getFunctionValue(x * this.x) * y;
    }
}
