package functions.tabbulatedfunctions;

import functions.Function;
import functions.FunctionPoint;
import functions.exception.InappropriateFunctionPointException;

import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable {
    double getFunctionValue(double pointX);
    int getPointsCount();
    FunctionPoint getPoint(int index) throws IndexOutOfBoundsException;
    void setPoint(int index, FunctionPoint point) throws Exception;
    double getPointX(int index) throws IndexOutOfBoundsException;
    void setPointX(int index, double pointX) throws Exception;
    double getPointY(int index) throws IndexOutOfBoundsException;
    int getLength();
    void setPointY(int index, double pointY) throws InappropriateFunctionPointException, Exception;
    void deletePoint(int index) throws Exception;
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
    public double getLeftDomainBorder();
    public double getRightDomainBorder();
}
