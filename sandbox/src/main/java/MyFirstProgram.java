public class MyFirstProgram{

	public static void main(String[] args){
		Point p1=new Point();
		Point p2=new Point();
		p1.x=45;
		p1.y=-56;
		p2.x=0;
		p2.y=565;
		System.out.println("Расстояние между двумя точками равно: "+  distance(p1,p2));
	}
	public static double distance(Point p1, Point p2){
		int difX=p2.y-p1.y;
		int difY=p2.x-p1.x;
		return Math.sqrt((difX*difX)-(difY*difY));
	}

}