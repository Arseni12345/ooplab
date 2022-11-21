package functions.tabbulatedfunctions;

import functions.Function;
import functions.FunctionPoint;
import functions.exception.InappropriateFunctionPointException;

import java.io.*;

public abstract class TabulatedFunctions {
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; ++i){
            points[i] = new FunctionPoint(leftX + i * (rightX - leftX) / (pointsCount - 1), function.getFunctionValue(leftX + i * (rightX - leftX) / (pointsCount - 1)));
        }
        try {
            TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(points);
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
        return new LinkedListTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, FileWriter out) throws IOException {
        String tabulatedFunction = Integer.toString(function.getLength()) + " " + function.toString();
        out.write(tabulatedFunction);
        out.close();
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
        return new LinkedListTabulatedFunction(points);
    }
}
