package functions.meta;

import functions.Function;

public class Sum implements Function {
    private Function function1, function2;

    public Sum(Function function1, Function function2){
        this.function1 = function1;
        this.function2 = function2;
    }

    @Override
    public double getLeftDomainBorder() {
        if(function1.getLeftDomainBorder() >= function2.getLeftDomainBorder()){
            return function1.getLeftDomainBorder();
        }else {
            return function2.getLeftDomainBorder();
        }
    }

    @Override
    public double getRightDomainBorder() {
        if(function1.getRightDomainBorder() <= function2.getRightDomainBorder()){
            return function1.getRightDomainBorder();
        }else {
            return function2.getRightDomainBorder();
        }
    }

    @Override
    public double getFunctionValue(double x) {
        return function1.getFunctionValue(x) + function2.getFunctionValue(x);
    }
}
