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

    public static void drawMap(Map2D map) {
        int[][] matrix = map.getMap();
        int rows = matrix.length;
        int cols = matrix[0].length;

        double border = 0.005; // Border thickness
        double textSpace = 0.8;
        // Canvas setup
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows + textSpace);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenRadius(border);


        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                double x = col + 0.5;
                double y = rows - row - 0.5;

                // Convert matrix value to color (0–255 grayscale)
                int value = matrix[row][col];
                Color cellColor = valueToColor(value);

                // Draw filled square slightly smaller to leave grid border
                StdDraw.setPenColor(cellColor);
                StdDraw.filledSquare(x, y, 0.5 - border);

                // Write the value in text
                Color textColor = cellColor == StdDraw.BLACK ? StdDraw.WHITE : StdDraw.BLACK;
                StdDraw.setPenColor(textColor);
                StdDraw.text(x, y, String.valueOf(value));

                // Draw black grid border
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.square(x, y, 0.5);

            }
        }

        StdDraw.show();
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

    public static void main(String[] a) {
        Random rand = new Random(42);
        Map map = generateRandomMap(rand);
        saveMap(map, "map.txt");
        Map map2 = (Map)loadMap("map.txt");
        Pixel2D[] arr = map2.shortestPath(new Index2D(0, 1), new Index2D(4, 9), -1, true);
        for (Pixel2D pixel2D : arr) {
            Index2D index = (Index2D) pixel2D;
            System.out.println(index.getX() + " " + index.getY());
        }
        Map distaceMap = (Map)map2.allDistance(new Index2D(1, 0), -1, false);
        drawMap(distaceMap);
        saveMap(distaceMap, "distace.txt");
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
