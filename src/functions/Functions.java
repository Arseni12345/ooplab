package functions;

import functions.meta.*;

public abstract class Functions {
    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, Function power){
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }

    public static double integrate(Function f, double leftDomain, double rightDomain, double samplingStep) throws Exception{
        double integration = 0;
        if(f.getLeftDomainBorder() > leftDomain && f.getRightDomainBorder() < rightDomain){
            throw new Exception();
        }
        for(int i = 1; i < (rightDomain - leftDomain) / samplingStep; ++i){
            if(leftDomain + samplingStep * i > f.getRightDomainBorder()) {
                integration += samplingStep * f.getFunctionValue(leftDomain + (i - 1) * samplingStep + rightDomain) / 2;
            }else{
                integration += samplingStep * f.getFunctionValue(leftDomain * 2 + (i - 1) * samplingStep + samplingStep * i) / 2;
            }
        }
        return integration;
    }
}
