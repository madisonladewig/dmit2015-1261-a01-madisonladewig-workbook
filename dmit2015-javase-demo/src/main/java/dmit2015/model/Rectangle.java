package dmit2015.model;

public class Rectangle {

    public double length;
    public double width;

    //Generated getters and setters:
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    //No-argument constructor:

    public Rectangle() {
        length = 1;
        width = 1;
    }

    //Methods:
    public double area(){
        return length * width;
    }

    public double perimeter(){
        return (length * 2) + (width * 2);
    }

    static void main(String[] args){

        //Create rectangle:
        Rectangle currentRectangle = new Rectangle();

        //Set the length and width:
        currentRectangle.setLength(5);
        currentRectangle.setWidth(3);

        //Get rectangle area and perimeter:
        System.out.printf("Area: %2f, Perimeter: %2f\n",
                currentRectangle.area(),
                currentRectangle.perimeter());
    }
}
