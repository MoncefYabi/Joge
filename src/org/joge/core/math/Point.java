package org.joge.core.math;
/**
 * Diese Klasse Erzeugt einen Punktobjekt
 * @author Moncif Yabi
 * */

public class Point extends Shape
{

    /**
     * Create a new point
     *
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     */
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
        checkPoints();
    }

    
    @Override
    protected void createPoints()
    {
        points = new float[2];
        points[0] = getX();
        points[1] = getY();

        maxX = x;
        maxY = y;
        minX = x;
        minY = y;

        findCenter();
        calculateRadius();
    }

    @Override
    protected void findCenter()
    {
        center = new float[2];
        center[0] = points[0];
        center[1] = points[1];
    }


    @Override
    protected void calculateRadius()
    {
        boundingCircleRadius = 0;
    }
}