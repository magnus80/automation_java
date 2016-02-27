package ru.stqa.pft.sandbox;

public class MyFirstProgram{

	public static void main(String[] args){
		Point p1=new Point(45,-56);
		Point p2=new Point(0,565);

		/*вызов через метод класса*/
		System.out.println("Расстояние между двумя точками равно: "+  Point.distance(p1,p2));
	}
	/*public static double distance(ru.stqa.pft.sandbox.Point p1, ru.stqa.pft.sandbox.Point p2){
		int difX=p2.y-p1.y;
		int difY=p2.x-p1.x;
		return Math.sqrt((difX*difX)-(difY*difY));
	}*/

}