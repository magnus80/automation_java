package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by KIryshkov on 25.02.2016.
 */
public class PointTests {

    @Test
    public void testDistance1(){
        Point p1=new Point(5,-98);
        Point p2=new Point(57,99);
        Assert.assertEquals(Point.distance(p1,p2),190.01315743916263);
    }
    @Test
    public void testDistance2(){
        Point p1=new Point(-9,435);
        Point p2=new Point(67,5);
        Assert.assertEquals(Point.distance(p1,p2),423.23043368831594);
    }
    @Test
    public void testDistance3(){
        Point p1=new Point(79,28);
        Point p2=new Point(85,78);
        Assert.assertEquals(Point.distance(p1,p2),49.63869458396343);
    }


}
