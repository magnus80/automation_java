/**
 * Created by KIryshkov on 22.02.2016.
 */
public class Point {

    public int x;
    public int y;

    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }

    public static double distance(Point p1, Point p2){
        int difX=p2.y-p1.y;
        int difY=p2.x-p1.x;
        return Math.sqrt((difX*difX)-(difY*difY));
    }
}

