# I2CS Exercise 2 - Map2D Documentation

## Overview

This project implements a 2D raster map system with graphical visualization capabilities. It provides functionality for creating, manipulating, and visualizing 2D maps as integer matrices, with support for pathfinding algorithms, distance calculations, and interactive GUI operations.

## Table of Contents

1. [Getting Started](#getting-started)
2. [Interfaces](#interfaces)
   - [Map2D](#map2d-interface)
   - [Pixel2D](#pixel2d-interface)
3. [Classes](#classes)
   - [Map](#map-class)
   - [Index2D](#index2d-class)
   - [Ex2_GUI](#ex2_gui-class)
   - [StdDraw](#stddraw-class)
4. [Usage Examples](#usage-examples)
5. [Keyboard Shortcuts](#keyboard-shortcuts)

---

## Interfaces

### Map2D Interface

**Package:** `ex2`  
**Purpose:** Defines the contract for 2D raster maps (matrices, images, or mazes) over integers.

#### Key Methods

##### Initialization
- `void init(int w, int h, int v)` - Initialize a w×h map with all pixels set to value `v`
- `void init(int[][] arr)` - Initialize map from a 2D integer array (deep copy)

##### Accessors
- `int[][] getMap()` - Returns a deep copy of the underlying 2D matrix
- `int getWidth()` - Returns the width (number of columns)
- `int getHeight()` - Returns the height (number of rows)
- `int getPixel(int x, int y)` - Get pixel value at coordinates (x, y)
- `int getPixel(Pixel2D p)` - Get pixel value at pixel `p`
- `boolean isInside(Pixel2D p)` - Check if pixel `p` is within map boundaries
- `boolean sameDimensions(Map2D p)` - Check if this map has same dimensions as `p`

##### Mutators
- `void setPixel(int x, int y, int v)` - Set pixel at (x, y) to value `v`
- `void setPixel(Pixel2D p, int v)` - Set pixel at `p` to value `v`
- `void addMap2D(Map2D p)` - Add another map to this map (element-wise addition)
- `void mul(double scalar)` - Multiply all pixels by a scalar (cast to int)
- `void rescale(double sx, double sy)` - Rescale map dimensions

##### Drawing Operations
- `void drawCircle(Pixel2D center, double rad, int newColor)` - Draw a filled circle
- `void drawLine(Pixel2D p1, Pixel2D p2, int newColor)` - Draw a line between two points
- `void drawRect(Pixel2D p1, Pixel2D p2, int newColor)` - Draw a filled rectangle

##### Algorithms
- `int fill(Pixel2D p, int new_v, boolean cyclic)` - Flood fill connected component
- `Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic)` - Find shortest path avoiding obstacles
- `Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic)` - Calculate distances from start point to all pixels

#### Cyclic Mode

When `cyclic = true`, the map edges wrap around:
- Left edge of column 0 connects to right edge
- Right edge connects to left edge
- Top edge connects to bottom edge
- Bottom edge connects to top edge

This affects pathfinding and distance algorithms but not visual appearance.

---

### Pixel2D Interface

**Package:** `ex2`  
**Purpose:** Represents integer-based 2D coordinates in a raster/matrix.

#### Methods
- `int getX()` - Returns the X coordinate
- `int getY()` - Returns the Y coordinate
- `double distance2D(Pixel2D p2)` - Computes Euclidean distance to `p2`
- `String toString()` - String representation of coordinates
- `boolean equals(Object p)` - Equality comparison

---

## Classes

### Map Class

**Package:** `ex2`  
**Implements:** `Map2D`, `Serializable`  
**Purpose:** Concrete implementation of a 2D raster map as an integer matrix.

#### Constructors

```java
Map(int w, int h, int v)        // Create w×h map initialized to v
Map(int size)                   // Create size×size map initialized to 0
Map(int[][] data)               // Create map from 2D array (deep copy)
```

#### Key Features

##### Data Storage
- Internal storage: `int[][] matrix` with dimensions `[height][width]`
- Matrix indexing: `matrix[y][x]` where y is row, x is column

##### Drawing Algorithms

**drawCircle(Pixel2D center, double rad, int color)**
- Draws a filled circle using Euclidean distance
- Optimized with bounding box calculation
- Formula: `sqrt((x-cx)² + (y-cy)²) < radius`

**drawLine(Pixel2D p1, Pixel2D p2, int color)**
- Bresenham-like line algorithm
- Uses linear interpolation
- Handles horizontal, vertical, and diagonal lines
- Swaps coordinates when dy > dx for optimal rendering

**drawRect(Pixel2D p1, Pixel2D p2, int color)**
- Draws filled rectangle between two corner points
- Fills all pixels within the rectangular region

##### Pathfinding Algorithms

**shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic)**
- Uses Breadth-First Search (BFS) algorithm
- Finds shortest path avoiding pixels with `obsColor`
- Returns array of consecutive pixels from `p1` to `p2`
- Returns `null` if no path exists
- Supports cyclic mode for edge wrapping

**allDistance(Pixel2D start, int obsColor, boolean cyclic)**
- Uses BFS to calculate shortest distances
- Returns new `Map2D` with distance values
- Inaccessible pixels marked as -1
- Distance values represent number of steps from start

**fill(Pixel2D p, int new_v, boolean cyclic)**
- Flood fill algorithm (recursive)
- Fills connected component of pixels with same color
- Returns number of pixels filled
- Supports cyclic mode for edge wrapping

##### Helper Methods

- `getAllNeighbors(Pixel2D p, boolean cyclic)` - Get valid neighbors (up, down, left, right)
- `recursiveFill(Pixel2D index, int originColor, int new_v, boolean cyclic)` - Recursive flood fill implementation
- `getLinearFunctionFrom2Points(Pixel2D p1, Pixel2D p2)` - Calculate line equation for drawing

#### Internal Node Class

Used for BFS pathfinding:
```java
private class Node {
    Pixel2D current;
    Node parent;
    // Tracks path during BFS traversal
}
```

---

### Index2D Class

**Package:** `ex2`  
**Implements:** `Pixel2D`  
**Purpose:** Concrete implementation of 2D integer coordinates.

#### Constructors

```java
Index2D(int x, int y)           // Create coordinate (x, y)
Index2D(Pixel2D other)          // Copy coordinates from another Pixel2D
```

#### Constants

- `Index2D ORIGIN` - Constant representing (0, 0)

#### Methods

- `int getX()` - Returns X coordinate
- `int getY()` - Returns Y coordinate
- `double distance2D(Pixel2D p2)` - Euclidean distance: `sqrt((x1-x2)² + (y1-y2)²)`
- `String toString()` - Returns `"(x,y)"`
- `boolean equals(Object p)` - Equality based on x and y coordinates

#### Features

- Immutable coordinates (final fields)
- Type-safe coordinate representation
- Standard distance and equality operations

---

### Ex2_GUI Class

**Package:** `ex2`  
**Purpose:** Graphical User Interface for Map2D visualization and interaction.

#### Main Features

##### Interactive Map Visualization
- Real-time map rendering with color coding
- Value display on each cell
- Status messages and help text
- Cyclic mode indicator

##### File Operations
- `loadMap(String mapFileName)` - Load map from text file
- `saveMap(Map2D map, String mapFileName)` - Save map to text file
- File format: Space-delimited integers, one row per line

##### Drawing Functions

**drawMap(Map2D map)**
- Renders map with menu and status area
- Color mapping: -1=Black, values cycle through 20 distinct colors (mod 20)
- Displays pixel values as text on cells

**drawMap(Map2D map, String label)**
- Alternative drawing with custom label
- Includes help text and cyclic mode indicator
- Used in main interactive loop

##### Main Interactive Loop

The main method provides keyboard-driven interaction:

**Constants:**
- `MAP_SIZE = 10` - Default map dimensions
- `BLOCK_VALUE = -1` - Obstacle color
- `PATH_COLOR = 100` - Path visualization color (displays as Magenta)

**Keyboard Controls:**
- **S** - Shortest Path: Find path between two random points
- **A** - All Distance: Calculate and display distance map
- **M** - Maze: Generate random maze pattern
- **R** - Reset: Clear map (set all to 0)
- **C** - Cyclic: Toggle cyclic mode
- **H** - Help: Show help dialog

**Features:**
- Distance map persists until next key press
- Path pixels colored in magenta (100)
- Start/end points colored randomly (0-4)
- Maze generation uses random values 0-4
- No borders in cyclic mode
- 20 distinct colors for rich visualization

##### Helper Methods

**Pathfinding:**
- `handleShortestPathAction(Map2D map)` - Execute shortest path operation
- `handleDistanceAction(Map2D map)` - Execute distance calculation
- `randomValidPixel(int[][] mapData)` - Select random non-obstacle pixel

**Visualization:**
- `valueToColor(int value)` - Map integer values to colors
- `mazeGenerator(Map2D map)` - Generate maze pattern
- `showHelpDialog()` - Display centered help information

**Utilities:**
- `waitForKeyRelease(int keyCode)` - Prevent key repeat
- `handleMazeGenerationAction(Map2D map)` - Generate maze
- `handleResetAction(Map2D map)` - Reset map
- `handleCyclicToggleAction(Map2D map)` - Toggle cyclic mode

#### Color Mapping

The application supports 20 distinct colors that cycle based on the integer value:

```java
-1  → BLACK (obstacle)
0   → WHITE
1   → GREEN
2   → BLUE
3   → RED
4   → GRAY
5   → YELLOW
6   → CYAN
7   → ORANGE
8   → PINK
9   → PURPLE
10  → LIGHT PINK
11  → TEAL
12  → DARK ORANGE
13  → OLIVE
14  → SPRING GREEN
15  → DEEP PINK
16  → DEEP SKY BLUE
17  → DARK ORANGE
18  → LIME GREEN
19  → BLUE VIOLET
100 → MAGENTA (path visualization)
```

Colors cycle using modulo 20, so any integer value will map to one of these 20 colors. This provides rich visual variety for map visualization.

---

### StdDraw Class

**Package:** `ex2`  
**Purpose:** Standard drawing library for creating graphics windows and drawing shapes.

**Note:** This is a third-party library (Princeton StdDraw) used for GUI rendering. It provides:
- Window management
- Drawing primitives (squares, circles, lines, text)
- Color management
- Keyboard and mouse input handling
- Double buffering for smooth animation

**Key Methods Used:**
- `enableDoubleBuffering()` - Enable smooth rendering
- `setXscale()`, `setYscale()` - Set coordinate system
- `clear()`, `show()` - Clear and display buffer
- `filledSquare()`, `text()` - Drawing operations
- `isKeyPressed()`, `hasNextKeyTyped()` - Input handling
- `pause()` - Control animation speed

---

## Usage Examples

### Creating and Manipulating a Map

```java
// Create a 10x10 map initialized to 0
Map map = new Map(10);

// Set individual pixels
map.setPixel(5, 5, 1);  // Set (5,5) to value 1

// Draw shapes
map.drawCircle(new Index2D(5, 5), 3.0, 2);  // Blue circle
map.drawLine(new Index2D(0, 0), new Index2D(9, 9), 3);  // Red line
map.drawRect(new Index2D(1, 1), new Index2D(3, 3), 4);  // Gray rectangle

// Fill connected region
int filled = map.fill(new Index2D(2, 2), 5, false);

// Find shortest path
Pixel2D start = new Index2D(0, 0);
Pixel2D end = new Index2D(9, 9);
Pixel2D[] path = map.shortestPath(start, end, -1, false);  // Avoid obstacles (-1)

// Calculate distances
Map2D distances = map.allDistance(start, -1, false);
```

### Using the GUI

```java
// Run the interactive GUI
Ex2_GUI.main(new String[]{});

// Or use GUI functions directly
Map2D map = Ex2_GUI.loadMap("map.txt");
Ex2_GUI.drawMap(map);
Ex2_GUI.saveMap(map, "output.txt");
```

### Working with Coordinates

```java
// Create coordinates
Pixel2D p1 = new Index2D(3, 4);
Pixel2D p2 = new Index2D(7, 8);

// Calculate distance
double dist = p1.distance2D(p2);  // Euclidean distance

// Check equality
boolean same = p1.equals(p2);  // false

// String representation
String str = p1.toString();  // "(3,4)"
```

---

## Keyboard Shortcuts

When running `Ex2_GUI.main()`:

| Key | Action | Description |
|-----|--------|-------------|
| **S** | Shortest Path | Finds shortest path between two random valid points and colors the path magenta |
| **A** | All Distance | Calculates distances from a random point; displays distance map until next key |
| **M** | Maze | Generates a random maze pattern with values 0-4 |
| **R** | Reset | Clears the map (sets all pixels to 0) |
| **C** | Cyclic | Toggles cyclic mode (affects pathfinding algorithms) |
| **H** | Help | Displays help dialog with all keyboard shortcuts |

### Notes

- **Distance Map Persistence**: When pressing 'A', the distance map remains visible until you press another key (S, M, R, or C)
- **Path Visualization**: Shortest paths are colored magenta (value 100) for easy identification
- **Cyclic Mode**: When enabled, map edges wrap around for pathfinding, but visual appearance doesn't change
- **Maze Generation**: Creates borders only in non-cyclic mode; uses random values 0-4 for variety

---

## File Format

Maps are saved/loaded as text files with space-delimited integers:

```
0 0 0 1 1
0 -1 -1 0 1
0 0 0 0 1
2 2 2 2 2
```

Each line represents a row, and values are separated by spaces.
