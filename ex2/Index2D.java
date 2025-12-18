package ex2;

import java.lang.Math;

public class Index2D implements Pixel2D {

    public static final Index2D ORIGIN = new Index2D(0,0);
    private final int _x;
    private final int _y;

    public Index2D(int x, int y) {
       this._x = x;
       this._y = y;
    }
    public Index2D(Pixel2D other) {
        this._x = other.getX();
        this._y = other.getY();
    }

    @Override
    public int getX() {
        return this._x;
    }

    @Override
    public int getY() {
        return this._y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        double xDiff = Math.pow(this._x - p2.getX(), 2);
        double yDiff = Math.pow(this._y - p2.getY(), 2);
        return Math.sqrt(xDiff+yDiff);
    }

    @Override
    public String toString() {
        String ans = null;
        ans = "(" + this._x + "," + this._y + ")";
        return ans;
    }

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
