package myfirstproject;
import java.util.Scanner;
public class Areaofcircle {

	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
        double area;
        double radius;
        System.out.println("enter radius");
        radius=s.nextFloat();
        area=3.14*radius*radius;
        System.out.println("area is "+area);
	}

}
