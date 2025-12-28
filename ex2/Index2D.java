package ex2;

import java.lang.Math;

/**
 * This class represents a 2D index (coordinate) in a 2D raster/matrix.
 * It implements the Pixel2D interface, providing integer-based 2D coordinates
 * and methods to compute distances and compare coordinates.
 */
public class Index2D implements Pixel2D {

    /**
     * A constant representing the origin point at coordinates (0,0)
     */
    public static final Index2D ORIGIN = new Index2D(0,0);
    
    /**
     * The X coordinate of this 2D index
     */
    private final int _x;
    
    /**
     * The Y coordinate of this 2D index
     */
    private final int _y;

    /**
     * Constructs a new Index2D with the specified x and y coordinates
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Index2D(int x, int y) {
       this._x = x;
       this._y = y;
    }
    
    /**
     * Constructs a new Index2D by copying the coordinates from another Pixel2D object
     * @param other the Pixel2D object to copy coordinates from
     */
    public Index2D(Pixel2D other) {
        this._x = other.getX();
        this._y = other.getY();
    }

    /**
     * Returns the X coordinate of this index
     * @return the X coordinate
     */
    @Override
    public int getX() {
        return this._x;
    }

    /**
     * Returns the Y coordinate of this index
     * @return the Y coordinate
     */
    @Override
    public int getY() {
        return this._y;
    }

    /**
     * Computes the Euclidean distance between this index and another Pixel2D point.
     * The distance is calculated using the formula: sqrt((x1-x2)^2 + (y1-y2)^2)
     * @param p2 the other Pixel2D point to calculate distance to
     * @return the Euclidean distance between this point and p2
     */
    @Override
    public double distance2D(Pixel2D p2) {
        double xDiff = Math.pow(this._x - p2.getX(), 2);
        double yDiff = Math.pow(this._y - p2.getY(), 2);
        return Math.sqrt(xDiff+yDiff);
    }

    /**
     * Returns a string representation of this index in the format "(x,y)"
     * @return a string representation of the coordinates
     */
    @Override
    public String toString() {
        String ans = null;
        ans = "(" + this._x + "," + this._y + ")";
        return ans;
    }

    /**
     * Compares this index with another object for equality.
     * Two Index2D objects are equal if they have the same x and y coordinates.
     * @param p the reference object with which to compare
     * @return true if the objects are equal (same x and y coordinates), false otherwise
     */
    @Override
    public boolean equals(Object p) {
        if(p == null) {
            return false;
        }
        if (p.getClass() != Index2D.class) {
            return false;
        }
        return (((Index2D) p).getX() == this._x && ((Index2D) p).getY() == this._y);
    }
}
