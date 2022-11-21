package UI;

import functions.Function;
import functions.FunctionPoint;
import functions.exception.InappropriateFunctionPointException;
import functions.tabbulatedfunctions.LinkedListTabulatedFunction;
import functions.tabbulatedfunctions.TabulatedFunction;
import functions.tabbulatedfunctions.TabulatedFunctions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TabulatedFunctionDoc implements TabulatedFunction{

    private TabulatedFunction tabulatedFunction;

    String fileName;

    boolean modify;

    private Controller controller;

    public void callRedraw() {
        controller.redraw();
    }

    public void registerRedrawFunctionController(Controller controller) {
        this.controller = controller;
        callRedraw();
    }

    private void save(String fileName) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        JSONObject jsonTabulatedFunction = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonTabulatedFunction.put("length", tabulatedFunction.getLength());
        jsonTabulatedFunction.put("points", jsonArray);
        for (int i = 0; i < tabulatedFunction.getLength(); ++i){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", tabulatedFunction.getPointX(i));
            jsonObject.put("y", tabulatedFunction.getPointY(i));
            jsonArray.add(jsonObject);

        }
        out.print(jsonTabulatedFunction.toJSONString());
        out.flush();
        out.close();
    }

    public void newFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        tabulatedFunction = new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        modify = true;
        if(controller != null) {
            callRedraw();
        }
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount){
        tabulatedFunction = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        modify = true;
        callRedraw();
    }

    public void saveFunctionAs(String fileName) throws Exception{
        save(fileName);
        this.modify = false;
        this.fileName = fileName;
    }

    public void loadFunction(String fileName) throws Exception{
        String stringTabulatedFunction = new String(Files.readAllBytes(Paths.get(fileName)));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonTabulatedFunction = (JSONObject) jsonParser.parse(stringTabulatedFunction);
        JSONArray jsonPoints = (JSONArray) jsonTabulatedFunction.get("points");
        FunctionPoint[] points = new FunctionPoint[((Long)jsonTabulatedFunction.get("length")).intValue()];
        for(int i = 0; i < ((Long)jsonTabulatedFunction.get("length")).intValue(); ++i){
            points[i] = new FunctionPoint(Double.parseDouble((((JSONObject) jsonPoints.get(i)).get("x").toString())), Double.parseDouble((((JSONObject) jsonPoints.get(i)).get("y").toString())));
        }
        this.tabulatedFunction = new LinkedListTabulatedFunction(points);
        modify = true;
        callRedraw();
    }

    public void saveFunction() throws Exception{
        save(this.fileName);
        modify = false;
    }

    @Override
    public double getFunctionValue(double pointX) {
        return tabulatedFunction.getFunctionValue(pointX);
    }

    @Override
    public int getPointsCount() {
        return tabulatedFunction.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) throws IndexOutOfBoundsException {
        return tabulatedFunction.getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws Exception {
        tabulatedFunction.setPoint(index, point);
        callRedraw();
    }

    @Override
    public double getPointX(int index) throws IndexOutOfBoundsException {
        return tabulatedFunction.getPointX(index);
    }

    @Override
    public void setPointX(int index, double pointX) throws Exception {
        tabulatedFunction.setPointX(index, pointX);
        callRedraw();
    }

    @Override
    public double getPointY(int index) throws IndexOutOfBoundsException {
        return tabulatedFunction.getPointY(index);
    }

    @Override
    public int getLength() {
        return tabulatedFunction.getLength();
    }

    @Override
    public void setPointY(int index, double pointY) throws InappropriateFunctionPointException, Exception {
        tabulatedFunction.setPointY(index, pointY);
        callRedraw();
    }

    @Override
    public void deletePoint(int index) throws Exception {
        tabulatedFunction.deletePoint(index);
        callRedraw();
        callRedraw();
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        tabulatedFunction.addPoint(point);
        callRedraw();
    }

    @Override
    public double getLeftDomainBorder() {
        return tabulatedFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return tabulatedFunction.getRightDomainBorder();
    }

    @Override
    public String toString(){
        return fileName + " " + modify + " " + tabulatedFunction.toString();
    }

    @Override
    public int hashCode(){
        return tabulatedFunction.hashCode();
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i == tabulatedFunction.getLength() - 1 ? false : true;
            }

            @Override
            public FunctionPoint next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                return tabulatedFunction.getPoint(++i);
            }
        };
    }
}
