package ex2;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {
    // Change this to whatever separator you want (",", "\t", ";", etc.)
    private static final String DELIMITER = " ";

    private static String statusMessage = "";  // Global status message
    
    public static void drawMap(Map2D map) {
        int[][] matrix = map.getMap();
        int rows = matrix.length;
        int cols = matrix[0].length;

        double border = 0.005;
        double menuHeight = 3.5;  // Increased to separate menu and status
        
        // Canvas setup
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows + menuHeight);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenRadius(border);

        // Draw menu in top area (above map)
        drawMenu(cols, rows, menuHeight);

        // Draw the map grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = col + 0.5;
                double y = rows + menuHeight - row - 0.5;

                int value = matrix[row][col];
                Color cellColor = valueToColor(value);

                StdDraw.setPenColor(cellColor);
                StdDraw.filledSquare(x, y, 0.5 - border);

                Color textColor = cellColor == StdDraw.BLACK ? StdDraw.WHITE : StdDraw.BLACK;
                StdDraw.setPenColor(textColor);
                StdDraw.text(x, y, String.valueOf(value));

                StdDraw.setPenColor(Color.BLACK);
                StdDraw.square(x, y, 0.5);
            }
        }
        
        // Draw status message in separate area (below menu, above map)
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        double statusY = rows + 0.5;  // Position status below menu area
        StdDraw.text(cols / 2.0, statusY, statusMessage.isEmpty() ? "Ready" : statusMessage);

        StdDraw.show();
    }
    
    /**
     * Draws a simple menu with keyboard shortcuts.
     */
    private static void drawMenu(double mapWidth, double mapHeight, double menuHeight) {
        // Menu background
        StdDraw.setPenColor(new Color(240, 240, 240));
        StdDraw.filledRectangle(mapWidth / 2.0, mapHeight + menuHeight - 1.0, mapWidth / 2.0, 1.5);
        
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
        double yStart = mapHeight + menuHeight - 0.4;
        
        // Menu title
        StdDraw.text(mapWidth / 2.0, yStart, "Commands (press keys):");
        
        // Simple menu items
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 11));
        double y = yStart - 0.4;
        StdDraw.text(mapWidth / 2.0, y, "R-Reset | C-Circle | L-Line | F-Fill | P-Path | D-Distance | S-Save | H-Help | Q-Quit");
        
        y -= 0.35;
        StdDraw.text(mapWidth / 2.0, y, "Click on map to interact");
    }
    
    /**
     * Converts mouse click coordinates to map grid coordinates.
     */
    private static Pixel2D mouseToMapCoordinates(double mouseX, double mouseY, int mapRows, double menuHeight) {
        int x = (int) mouseX;
        int y = mapRows - 1 - (int)(mouseY - menuHeight);
        return new Index2D(x, y);
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        List<String> lines = new ArrayList<>();

        // Read from file all the lines
        try (BufferedReader reader = new BufferedReader(new FileReader(mapFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read matrix file: " + mapFileName, e);
        }

        // Determine number of rows and columns
        int rows = lines.size();
        int cols = lines.getFirst().split(DELIMITER).length;

        // Allocate the matrix according to number of rows and columns
        int[][] matrix = new int[rows][cols];

        // Parse stored lines into the matrix
        for (int i = 0; i < rows; i++) {
            String[] tokens = lines.get(i).split(DELIMITER);
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(tokens[j]);
            }
        }

        return new Map(matrix);
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        int[][] matrix = map.getMap();
        /*
         * FileWriter for file access
         * BufferedWriter for better performance
         * PrintWriter for easier implementation thanks to formatting
         */
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(mapFileName)))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.print(matrix[i][j]);

                    // Write delimiter between elements (not after last)
                    if (j < matrix[i].length - 1) {
                        writer.print(DELIMITER);
                    }
                }
                writer.println(); // new line after each row
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to write map to file: " + mapFileName, e);
        }
    }

    // Constants for the alternative main implementation
    private static final int MAP_SIZE = 10;
    private static final int BLOCK_VALUE = -1;
    private static final int PATH_COLOR = 100;  // Unique color not used in matrix (0-4 are used)
    private static final String TOP_LABEL = "Map Visualization - Press S/A/M/R/C/H for Help";
    private static boolean cyclic = false;
    private static Random random = new Random();
    private static Map2D distanceMapDisplay = null;  // Store distance map to keep displaying

    /**
     * Main function with interactive GUI using StdDraw.
     * Alternative implementation with S/A/M/R/C key features.
     */
    public static void main(String[] a) {
        Map2D gameMap = new Map(MAP_SIZE);
        StdDraw.enableDoubleBuffering();
        
        // Main game loop
        while (true) {
            StdDraw.clear();
            // Display distance map if it exists, otherwise display regular map
            if (distanceMapDisplay != null) {
                drawMap(distanceMapDisplay, TOP_LABEL);
            } else {
                drawMap(gameMap, TOP_LABEL);
            }
            
            // Process keyboard input
            if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                distanceMapDisplay = null;  // Clear distance map when new action
                handleShortestPathAction(gameMap);
                waitForKeyRelease(KeyEvent.VK_S);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                handleDistanceAction(gameMap);
                waitForKeyRelease(KeyEvent.VK_A);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_M)) {
                distanceMapDisplay = null;  // Clear distance map when new action
                handleMazeGenerationAction(gameMap);
                waitForKeyRelease(KeyEvent.VK_M);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                distanceMapDisplay = null;  // Clear distance map when new action
                handleResetAction(gameMap);
                waitForKeyRelease(KeyEvent.VK_R);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_C)) {
                distanceMapDisplay = null;  // Clear distance map when new action
                handleCyclicToggleAction(gameMap);
                waitForKeyRelease(KeyEvent.VK_C);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_H)) {
                showHelpDialog();
                waitForKeyRelease(KeyEvent.VK_H);
            }
            
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
    
    /**
     * Handles the shortest path action (S key).
     */
    private static void handleShortestPathAction(Map2D map) {
        Pixel2D start = randomValidPixel(map.getMap());
        Pixel2D end = randomValidPixel(map.getMap());
        Pixel2D[] path = map.shortestPath(start, end, BLOCK_VALUE, cyclic);
        
        // Color the path pixels instead of printing
        if (path != null) {
            for (Pixel2D p : path) {
                map.setPixel(p, PATH_COLOR);
            }
        }
        
        // Mark start and end with random colors 0-4
        map.setPixel(start, random.nextInt(5));
        map.setPixel(end, random.nextInt(5));
        drawMap(map, TOP_LABEL);
    }
    
    /**
     * Handles the distance calculation action (A key).
     */
    private static void handleDistanceAction(Map2D map) {
        Pixel2D point = randomValidPixel(map.getMap());
        map.setPixel(point, 0);
        System.out.println(map.getPixel(point));
        // Store distance map to keep displaying until next key
        distanceMapDisplay = map.allDistance(point, BLOCK_VALUE, cyclic);
        drawMap(distanceMapDisplay, TOP_LABEL);
    }
    
    /**
     * Handles the maze generation action (M key).
     */
    private static void handleMazeGenerationAction(Map2D map) {
        map.mul(0);
        mazeGenerator(map);
        StdDraw.pause(20);
        drawMap(map, TOP_LABEL);
    }
    
    /**
     * Handles the reset action (R key).
     */
    private static void handleResetAction(Map2D map) {
        map.mul(0);
        StdDraw.pause(20);
        drawMap(map, TOP_LABEL);
    }
    
    /**
     * Handles the cyclic mode toggle action (C key).
     */
    private static void handleCyclicToggleAction(Map2D map) {
        cyclic = !cyclic;
        drawMap(map, TOP_LABEL);
    }
    
    /**
     * Waits for a key to be released.
     */
    private static void waitForKeyRelease(int keyCode) {
        while (StdDraw.isKeyPressed(keyCode)) {
            StdDraw.pause(20);
        }
    }
    
    /**
     * Draws the map with a top label and help text.
     */
    private static void drawMap(Map2D map, String label) {
        int[][] matrix = map.getMap();
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Set up coordinate system with space for help text
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows + 4);
        
        // Draw header
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        StdDraw.text(cols / 2.0, rows + 3.5, label);
        
        // Draw help text
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        StdDraw.text(cols / 2.0, rows + 2.5, "S-Shortest Path | A-All Distance | M-Maze | R-Reset | C-Toggle Cyclic | H-Help");
        
        // Draw cyclic mode indicator
        String modeText = cyclic ? "Cyclic Mode: ON" : "Cyclic Mode: OFF";
        StdDraw.text(cols / 2.0, rows + 1.5, modeText);
        
        // Draw map cells
        double cellSize = 0.45;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = col + 0.5;
                double y = rows - row - 0.5;
                int value = matrix[row][col];
                
                Color cellColor = valueToColor(value);
                StdDraw.setPenColor(cellColor);
                StdDraw.filledSquare(x, y, cellSize);
                
                // Draw value text
                Color textColor = (value == BLOCK_VALUE) ? Color.WHITE : Color.BLACK;
                StdDraw.setPenColor(textColor);
                StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 8));
                StdDraw.text(x, y, String.valueOf(value));
                
                // Draw cell border
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.setPenRadius(0.002);
                StdDraw.square(x, y, cellSize);
            }
        }
        StdDraw.setPenRadius();
    }
    
    /**
     * Selects a random valid pixel from the map (not an obstacle).
     */
    private static Pixel2D randomValidPixel(int[][] mapData) {
        List<Pixel2D> validPixels = new ArrayList<>();
        
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                if (mapData[row][col] != BLOCK_VALUE) {
                    validPixels.add(new Index2D(col, row));
                }
            }
        }
        
        if (validPixels.isEmpty()) {
            return new Index2D(0, 0);
        }
        
        return validPixels.get(random.nextInt(validPixels.size()));
    }
    
    /**
     * Shows help dialog with keyboard shortcuts.
     */
    private static void showHelpDialog() {
        // Set up coordinate system for help dialog
        double width = 20;
        double height = 20;
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.WHITE);
        
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        StdDraw.text(width / 2.0, height - 1.5, "Help - Keyboard Shortcuts");
        
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 13));
        String[] help = {
            "S - Shortest Path: Find path between two random points",
            "A - All Distance: Calculate distances from random point",
            "M - Maze: Generate a random maze",
            "R - Reset: Clear the map",
            "C - Cyclic: Toggle cyclic mode (wraps edges)",
            "H - Help: Show this help dialog",
            "",
            "Cyclic mode affects pathfinding algorithms only,",
            "not the visual appearance of the map."
        };
        
        double startY = height - 3.5;
        double lineSpacing = 1.3;
        double centerX = width / 2.0;
        
        for (String line : help) {
            StdDraw.text(centerX, startY, line);
            startY -= lineSpacing;
        }
        
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
        StdDraw.text(centerX, 2, "Press any key to continue...");
        StdDraw.show();
        
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(50);
        }
        StdDraw.nextKeyTyped();
    }
    
    /**
     * Generates a maze pattern on the map.
     */
    private static void mazeGenerator(Map2D map) {
        int[][] mapData = map.getMap();
        int height = mapData.length;
        int width = mapData[0].length;
        
        // Create a simple maze pattern with walls and paths
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Only create borders if NOT in cyclic mode
                if (!cyclic && (row == 0 || row == height - 1 || col == 0 || col == width - 1)) {
                    map.setPixel(col, row, BLOCK_VALUE);
                } else if (row % 2 == 0 && col % 2 == 0) {
                    // Create some internal walls
                    if (random.nextDouble() < 0.3) {
                        map.setPixel(col, row, BLOCK_VALUE);
                    } else {
                        // Use random number between 0 and 4
                        map.setPixel(col, row, random.nextInt(5));
                    }
                } else {
                    // Create paths with random numbers 0-4
                    map.setPixel(col, row, random.nextInt(5));
                }
            }
        }
        
        // Ensure start and end points are clear (not obstacles)
        map.setPixel(1, 1, random.nextInt(5));
        map.setPixel(width - 2, height - 2, random.nextInt(5));
    }
    
    /**
     * Handles mouse clicks on the map.
     */
    private static void handleMapClick(Map map, Pixel2D clicked, String mode, int color, 
                                       int obsColor, double menuHeight, 
                                       int mapRows, Pixel2D[] state) {
        Pixel2D firstPoint = state[0];
        
        switch (mode) {
            case "circle":
                map.drawCircle(clicked, 2.0, color);
                showStatus("Circle drawn");
                drawMap(map);
                break;
                
            case "line":
                if (firstPoint == null) {
                    state[0] = clicked;
                    showStatus("Click end point");
                    drawMap(map);
                } else {
                    map.drawLine(firstPoint, clicked, color);
                    showStatus("Line drawn");
                    state[0] = null;
                    drawMap(map);
                }
                break;
                
            case "fill":
                map.fill(clicked, color, false);
                showStatus("Filled");
                drawMap(map);
                break;
                
            case "path":
                if (firstPoint == null) {
                    state[0] = clicked;
                    showStatus("Click end point");
                    drawMap(map);
                } else {
                    Pixel2D[] path = map.shortestPath(firstPoint, clicked, obsColor, false);
                    if (path != null) {
                        for (Pixel2D p : path) {
                            map.setPixel(p, 99);
                        }
                        showStatus("Path found: " + path.length + " steps");
                    } else {
                        showStatus("No path");
                    }
                    state[0] = null;
                    drawMap(map);
                }
                break;
                
            case "distance":
                Map2D distMap = map.allDistance(clicked, obsColor, false);
                showStatus("Distance map shown");
                drawMap(distMap);
                break;
                
            default:
                map.setPixel(clicked, color);
                showStatus("Pixel set");
                drawMap(map);
                break;
        }
    }
    
    /**
     * Sets the status message.
     */
    private static void showStatus(String message) {
        statusMessage = message;
    }
    
    

    /// ///////////// Private functions ///////////////
    public static Map generateRandomMap (Random rand) {

        int height = rand.nextInt(9) + 2;
        int width = rand.nextInt(9) + 2;
        Map map = new Map(width, height, -1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map.setPixel(j, i, rand.nextInt(6) - 1);
            }
        }
        return map;
    }

    private static Color valueToColor(int value) {
        if (value == PATH_COLOR) {
            return Color.MAGENTA;  // Distinct color for path that's not used in matrix
        }
        if (value == -1)
        {
            return StdDraw.BLACK;
        }
        return switch (value % 5) {
            case 0 -> StdDraw.WHITE;
            case 1 -> StdDraw.GREEN;
            case 2 -> StdDraw.BLUE;
            case 3 -> StdDraw.RED;
            case 4 -> StdDraw.GRAY;
            default -> StdDraw.BLACK;
        };
    }
}
