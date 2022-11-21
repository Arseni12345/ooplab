package functions.tabbulatedfunctions;

import functions.Function;
import functions.FunctionPoint;
import functions.exception.InappropriateFunctionPointException;

import java.io.*;

public abstract class TabulatedFunctions {
    private static TabulatedFunctionFactory tabulatedFunctionFactory = new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory();
    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> cl, Function function, double leftX, double rightX, int pointsCount){
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; ++i){
            points[i] = new FunctionPoint(leftX + i * (rightX - leftX) / (pointsCount - 1), function.getFunctionValue(leftX + i * (rightX - leftX) / (pointsCount - 1)));
        }
        try {
            TabulatedFunction tabulatedFunction = createTabulatedFunction(cl, points);
            return tabulatedFunction;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; ++i){
            points[i] = new FunctionPoint(leftX + i * (rightX - leftX) / (pointsCount - 1), function.getFunctionValue(leftX + i * (rightX - leftX) / (pointsCount - 1)));
        }
        try {
            TabulatedFunction tabulatedFunction = createTabulatedFunction(points);
            return tabulatedFunction;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void outputTabulatedFunction(TabulatedFunction function, FileOutputStream out) throws IOException {
        String tabulatedFunction = Integer.toString(function.getLength()) + " " + function.toString();
        out.write(tabulatedFunction.getBytes());
        out.close();
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> cl, FileInputStream in) throws IOException, InappropriateFunctionPointException {
        String tabulatedFunctionString = new String(in.readAllBytes());
        int length;
        String numX = "";
        String numY = "";
        int i = 0, index = 0;
        FunctionPoint[] points;
        int flag = 0;
        while (tabulatedFunctionString.charAt(i) != ' '){
            numX += tabulatedFunctionString.charAt(i);
            ++i;
        }
        length = Integer.parseInt(numX);
        points = new FunctionPoint[length];
        numX = "";
        for (int j = i + 1; j < tabulatedFunctionString.length() + 1; ++j){
            if (flag == 0){
                if (tabulatedFunctionString.charAt(j) != ' '){
                    numX += tabulatedFunctionString.charAt(j);
                }else{
                    flag = 1;
                }
            } else if (flag == 1) {
                if (tabulatedFunctionString.charAt(j) != ' '){
                    numY += tabulatedFunctionString.charAt(j);
                }else{
                    flag = 2;
                }
            }else{
                points[index] = new FunctionPoint(Double.parseDouble(numX), Double.parseDouble(numY));
                numX = "";
                if (j != tabulatedFunctionString.length()) {
                    numX += tabulatedFunctionString.charAt(j);
                }
                numY = "";
                ++index;
                flag = 0;
            }
        }
        for (int c = 0; c < points.length; ++c){
            System.out.println(points[c].getX() + " " + points[c].getY());
        }
        in.close();
        return createTabulatedFunction(cl, points);
    }

    public static TabulatedFunction inputTabulatedFunction(FileInputStream in) throws IOException, InappropriateFunctionPointException {
        String tabulatedFunctionString = new String(in.readAllBytes());
        int length;
        String numX = "";
        String numY = "";
        int i = 0, index = 0;
        FunctionPoint[] points;
        int flag = 0;
        while (tabulatedFunctionString.charAt(i) != ' '){
            numX += tabulatedFunctionString.charAt(i);
            ++i;
        }
        length = Integer.parseInt(numX);
        points = new FunctionPoint[length];
        numX = "";
        for (int j = i + 1; j < tabulatedFunctionString.length() + 1; ++j){
            if (flag == 0){
                if (tabulatedFunctionString.charAt(j) != ' '){
                    numX += tabulatedFunctionString.charAt(j);
                }else{
                    flag = 1;
                }
            } else if (flag == 1) {
                if (tabulatedFunctionString.charAt(j) != ' '){
                    numY += tabulatedFunctionString.charAt(j);
                }else{
                    flag = 2;
                }
            }else{
                points[index] = new FunctionPoint(Double.parseDouble(numX), Double.parseDouble(numY));
                numX = "";
                if (j != tabulatedFunctionString.length()) {
                    numX += tabulatedFunctionString.charAt(j);
                }
                numY = "";
                ++index;
                flag = 0;
            }
        }
        for (int c = 0; c < points.length; ++c){
            System.out.println(points[c].getX() + " " + points[c].getY());
        }
        in.close();
        return createTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, FileWriter out) throws IOException {
        String tabulatedFunction = Integer.toString(function.getLength()) + " " + function.toString();
        out.write(tabulatedFunction);
        out.close();
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> cl, StreamTokenizer in) throws IOException, InappropriateFunctionPointException {
        in.nextToken();
        int length = (int)in.nval;
        FunctionPoint[] points = new FunctionPoint[length];
        for (int i = 0; i < length; ++i){
            points[i] = new FunctionPoint();
            in.nextToken();
            points[i].setX(in.nval);
            in.nextToken();
            points[i].setY(in.nval);
        }
        return createTabulatedFunction(cl, points);
    }

    public static TabulatedFunction readTabulatedFunction(StreamTokenizer in) throws IOException, InappropriateFunctionPointException {
        in.nextToken();
        int length = (int)in.nval;
        FunctionPoint[] points = new FunctionPoint[length];
        for (int i = 0; i < length; ++i){
            points[i] = new FunctionPoint();
            in.nextToken();
            points[i].setX(in.nval);
            in.nextToken();
            points[i].setY(in.nval);
        }
        return createTabulatedFunction(points);
    }
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory functionFactory){
       tabulatedFunctionFactory = functionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) throws InappropriateFunctionPointException {
        return tabulatedFunctionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cl, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        return cl.getName().equals(LinkedListTabulatedFunction.class.getName())
                ? new LinkedListTabulatedFunction(leftX, rightX, pointsCount)
                : new ArrayTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cl, double leftX, double rightX, double[] values) throws InappropriateFunctionPointException {
        return cl.getName().equals(LinkedListTabulatedFunction.class.getName())
                ? new LinkedListTabulatedFunction(leftX, rightX, values)
                : new ArrayTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cl, FunctionPoint[] points) throws InappropriateFunctionPointException {
        return cl.getName().equals(LinkedListTabulatedFunction.class.getName())
                ? new LinkedListTabulatedFunction(points)
                : new ArrayTabulatedFunction(points);
    }
}
