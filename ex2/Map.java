package ex2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 * The map stores integer values representing different colors or states in a 2D grid.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{

    /**
     * The internal 2D matrix storing the map data.
     * Matrix dimensions: [height][width], where height = matrix.length and width = matrix[0].length
     */
    private int[][] matrix;
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * All pixels in the map will be initialized to the value v.
	 * @param w the width of the map (number of columns)
	 * @param h the height of the map (number of rows)
	 * @param v the initial value for all pixels
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	
	/**
	 * Constructs a square map (size*size) with all pixels initialized to 0.
	 * @param size the size of the square map (both width and height)
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * Creates a deep copy of the provided array data.
	 * @param data the 2D array to initialize the map from
	 */
	public Map(int[][] data) {
		init(data);
	}
	/**
	 * Initializes a w*h 2D raster map with an initial value v.
	 * Creates a new matrix of size [height][width] and fills all entries with value v.
	 * @param w the width of the map (number of columns)
	 * @param h the height of the map (number of rows)
	 * @param v the initial value for all pixels
	 */
	@Override
	public void init(int w, int h, int v) {
        // Create a new matrix with dimensions [height][width]
        matrix = new int[h][w];
        // Iterate through all positions and set them to the initial value v
        // Note: matrix indexing is [y][x], so y (row) is the first index
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                matrix[y][x] = v;
            }
        }
	}
	/**
	 * Initializes the map from a given 2D array.
	 * Performs a deep copy of the provided array into the internal matrix.
	 * @param arr the 2D array to copy from
	 */
	@Override
	public void init(int[][] arr) {
        // Create a new matrix with the same dimensions as the input array
        matrix = new int[arr.length][arr[0].length];
        // Copy all values from the input array to the internal matrix
        for (int i = 0; i < arr.length; i++){
            for (int j = 0; j < arr[0].length; j++){
                matrix[i][j] = arr[i][j];
            }
        }
	}
	/**
	 * Returns a deep copy of the internal 2D matrix.
	 * This ensures that modifications to the returned array do not affect the internal matrix.
	 * @return a deep copy of the internal matrix
	 */
	@Override
	public int[][] getMap() {
        // Create a new 2D array with the same dimensions
        int[][] ans = new int[matrix.length][matrix[0].length];
        // Copy all values from the internal matrix to the result array
        for (int i = 0; i < ans.length; i++){
            for (int j = 0; j < ans[i].length; j++){
                ans[i][j] = matrix[i][j];
            }
        }
		return ans;
	}
	/**
	 * Returns the width (number of columns) of the map.
	 * @return the width of the map
	 */
	@Override
	public int getWidth() {
        return matrix[0].length;
    }
	
	/**
	 * Returns the height (number of rows) of the map.
	 * @return the height of the map
	 */
	@Override
	public int getHeight() {
        return matrix.length;
    }
	/**
	 * Returns the pixel value at coordinates (x, y).
	 * Note: The coordinates are 0-indexed.
	 * @param x the x coordinate (column index)
	 * @param y the y coordinate (row index)
	 * @return the pixel value at (x, y), or -1 if coordinates are out of bounds
	 */
	@Override
	public int getPixel(int x, int y) {
        // Check if coordinates are out of bounds
        // Returns -1 if x is greater than or equal to width, or y is greater than or equal to height
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
            return -1;
        }
        // Return the pixel value (matrix is indexed as [y][x])
        return matrix[y][x];
    }
	
	/**
	 * Returns the pixel value at the position specified by the Pixel2D object.
	 * @param p the Pixel2D object containing the coordinates
	 * @return the pixel value at position (p.getX(), p.getY())
	 */
	@Override
	public int getPixel(Pixel2D p) {
        if (p.getX() < 0 || p.getY() < 0 || p.getX() >= getWidth() || p.getY() >= getHeight()) {
            return -1;
        }
        return matrix[p.getY()][p.getX()];
	}
	/**
	 * Sets the pixel value at coordinates (x, y) to the specified value v.
	 * @param x the x coordinate (column index)
	 * @param y the y coordinate (row index)
	 * @param v the value to set the pixel to
	 */
	@Override
	public void setPixel(int x, int y, int v) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
            return;
        }
        matrix[y][x] = v;
    }
	
	/**
	 * Sets the pixel value at the position specified by the Pixel2D object to value v.
	 * @param p the Pixel2D object containing the coordinates
	 * @param v the value to set the pixel to
	 */
	@Override
	public void setPixel(Pixel2D p, int v) {
        if (p.getX() < 0 || p.getY() < 0 || p.getX() >= getWidth() || p.getY() >= getHeight()) {
            return;
        }
        matrix[p.getY()][p.getX()] = v;
	}

    /**
     * Checks if the given Pixel2D coordinate is within the bounds of this map.
     * A coordinate is inside if both x and y are non-negative and less than width/height respectively.
     * @param p the Pixel2D coordinate to check
     * @return true if the coordinate is inside the map bounds, false otherwise
     */
    @Override
    public boolean isInside(Pixel2D p) {
        // Check if x is within [0, width) and y is within [0, height)
        return (p.getX() >= 0 && p.getY() >= 0 && p.getX() < getWidth() && p.getY() <  getHeight());
    }

    /**
     * Checks if this map has the same dimensions as the given Map2D object.
     * @param p the Map2D object to compare dimensions with
     * @return true if both maps have the same width and height, false if null or different dimensions
     */
    @Override
    public boolean sameDimensions(Map2D p) {
        // Return false if the other map is null
        if (p == null) {
            return false;
        }
        // Compare both width and height
        return p.getHeight() == getHeight() && p.getWidth() == getWidth();
    }

    /**
     * Adds the values from another Map2D to this map element-wise.
     * Only performs the operation if both maps have the same dimensions.
     * @param p the Map2D to add to this map
     */
    @Override
    public void addMap2D(Map2D p) {
        // Only proceed if dimensions match
        if (!sameDimensions(p)) {
            return;
        }
        // Add corresponding pixel values element-wise
        // Note: getPixel is called with (j, i) because Map2D interface uses (x, y) = (column, row)
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] += p.getPixel(j, i);
            }
        }
    }

    /**
     * Multiplies all pixel values in this map by a scalar value.
     * The result is cast to int, so fractional parts are truncated.
     * @param scalar the scalar value to multiply all pixels by
     */
    @Override
    public void mul(double scalar) {
        // Multiply each pixel value by the scalar and cast to int
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = (int)(matrix[i][j]*scalar);
            }
        }
    }

    /**
     * Rescales the map by the given scale factors sx and sy.
     * The new dimensions are calculated as: newWidth = sx * width, newHeight = sy * height.
     * Uses nearest-neighbor interpolation: each pixel in the new map is mapped to the nearest
     * corresponding pixel in the original map.
     * @param sx the scale factor for the width (x-axis)
     * @param sy the scale factor for the height (y-axis)
     */
    @Override
    public void rescale(double sx, double sy) {
        // Calculate new dimensions (cast to int truncates fractional parts)
        int newWidth = (int)(sx * getWidth());
        int newHeight = (int)(sy * getHeight());
        int[][] newMatrix = new int[newHeight][newWidth];

        // For each pixel in the new map, find the corresponding pixel in the original map
        for (int x = 0; x < newWidth; x++) {
            // Map new x coordinate back to original coordinate space
            // Math.min ensures we don't exceed original bounds
            int previousX = Math.min((int)(x / sx), getWidth() - 1);
            for (int y = 0; y < newHeight; y++) {
                // Map new y coordinate back to original coordinate space
                int previousY = Math.min((int)(y / sy), getHeight() - 1);
                // Copy pixel value using nearest-neighbor interpolation
                newMatrix[y][x] = matrix[previousY][previousX];
            }
        }
        // Initialize this map with the rescaled matrix
        init(newMatrix);
    }

    /**
     * Draws a circle (filled disk) with the specified center, radius, and color.
     * All pixels whose distance from the center is less than the radius are filled with the color.
     * Uses the Euclidean distance formula: sqrt((x-cx)^2 + (y-cy)^2) < radius
     * @param center the center point of the circle
     * @param rad the radius of the circle
     * @param color the color value to fill the circle with
     */
    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        // Calculate the bounding box for the circle to optimize iteration
        // Start coordinates: ensure we don't go below 0
        int startX = (int)Math.max(center.getX() - rad, 0);
        int startY = (int)Math.max(center.getY() - rad, 0);
        // End coordinates: ensure we don't exceed map boundaries
        int endX = (int)Math.min(center.getX() + rad, getWidth() - 1);
        int endY = (int)Math.min(center.getY() + rad, getHeight() - 1);
        // Pre-calculate radius squared to avoid repeated multiplication in distance calculation
        double radSquared = rad * rad;

        // Iterate only through the bounding box
        for (int x = startX; x <= endX; x++) {
            // Calculate x distance from center (squared to avoid sqrt)
            int xDiff = x -  center.getX();
            int xDiffSquared = xDiff * xDiff;
            for (int y = startY; y <= endY; y++) {
                // Calculate y distance from center
                int yDiff = y - center.getY();
                // Check if pixel is inside circle using squared distance (faster than sqrt)
                // If (x-cx)^2 + (y-cy)^2 < r^2, then the pixel is inside the circle
                if (xDiffSquared + (yDiff * yDiff) < radSquared) {
                    matrix[y][x] = color;
                }
            }
        }
    }

    /**
     * Draws a line between two points using Bresenham-like line algorithm.
     * The algorithm uses linear interpolation to determine which pixels to color.
     * For lines where dx >= dy, iterates over x and calculates y.
     * For lines where dy > dx, iterates over y and calculates x (swaps coordinates).
     * @param p1 the start point of the line
     * @param p2 the end point of the line
     * @param color the color value to draw the line with
     */
    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        // Special case: if both points are the same, just color that pixel
        if (p1.equals(p2)) {
            matrix[p1.getY()][p1.getX()] = color;
            return;
        }

        // Calculate absolute differences to determine line direction
        int dx = Math.abs(p2.getX() - p1.getX());
        int dy = Math.abs(p2.getY() - p1.getY());
        
        // Case 1: Line is more horizontal than vertical (dx >= dy)
        if (dx >= dy) {
            // Ensure we iterate from left to right (smaller x to larger x)
            if (p1.getX() > p2.getX()) {
                // Swap points and recurse
                drawLine(p2, p1, color);
                return;
            }
            // Calculate the linear function y = mx + b for the line
            double[] func = getLinearFunctionFrom2Points(p1, p2);
            // Iterate over x coordinates and calculate corresponding y
            for (int x = p1.getX(); x <= p2.getX(); x++) {
                // Calculate y using the linear function and round to nearest integer
                matrix[CalcLinearFunc(func, x)][x] = color;
            }
        }
        // Case 2: Line is more vertical than horizontal (dy > dx)
        else {
            // Ensure we iterate from bottom to top (smaller y to larger y)
            if (p1.getY() > p2.getY()) {
                // Swap points and recurse
                drawLine(p2, p1, color);
                return;
            }
            // For vertical lines, swap x and y coordinates to use the same algorithm
            // Create points with swapped coordinates: (y, x) instead of (x, y)
            Pixel2D reverseP1 = new Index2D(p1.getY(), p1.getX());
            Pixel2D reverseP2 = new Index2D(p2.getY(), p2.getX());
            // Calculate linear function for swapped coordinates
            double[] func = getLinearFunctionFrom2Points(reverseP1, reverseP2);
            // Iterate over y coordinates and calculate corresponding x
            for (int y = p1.getY(); y <= p2.getY(); y++) {
                // Calculate x using the linear function (note: func works on swapped coordinates)
                matrix[y][CalcLinearFunc(func, y)] = color;
            }
        }
    }

    /**
     * Draws a filled rectangle between two corner points.
     * The rectangle is defined by the minimum and maximum x and y coordinates of p1 and p2.
     * @param p1 one corner of the rectangle
     * @param p2 the opposite corner of the rectangle
     * @param color the color value to fill the rectangle with
     */
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        // Find the bounding box of the rectangle
        int xMin = Math.min(p1.getX(), p2.getX());
        int xMax = Math.max(p1.getX(), p2.getX());
        int yMin = Math.min(p1.getY(), p2.getY());
        int yMax = Math.max(p1.getY(), p2.getY());

        // Fill all pixels within the bounding box
        for (int y =  yMin; y <= yMax; y++) {
            for (int x =  xMin; x <= xMax; x++) {
                matrix[y][x] = color;
            }
        }
    }

    /**
     * Compares this map with another object for equality.
     * Two maps are equal if they have the same class, dimensions, and all corresponding pixels have the same values.
     * @param ob the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object ob) {
        // Null check
        if(ob == null) {
            return false;
        }
        // Class check - must be the same class
        if (ob.getClass() != this.getClass()) {
            return false;
        }
        // Dimension check - must have same width and height
        if (!sameDimensions((Map2D)ob)) {
            return false;
        }
        // Element-wise comparison - all pixels must match
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != ((Map)ob).matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
	/**
	 * Fills a connected component of pixels with a new color using flood fill algorithm.
	 * Starting from the given pixel, all adjacent pixels with the same color are filled with the new color.
	 * Uses a recursive flood fill algorithm: https://en.wikipedia.org/wiki/Flood_fill
	 * 
	 * @param xy the starting pixel position
	 * @param new_v the new color value to fill with
	 * @param cyclic if true, treats the map as cyclic (edges wrap around), otherwise edges are boundaries
	 * @return the number of pixels that were filled
	 */
	@Override
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
        // Optimization: if the new color is the same as the original color, no filling is needed
        if (new_v == matrix[xy.getY()][xy.getX()]) {
            return 0;
        }
        // Perform recursive flood fill starting from the given pixel
        // Store the original color to identify which pixels to fill
		return recursiveFill(xy, matrix[xy.getY()][xy.getX()], new_v, cyclic);
	}

	/**
	 * Finds the shortest path between two pixels using Breadth-First Search (BFS).
	 * The path avoids pixels with the obstacle color. Returns null if no path exists.
	 * Algorithm: https://en.wikipedia.org/wiki/Breadth-first_search
	 * 
	 * @param p1 the starting pixel
	 * @param p2 the destination pixel
	 * @param obsColor the color value representing obstacles that cannot be traversed
	 * @param cyclic if true, treats the map as cyclic (edges wrap around), otherwise edges are boundaries
	 * @return an array of Pixel2D representing the shortest path from p1 to p2, or null if no path exists
	 */
	@Override
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // The result path
        // Create a 2D array of nodes to track visited status and parent relationships
        Node[][] nodeArray = new Node[matrix.length][matrix[0].length];
        // Initialize all nodes with their corresponding pixel coordinates
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                nodeArray[y][x] = new Node(new Index2D(x, y));
            }
        }

        // Mark the starting node as visited (with null parent since it's the root)
        nodeArray[p1.getY()][p1.getX()].visit(null);

        // Initialize the BFS queue with the starting node
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(p1));

        // BFS main loop: process nodes level by level
        while (!q.isEmpty()) {
            Node current = q.poll();
            // Skip obstacle pixels
            if (getPixel(current.current) == obsColor) {
                continue;
            }
            // Check if we've reached the destination
            if (current.current.equals(p2)) {
                // Reconstruct the path by following parent pointers backwards
                ArrayList<Pixel2D> path = new ArrayList<>();
                while (current.parent != null) {
                    // Add current node to the front of the path
                    path.addFirst(current.current);
                    // Move to parent node
                    current = current.parent;
                }
                // Add the starting node (which has null parent)
                path.addFirst(current.current);
                // Convert ArrayList to array and return
                ans = path.toArray(new Pixel2D[0]);
                return ans;
            }

            // Get all valid neighbors of the current node
            ArrayList<Pixel2D> neighbors = getAllNeighbors((Pixel2D)current.current, cyclic);
            // Process each neighbor
            for (Pixel2D neighbor : neighbors) {
                // Only process unvisited neighbors
                if (!nodeArray[neighbor.getY()][neighbor.getX()].visited) {
                    // Mark neighbor as visited and set current as its parent
                    nodeArray[neighbor.getY()][neighbor.getX()].visit(current);
                    // Add neighbor to queue for further exploration
                    q.add(nodeArray[neighbor.getY()][neighbor.getX()]);
                }
            }
        }
        // No path found
        return null;
	}
    /**
     * Computes the shortest distance from a starting pixel to all reachable pixels in the map.
     * Uses BFS to find all distances, avoiding obstacle pixels.
     * Returns a new map where each pixel contains its distance from the start (or -1 if unreachable).
     * 
     * @param start the starting pixel position
     * @param obsColor the color value representing obstacles that cannot be traversed
     * @param cyclic if true, treats the map as cyclic (edges wrap around), otherwise edges are boundaries
     * @return a new Map2D where each pixel contains its shortest distance from start, or -1 if unreachable
     */
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        // Initialize result map with -1 (unreachable) for all pixels
        Map2D ans = new Map(getWidth(), getHeight(), -1);
        // If starting position is an obstacle, return map with all -1
        if (getPixel(start) == obsColor) {
            return ans;
        }
        // Set starting position distance to 0
        ans.setPixel(start, 0);

        // Initialize BFS queue with starting pixel
        Queue<Pixel2D> q = new LinkedList<>();
        q.add(start);

        // BFS main loop: explore all reachable pixels
        while (!q.isEmpty()) {
            Pixel2D current = q.poll();
            // Get all valid neighbors of current pixel
            ArrayList<Pixel2D> neighbors = getAllNeighbors(current, cyclic);

            // Process each neighbor
            for (Pixel2D neighbor : neighbors) {
                // Check if neighbor hasn't been visited yet (distance == -1) and is not an obstacle
                if (ans.getPixel(neighbor) == -1 && this.getPixel(neighbor) != obsColor) {
                    // Set neighbor's distance to current distance + 1
                    ans.setPixel(neighbor, ans.getPixel(current) + 1);
                    // Add neighbor to queue for further exploration
                    q.add(neighbor);
                }
            }
        }
        return ans;
    }
	////////////////////// Private Methods ///////////////////////

    /**
     * Gets all valid neighbors of a given pixel (up, down, left, right).
     * In non-cyclic mode, only returns neighbors within map boundaries.
     * In cyclic mode, edges wrap around (e.g., right edge connects to left edge).
     * 
     * @param p the pixel to get neighbors for
     * @param cyclic if true, map is cyclic (edges wrap around), otherwise edges are boundaries
     * @return an ArrayList of valid neighboring pixels
     */
    private ArrayList<Pixel2D> getAllNeighbors(Pixel2D p, boolean cyclic) {
        ArrayList<Pixel2D> neighbors = new ArrayList<>();

        // Add the up neighbor (increasing Y coordinate)
        if (p.getY() < getHeight() - 1) {
            // Standard case: not at top edge
            neighbors.add(new Index2D(p.getX(), p.getY() + 1));
        }
        else if (cyclic) {
            // Cyclic case: wrap to bottom of map
            neighbors.add(new Index2D(p.getX(), 0));
        }

        // Add the down neighbor (decreasing Y coordinate)
        if (p.getY() > 0) {
            // Standard case: not at bottom edge
            neighbors.add(new Index2D(p.getX(), p.getY() - 1));
        }
        else if (cyclic) {
            // Cyclic case: wrap to top of map
            neighbors.add(new Index2D(p.getX(), getHeight() - 1));
        }

        // Add the right neighbor (increasing X coordinate)
        if (p.getX() < getWidth() - 1) {
            // Standard case: not at right edge
            neighbors.add(new Index2D(p.getX() + 1, p.getY()));
        }
        else if (cyclic) {
            // Cyclic case: wrap to left edge of map
            neighbors.add(new Index2D(0, p.getY()));
        }

        // Add the left neighbor (decreasing X coordinate)
        if (p.getX() > 0) {
            // Standard case: not at left edge
            neighbors.add(new Index2D(p.getX() - 1, p.getY()));
        }
        else if (cyclic) {
            // Cyclic case: wrap to right edge of map
            neighbors.add(new Index2D(getWidth() - 1, p.getY()));
        }

        return neighbors;
    }

    /**
     * Calculates the linear function (y = mx + b) that passes through two points.
     * Used for line drawing algorithm to interpolate pixel positions.
     * 
     * @param p1 the first point
     * @param p2 the second point
     * @return a double array [m, b] representing the linear function y = mx + b
     */
    private double[] getLinearFunctionFrom2Points(Pixel2D p1, Pixel2D p2) {
        double[] result = new double[2];
        // Calculate slope: m = (y1 - y2) / (x1 - x2)
        double m = (double)(p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        // Calculate y-intercept: b = y - mx
        double b = p1.getY() - m*p1.getX();
        result[0] = m;  // Store slope
        result[1] = b;  // Store y-intercept
        return result;
    }

    /**
     * Calculates the y value for a given x using a linear function.
     * Uses rounding (adding 0.5 before casting to int) to get nearest integer.
     * 
     * @param func the linear function [m, b] representing y = mx + b
     * @param x the x coordinate
     * @return the y coordinate rounded to nearest integer
     */
    private int CalcLinearFunc(double[] func, int x) {
        // Calculate y = mx + b and round to nearest integer
        return (int)(func[0] * x + func[1] + 0.5);
    }

    /**
     * Recursively fills a connected component of pixels with the same color.
     * This is the core of the flood fill algorithm.
     * 
     * @param index the current pixel being processed
     * @param originColor the original color that should be replaced
     * @param new_v the new color to fill with
     * @param cyclic if true, treats the map as cyclic (edges wrap around)
     * @return the number of pixels filled by this recursive call and its children
     */
    public int recursiveFill(Pixel2D index, int originColor, int new_v,  boolean cyclic) {
        // Base case: if current pixel doesn't have the origin color, stop recursion
        if (matrix[index.getY()][index.getX()] != originColor) {
            return 0;
        }

        // Fill current pixel with new color
        matrix[index.getY()][index.getX()] = new_v;
        int result = 1;  // Count this pixel

        // Recursively fill all neighbors that have the same origin color
        ArrayList<Pixel2D> neighbors = getAllNeighbors(index, cyclic);
        for (Pixel2D neighbor : neighbors){
            // Add the count of pixels filled by recursive calls
            result += recursiveFill(neighbor, originColor, new_v, cyclic);
        }

        return result;
    }
}

/**
 * Helper class used for BFS pathfinding algorithm.
 * Stores a pixel, its parent in the search tree, and visited status.
 */
class Node {
    /** The pixel coordinate this node represents */
    public Pixel2D current;
    /** The parent node in the BFS tree (null for the root) */
    public Node parent;
    /** Whether this node has been visited during BFS */
    public boolean visited;

    /**
     * Constructs a new Node with the given pixel.
     * @param p the pixel coordinate for this node
     */
    public Node(Pixel2D p) {
        this.current = p;
        this.parent = null;
        this.visited = false;
    }

    /**
     * Marks this node as visited and sets its parent.
     * Used during BFS to track the path back to the starting node.
     * @param parent the parent node in the BFS tree
     */
    public void visit(Node parent) {
        this.parent = parent;
        this.visited = true;
    }
}