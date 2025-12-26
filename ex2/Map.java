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
        matrix = new int[h][w];
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                matrix[y][x] = v;
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
        int[][] ans = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < ans.length; i++){
            for (int j = 0; j < ans[i].length; j++){
                ans[i][j] = matrix[i][j];
            }
        }

		return ans;
	}
	@Override
	public int getWidth() {
        return matrix[0].length;
    }
	@Override
	public int getHeight() {
        return matrix.length;
    }
	@Override
	public int getPixel(int x, int y) {
        if (x <= getWidth() || y >= getHeight()) {
            return -1;
        }

        return matrix[y][x];
    }
	@Override
	public int getPixel(Pixel2D p) {
        return matrix[p.getY()][p.getX()];
	}
	@Override
	public void setPixel(int x, int y, int v) {
        matrix[y][x] = v;
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        setPixel(p.getY(), p.getX(), v);
	}

    @Override
    public boolean isInside(Pixel2D p) {
        return (p.getX() < getWidth() && p.getY() <  getHeight());
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        if (p == null) {
            return false;
        }
        return p.getHeight() == getHeight() && p.getWidth() == getWidth();
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
        int newWidth = (int)(sx * getWidth());
        int newHeight = (int)(sy * getHeight());
        int[][] newMatrix = new int[newHeight][newWidth];

        for (int x = 0; x < newWidth; x++) {
            int previousX = Math.min((int)(x / sx), getWidth() - 1);
            for (int y = 0; y < newHeight; y++) {
                int previousY = Math.min((int)(y / sy), getHeight() - 1);
                newMatrix[y][x] = matrix[previousY][previousX];
            }
        }
        matrix = newMatrix;
    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        int startX = (int)Math.max(center.getX() - rad + 1, 0);
        int startY = (int)Math.max(center.getY() - rad + 1, 0);
        int endX = (int)Math.min(center.getX() + rad - 1, getWidth() - 1);
        int endY = (int)Math.min(center.getY() + rad - 1, getHeight() - 1);
        double radSquared = rad * rad;

        for (int x = startX; x <= endX; x++) {
            int xDiff = x -  center.getX();
            int xDiffSquared = xDiff * xDiff;
            for (int y = startY; y <= endY; y++) {
                int yDiff = y - center.getY();
                if (xDiffSquared + (yDiff * yDiff) < radSquared) {
                    matrix[y][x] = color;
                }
            }
        }
    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        if (p1.equals(p2)) {
            matrix[p1.getY()][p1.getX()] = color;
            return;
        }

        int dx = Math.abs(p2.getX() - p1.getX());
        int dy = Math.abs(p2.getY() - p1.getY());
        if (dx >= dy) {
            if (p1.getX() > p2.getX()) {
                drawLine(p2, p1, color);
                return;
            }
            double[] func = getLinearFunctionFrom2Points(p1, p2);
            for (int x = p1.getX(); x <= p2.getX(); x++) {
                matrix[CalcLinearFunc(func, x)][x] = color;
            }
        }
        else {
            if (p1.getY() > p2.getY()) {
                drawLine(p2, p1, color);
                return;
            }
            Index2D reverseP1 = new Index2D(p1.getY(), p1.getX());
            Index2D reverseP2 = new Index2D(p2.getY(), p2.getX());
            double[] func = getLinearFunctionFrom2Points(reverseP1, reverseP2);
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                matrix[y][CalcLinearFunc(func, y)] = color;
            }
        }
    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        int xMin = Math.min(p1.getX(), p2.getX());
        int xMax = Math.max(p1.getX(), p2.getX());
        int yMin = Math.min(p1.getY(), p2.getY());
        int yMax = Math.max(p1.getY(), p2.getY());

        for (int y =  yMin; y <= yMax; y++) {
            for (int x =  xMin; x <= xMax; x++) {
                matrix[y][x] = color;
            }
        }
    }

    @Override
    public boolean equals(Object ob) {
        if(ob == null) {
            return false;
        }
        if (ob.getClass() != this.getClass()) {
            return false;
        }
        if (!sameDimensions((Map2D)ob)) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != ((Map)ob).matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
        // If the new color is the original color I don't need to fill
        if (new_v == matrix[xy.getY()][xy.getX()]) {
            return 0;
        }
		return recursiveFill(xy.getY(), xy.getX(), matrix[xy.getY()][xy.getX()], new_v, cyclic);
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

    private double[] getLinearFunctionFrom2Points(Pixel2D p1, Pixel2D p2) {
        double[] result = new double[2];
        double m = (double)(p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double b = p1.getY() - m*p1.getX();
        result[0] = m;
        result[1] = b;
        return result;
    }

    private int CalcLinearFunc(double[] func, int x) {
        return (int)(func[0] * x + func[1] + 0.5);
    }

    public int recursiveFill(int y, int x, int originColor, int new_v,  boolean cyclic) {
        if (matrix[y][x] != originColor) {
            return 0;
        }

        matrix[y][x] = new_v;
        int result = 1;

        // Fill the up neighbor
        if (y < getHeight() - 1) {
            result += recursiveFill(y + 1, x, originColor, new_v, cyclic);
        }
        else if (cyclic) {
            result += recursiveFill(0, x, originColor, new_v, cyclic);
        }

        // Fill the down neighbor
        if (y > 0) {
            result += recursiveFill(y - 1, x, originColor, new_v, cyclic);
        }
        else if (cyclic) {
            result += recursiveFill(getHeight() - 1, x, originColor, new_v, cyclic);
        }

        // Fill the right neighbor
        if (x < getWidth() - 1) {
            result += recursiveFill(y, x + 1, originColor, new_v, cyclic);
        }
        else if (cyclic) {
            result += recursiveFill(y, 0, originColor, new_v, cyclic);
        }

        // Fill the left neighbor
        if (x > 0) {
            result += recursiveFill(y, x - 1, originColor, new_v, cyclic);
        }
        else if (cyclic) {
            result += recursiveFill(y, getWidth() - 1, originColor, new_v, cyclic);
        }

        return result;
    }
}
