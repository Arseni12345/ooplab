package functions.tabbulatedfunctions;

import functions.FunctionPoint;
import functions.exception.FunctionPointIndexOutOfBoundsException;
import functions.exception.InappropriateFunctionPointException;

public class LinkedListTabulatedFunction implements TabulatedFunction {

    private class FunctionNode{

        private FunctionPoint point;

        private FunctionNode next;

        private FunctionNode prev;

        public FunctionNode( FunctionNode next, FunctionNode prev){
            this.next = next;
            this.prev = prev;
        }
    }

    private FunctionNode head, tail;

    private int length;

    private FunctionNode getNodeByIndex(int index) {
        FunctionNode current;
        if (index < length / 2) {
            current = head;
            for (int i = 0; i < index; ++i) {
                current = current.next;
            }
        }else{
            current = tail;
            for (int i = length - 1; i > index; --i) {
                current = current.prev;
            }
        }
        return current;
    }

    private FunctionNode addNodeToTail(){
        FunctionNode newNode;
        if (length == 0) {
            newNode = new FunctionNode(null, null);
            head = newNode;
            tail = newNode;
        }else {
            newNode = new FunctionNode(null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        ++length;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index){
        FunctionNode newNode;
        if(index == length - 1){
            newNode = new FunctionNode(null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        else if(index != 0 ) {
            FunctionNode node = getNodeByIndex(index);
            newNode = new FunctionNode(node, node.prev);
            node.prev.next = newNode;
            node.prev = newNode;
            return newNode;
        }else{
            newNode = new FunctionNode(head, null);
            head.prev = newNode;
            head = newNode;
        }
        ++length;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index){
        if (index == 0){
            FunctionNode node = head;
            head.next.prev = null;
            head = head.next;
            --length;
            return node;
        } else if (index == length - 1) {
            FunctionNode node = head;
            tail.prev.next = null;
            tail = tail.prev;
            --length;
            return node;
        }else {
            FunctionNode node = getNodeByIndex(index);
            node.prev.next = node.next;
            node.next.prev = node.prev;
            --length;
            return node;
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException{
        if (leftX >= rightX || pointsCount < 2) {
            throw new InappropriateFunctionPointException();
        }
        length = pointsCount;
        FunctionNode current = new FunctionNode(null, null);
        current.point = new FunctionPoint(leftX, 0);
        head = current;
        current = new FunctionNode(null, head);
        head.next = current;
        for(int i = 1; i < pointsCount; ++i){
            FunctionNode newNode = new FunctionNode(null, current);
            current.point = new FunctionPoint(leftX + i * (rightX - leftX) / (pointsCount - 1), 0);
            current.next = newNode;
            current = newNode;
        }
        tail = current.prev;
        tail.next = null;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws InappropriateFunctionPointException{
        if (leftX >= rightX || values.length < 2) {
            throw new InappropriateFunctionPointException();
        }
        length = values.length;
        FunctionNode current = new FunctionNode(null, null);
        current.point = new FunctionPoint(leftX, values[0]);
        head = current;
        current = new FunctionNode(null, head);
        head.next = current;
        for(int i = 1; i < length; ++i){
            FunctionNode newNode = new FunctionNode(null, current);
            current.point = new FunctionPoint(leftX + i * (rightX - leftX) / (length - 1), values[i]);
            current.next = newNode;
            current = newNode;
        }
        tail = current.prev;
        tail.next = null;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) throws InappropriateFunctionPointException{
        if(points.length < 2){
            throw new InappropriateFunctionPointException();
        }
        length = 0;
        addNodeToTail().point = points[0];
        for (int i = 1; i < points.length; ++i){
            if(points[i - 1].getX() > points[i].getX()){
                throw  new InappropriateFunctionPointException();
            }
            addNodeToTail().point = points[i];
        }
        length = points.length;
    }

    @Override
    public double getFunctionValue(double pointX) {
        if(pointX < getLeftDomainBorder() || pointX > getRightDomainBorder()){
            return Double.NaN;
        }
        else{
            FunctionNode current = head;
            for(int i = 0;i < length - 1; ++i){
                if(pointX == current.point.getX()){
                    return current.point.getY();
                }
                else if(pointX > current.point.getX() && pointX < current.next.point.getX()) {
                    return (pointX - current.point.getX())*(current.next.point.getY() - current.point.getY()) / (current.next.point.getX() - current.point.getX()) + current.point.getY();
                }
                current = current.next;
            }
        }
        return 0;
    }

    @Override
    public int getPointsCount() {
        return length;
    }

    @Override
    public FunctionPoint getPoint(int index) throws IndexOutOfBoundsException{
        if(index < 0 || index > length - 1){
            throw new IndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point;
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws Exception {
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (point.getX() < getLeftDomainBorder() || point.getX() > getRightDomainBorder()) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).point = point;
    }

    @Override
    public double getPointX(int index) throws  FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point.getX();
    }

    @Override
    public void setPointX(int index, double pointX) throws Exception {
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (pointX < getLeftDomainBorder() || pointX> getRightDomainBorder()) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).point.setX(pointX);
    }

    @Override
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).point.getY();
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void setPointY(int index, double pointY) throws Exception {
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (pointY < getLeftDomainBorder() || pointY > getRightDomainBorder()) {
            throw new InappropriateFunctionPointException();
        }
        getNodeByIndex(index).point.setY(pointY);
    }

    @Override
    public void deletePoint(int index) throws Exception {
        if(index < 0 || index > length - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (length < 3) {
            throw new IllegalStateException();
        }
        deleteNodeByIndex(index );
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode current = head;
        for(int i = 0; i < length; ++i) {
            if (point.getX() == current.point.getX()) {
                throw new InappropriateFunctionPointException();
            }
            current = current.next;
        }
        current = head.next;
        if (point.getX() <= head.point.getX()){
            FunctionNode newNode = addNodeByIndex(0);
            newNode.point = point;
        } else if (point.getX() >= tail.point.getX()) {
            FunctionNode newNode = addNodeToTail();
            newNode.point = point;
        }
        else {
            for (int i = 1; i < length; ++i) {
                if (point.getX() >= current.prev.point.getX() && point.getX() <= current.point.getX()) {
                    FunctionNode newNode = addNodeByIndex(i);
                    newNode.point = point;
                    ++length;
                    return;
                }
                current = current.next;
            }
        }
    }

    @Override
    public double getLeftDomainBorder() {
        return head.point.getX();
    }

    @Override
    public double getRightDomainBorder() {
        return tail.point.getX();
    }

    @Override
    public String toString(){
        FunctionNode current = head;
        Double num1;
        String tabulatedFunction = "";
        for (int i = 0; i < length; ++i){
            num1 = current.point.getX();
            tabulatedFunction += num1.toString() + " ";
            num1 = current.point.getY();
            tabulatedFunction += num1.toString() + " ";
            current = current.next;
        }
        return tabulatedFunction;
    }

    @Override
    public boolean equals(Object o){
        FunctionNode current = head;
        if(o.getClass().equals(ArrayTabulatedFunction.class)){
            if (this.length == ((ArrayTabulatedFunction) o).getLength()){
                for(int i = 0; i < length; ++i){
                    if(!current.point.equals(((ArrayTabulatedFunction) o).getPoint(i))){
                        return false;
                    }
                    current = current.next;
                }
                return true;
            }
            else{
                return false;
            }
        }else if (o.getClass().equals(LinkedListTabulatedFunction.class)){
            if (this.length == ((LinkedListTabulatedFunction) o).length){
                FunctionNode current1 = ((LinkedListTabulatedFunction) o).head;
                for(int i = 0; i < length; ++i){
                    if(!current.point.equals(current1.point)){
                        return false;
                    }
                    current = current.next;
                    current1 = current1.next;
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
        FunctionNode current = head;
        for (int i = 0; i < length; ++i){
            result = result ^ current.point.hashCode();
            current = current.next;
        }
        return result;
    }

    @Override
    public Object clone(){
        FunctionPoint[] points = new FunctionPoint[length];
        FunctionNode current = head;
        try {
            for (int i = 0; i < length; ++i){
                points[i] = current.point;
                current = current.next;
            }
            return new LinkedListTabulatedFunction(points);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
    }
}
