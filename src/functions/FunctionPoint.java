package functions;

public class FunctionPoint {

    private Double x, y;

    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(){
        this.x = 0.0;
        this.y = 0.0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString(){
        return x.toString() + " " + y.toString();
    }

    @Override
    public boolean equals(Object o){
        if (o.getClass().equals(FunctionPoint.class)){
            if(((FunctionPoint) o).x.equals(this.x) && ((FunctionPoint) o).y.equals(this.y)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int seniorBytesX, juniorBytesX, seniorBytesY, juniorBytesY;
        seniorBytesX = (int)(Double.doubleToLongBits(x) >> 32);
        juniorBytesX = (int)((Double.doubleToLongBits(x) << 32) >> 32);
        seniorBytesY = (int)(Double.doubleToLongBits(y) >> 32);
        juniorBytesY = (int)((Double.doubleToLongBits(y) << 32) >> 32);
        return (seniorBytesX + juniorBytesX) ^ (seniorBytesY + juniorBytesY);
    }
}
