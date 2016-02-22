public class MyFirstProgram{

	public static void main(String[] args){
		Point p1=new Point(45,-56);
		Point p2=new Point(0,565);
		/*вызов через метод класса*/
		System.out.println("Расстояние между двумя точками равно: "+  distance(p1,p2));
	}
	public static double distance(Point p1, Point p2){
		int difX=p2.y-p1.y;
		int difY=p2.x-p1.x;
		return Math.sqrt((difX*difX)-(difY*difY));
	}

}