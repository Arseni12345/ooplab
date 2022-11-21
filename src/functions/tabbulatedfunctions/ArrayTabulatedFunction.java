package functions.tabbulatedfunctions;

import functions.FunctionPoint;
import functions.exception.FunctionPointIndexOutOfBoundsException;
import functions.exception.InappropriateFunctionPointException;

public class ArrayTabulatedFunction implements TabulatedFunction {

    private FunctionPoint[] points;

    private int length;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException{
        if (leftX >= rightX || pointsCount < 2) {
            throw new InappropriateFunctionPointException();
        }
        length = pointsCount;
        this.points = new FunctionPoint[pointsCount];
        for(int i = 0; i < pointsCount; ++i){
            this.points[i] = new FunctionPoint(leftX + i * (rightX - leftX) / (pointsCount - 1), 0);
        }
    }
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException{
        if (leftX >= rightX || values.length < 2) {
            throw new InappropriateFunctionPointException();
        }
        length = values.length;
        this.points = new FunctionPoint[values.length];
        for(int i = 0; i < values.length; ++i){
            this.points[i] = new FunctionPoint(leftX + i * (rightX - leftX) / (values.length - 1), values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] points) throws InappropriateFunctionPointException{
        if(points.length < 2){
            throw new InappropriateFunctionPointException();
        }
        for (int i = 1; i < points.length; ++i){
            if(points[i - 1].getX() > points[i].getX()){
                throw  new InappropriateFunctionPointException();
            }
        }
        this.points = points;
        length = points.length;
    }

    public double getLeftDomainBorder(){
        return points[0].getX();
    }

    public double getRightDomainBorder(){
        return points[points.length - 1].getX();
    }

    public double getFunctionValue(double x){
        if(x < getLeftDomainBorder() || x > getRightDomainBorder()){
            return Double.NaN;
        }
        else{
            for(int i = 0;i < length - 1; ++i){
                if(x == points[i].getX()){
                    return points[i].getY();
                }
                else if(x > points[i].getX() && x < points[i + 1].getX()) {
                    return (x - points[i].getX())*(points[i + 1].getY() - points[i].getY()) / (points[i + 1].getX() - points[i].getX()) + points[i].getY();
                }
            }
        }
        return 0;
    }

    @Override
    public double getPointX(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index > length - 1){
            throw new IndexOutOfBoundsException();
        }
        return points[index - 1].getX();
    }

    @Override
    public double getPointY(int index) throws IndexOutOfBoundsException{
        if(index < 0 || index > length - 1){
            throw new IndexOutOfBoundsException();
        }
        return points[index - 1].getY();
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public FunctionPoint getPoint(int index) throws IndexOutOfBoundsException{
        if(index < 0 || index > length - 1){
            throw new IndexOutOfBoundsException();
        }
        return points[index];
    }

    @Override
    public int getPointsCount() {
        return points.length;
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws Exception{
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (point.getX() < getLeftDomainBorder() || point.getX() > getRightDomainBorder()) {
            throw new InappropriateFunctionPointException();
        }
        points[index - 1] = point;
    }

    @Override
    public void setPointX(int index, double pointX) throws Exception {
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (pointX < getLeftDomainBorder() || pointX > getRightDomainBorder()) {
            throw new InappropriateFunctionPointException();
        }
        points[index - 1].setX(pointX);
    }

    @Override
    public void setPointY(int index, double pointY) throws Exception{
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (pointY < getLeftDomainBorder() || pointY > getRightDomainBorder()) {
            throw new InappropriateFunctionPointException();
        }
        points[index - 1].setY(pointY);
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        for(int i = 0; i < length; ++i) {
            if (point.getX() == points[i].getX()) {
                throw new InappropriateFunctionPointException();
            }
        }
        if (points.length == length){
            FunctionPoint[] newPoints = new FunctionPoint[length + 1];
            if (point.getX() < points[0].getX()) {
                System.arraycopy(points, 0, newPoints, 1, length);
                newPoints[0] = point;
            } else if (point.getX() > points[length - 1].getX()) {
                System.arraycopy(points, 0, newPoints, 0, length);
                newPoints[length] = point;
            }else {
                for (int i = 1; i < length; ++i){
                    if (point.getX() >= points[i - 1].getX() && point.getX() <= points[i].getX()){
                        newPoints[i] = point;
                        System.arraycopy(points, 0, newPoints, 0, i);
                        System.arraycopy(points, i, newPoints, i + 1, length - i);
                        points = newPoints;
                        ++length;
                        return;
                    }
                }
            }
            points = newPoints;
        }
        else{
            if (point.getX() < points[0].getX()) {
                for (int i = length - 1; i > -1; --i){
                    points[i + 1] = points[i];
                }
                points[0] = point;
            } else if (point.getX() > points[length - 1].getX()) {
                points[length] = point;
            }else {
                for (int i = 1; i < length; ++i) {
                    if (point.getX() >= points[i - 1].getX() && point.getX() <= points[i].getX()) {
                        for (int j = length - 1; j > i - 1; --j) {
                            points[j + 1] = points[j];
                        }
                        points[i] = point;
                        ++length;
                        return;
                    }
                }
            }
        }
        ++length;
    }

    @Override
    public void deletePoint(int index) throws Exception {
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (length < 3) {
            throw new IllegalStateException();
        }
        points[index - 1] = null;
        for(int i = index; i < length; ++i){
            points[i - 1] = points[i];
        }
        --length;
    }

    public void print(){
        System.out.println(length);
        for (int i = 0; i < length; ++i){
            System.out.println(points[i].getX() + " " + points[i].getY());
        }
    }

    @Override
    public String toString(){
        Double num1;
        String tabulatedFunction = "";
        for (int i = 0; i < length; ++i){
            num1 = points[i].getX();
            tabulatedFunction += num1.toString() + " ";
            num1 = points[i].getY();
            tabulatedFunction += num1.toString() + " ";
        }
        return tabulatedFunction;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().equals(ArrayTabulatedFunction.class)){
            if (this.length == ((ArrayTabulatedFunction) o).length){
                for(int i = 0; i < length; ++i){
                    if(!points[i].equals(((ArrayTabulatedFunction) o).getPoint(i))){
                        return false;
                    }
                }
                return true;
            }
            else{
                return false;
            }
        }else if (o.getClass().equals(LinkedListTabulatedFunction.class)){
            if (this.length == ((LinkedListTabulatedFunction) o).getLength()){
                for(int i = 0; i < length; ++i){
                    if(!points[i].equals(((LinkedListTabulatedFunction) o).getPoint(i))){
                        return false;
                    }
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        int result =length;
        for (int i = 0; i < length; ++i){
            result = result ^ points[i].hashCode();
        }
        return result;
    }

    @Override
    public Object clone(){
        try {
            return new ArrayTabulatedFunction(points);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
    }
}
