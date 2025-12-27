package ex2;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        //
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
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        drawMap(map);
    }
    /// ///////////// Private functions ///////////////
}
