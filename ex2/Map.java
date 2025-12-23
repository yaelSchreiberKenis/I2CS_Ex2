package ex2;
import java.io.Serializable;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{

    // edit this class below
    private int[][] matrix;
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
        matrix = new int[w][h];
        for (int i = 0; i < w; i++){
            for (int j = 0; j < h; j++){
                matrix[i][j] = v;
            }
        }
	}
	@Override
	public void init(int[][] arr) {
        matrix = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[0].length; j++){
                matrix[i][j] = arr[i][j];
            }
        }
	}
	@Override
	public int[][] getMap() {
		int[][] ans = null;
        ans = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < ans.length; i++){
            for (int j = 0; j < ans[i].length; j++){
                ans[i][j] = matrix[i][j];
            }
        }

		return ans;
	}
	@Override
	public int getWidth() {
        return matrix.length;
    }
	@Override
	public int getHeight() {
        return matrix[0].length;
    }
	@Override
	public int getPixel(int x, int y) {
        if(matrix.length - 1 < x) {return -1;}
        if(matrix[0].length - 1 < y) {return -1;}
        int ans = -1;
        ans = matrix[x][y];
        return ans;
    }
	@Override
	public int getPixel(Pixel2D p) {
        return matrix[p.getX()][p.getY()];
	}
	@Override
	public void setPixel(int x, int y, int v) {
        matrix[x][y] = v;
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        setPixel(p.getX(), p.getY(), v);
	}

    @Override
    public boolean isInside(Pixel2D p) {
        return (matrix.length > p.getX() && matrix[0].length > p.getY());
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        return  p.getHeight() == getHeight() && p.getWidth() == getWidth();
    }

    @Override
    public void addMap2D(Map2D p) {
        if (!sameDimensions(p)) {
            return;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] += p.getPixel(i,j);
            }
        }
    }

    @Override
    public void mul(double scalar) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = (int)(matrix[i][j]*scalar);
            }
        }
    }

    @Override
    public void rescale(double sx, double sy) {

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {

    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public boolean equals(Object ob) {
        boolean ans = false;

        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = -1;

		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

}
