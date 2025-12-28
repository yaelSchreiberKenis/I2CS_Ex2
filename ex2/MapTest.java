package ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Map class.
 * This class tests all methods of Map, including edge cases and various scenarios.
 * Tests cover constructors, initialization, pixel operations, drawing methods,
 * transformations, pathfinding, and flood fill algorithms.
 */
class MapTest {
    private Map map1x1;
    private Map map3x3;
    private Map map5x5;
    private Map map10x10;
    private int[][] testArray3x3;
    private int[][] testArray5x5;

    @BeforeEach
    void setUp() {
        // Initialize test maps with different sizes
        map1x1 = new Map(1, 1, 0);
        map3x3 = new Map(3, 3, 0);
        map5x5 = new Map(5, 5, 0);
        map10x10 = new Map(10, 10, 0);
        
        // Initialize test arrays
        testArray3x3 = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        testArray5x5 = new int[][]{
            {0, 1, 2, 3, 4},
            {5, 6, 7, 8, 9},
            {10, 11, 12, 13, 14},
            {15, 16, 17, 18, 19},
            {20, 21, 22, 23, 24}
        };
    }

    // ==================== Constructor Tests ====================

    /**
     * Test constructor with width, height, and initial value
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithDimensions() {
        Map map = new Map(5, 3, 42);
        assertEquals(5, map.getWidth());
        assertEquals(3, map.getHeight());
        // Check that all pixels are initialized to 42
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                assertEquals(42, map.getPixel(x, y));
            }
        }
    }

    /**
     * Test square constructor
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSquareConstructor() {
        Map map = new Map(7);
        assertEquals(7, map.getWidth());
        assertEquals(7, map.getHeight());
        // Check that all pixels are initialized to 0
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                assertEquals(0, map.getPixel(x, y));
            }
        }
    }

    /**
     * Test constructor with 2D array
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithArray() {
        Map map = new Map(testArray3x3);
        assertEquals(3, map.getWidth());
        assertEquals(3, map.getHeight());
        // Verify all values are copied correctly
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(testArray3x3[i][j], map.getPixel(j, i));
            }
        }
    }

    /**
     * Test constructor with single pixel map
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructor1x1() {
        Map map = new Map(1, 1, 99);
        assertEquals(1, map.getWidth());
        assertEquals(1, map.getHeight());
        assertEquals(99, map.getPixel(0, 0));
    }

    /**
     * Test constructor with large map
     */
    @Test
    @Timeout(value = 2, unit = SECONDS)
    void testConstructorLargeMap() {
        Map map = new Map(100, 100, 0);
        assertEquals(100, map.getWidth());
        assertEquals(100, map.getHeight());
    }

    // ==================== Init Tests ====================

    /**
     * Test init with width, height, and value
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testInitWithDimensions() {
        Map map = new Map(2, 2, 0);
        map.init(4, 3, 77);
        assertEquals(4, map.getWidth());
        assertEquals(3, map.getHeight());
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 3; y++) {
                assertEquals(77, map.getPixel(x, y));
            }
        }
    }

    /**
     * Test init with 2D array
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testInitWithArray() {
        Map map = new Map(2, 2, 0);
        map.init(testArray5x5);
        assertEquals(5, map.getWidth());
        assertEquals(5, map.getHeight());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(testArray5x5[i][j], map.getPixel(j, i));
            }
        }
    }

    /**
     * Test init with negative initial value
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testInitWithNegativeValue() {
        Map map = new Map(3, 3, -5);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                assertEquals(-5, map.getPixel(x, y));
            }
        }
    }

    // ==================== GetMap Tests ====================

    /**
     * Test getMap returns a deep copy
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetMapDeepCopy() {
        Map map = new Map(testArray3x3);
        int[][] copy = map.getMap();
        // Modify the copy
        copy[0][0] = 999;
        // Original map should not be affected
        assertNotEquals(999, map.getPixel(0, 0));
    }

    /**
     * Test getMap with modified map
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetMapAfterModification() {
        Map map = new Map(3, 3, 0);
        map.setPixel(1, 1, 42);
        int[][] copy = map.getMap();
        assertEquals(42, copy[1][1]);
        assertEquals(0, copy[0][0]);
    }

    // ==================== GetWidth/GetHeight Tests ====================

    /**
     * Test getWidth and getHeight
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetDimensions() {
        Map map = new Map(7, 11, 0);
        assertEquals(7, map.getWidth());
        assertEquals(11, map.getHeight());
    }

    /**
     * Test dimensions after init
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDimensionsAfterInit() {
        Map map = new Map(2, 2, 0);
        assertEquals(2, map.getWidth());
        assertEquals(2, map.getHeight());
        map.init(8, 6, 0);
        assertEquals(8, map.getWidth());
        assertEquals(6, map.getHeight());
    }

    // ==================== GetPixel Tests ====================

    /**
     * Test getPixel with valid coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetPixelValid() {
        Map map = new Map(testArray3x3);
        assertEquals(0, map.getPixel(0, 0));
        assertEquals(4, map.getPixel(1, 1));
        assertEquals(8, map.getPixel(2, 2));
    }

    /**
     * Test getPixel with Pixel2D parameter
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetPixelWithPixel2D() {
        Map map = new Map(testArray3x3);
        Pixel2D p = new Index2D(1, 2);
        assertEquals(7, map.getPixel(p));
    }

    /**
     * Test getPixel with out of bounds coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetPixelOutOfBounds() {
        Map map = new Map(3, 3, 0);
        // Test various out-of-bounds cases
        assertEquals(-1, map.getPixel(3, 0));   // x too large
        assertEquals(-1, map.getPixel(0, 3));   // y too large
        assertEquals(-1, map.getPixel(-1, 0));  // x negative
        assertEquals(-1, map.getPixel(0, -1));  // y negative
    }

    /**
     * Test getPixel at boundary
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetPixelAtBoundary() {
        Map map = new Map(5, 5, 42);
        assertEquals(42, map.getPixel(4, 0));   // Right edge
        assertEquals(42, map.getPixel(0, 4));   // Bottom edge
        assertEquals(42, map.getPixel(4, 4));   // Corner
    }

    // ==================== SetPixel Tests ====================

    /**
     * Test setPixel with coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSetPixel() {
        Map map = new Map(3, 3, 0);
        map.setPixel(1, 1, 99);
        assertEquals(99, map.getPixel(1, 1));
        assertEquals(0, map.getPixel(0, 0));  // Other pixels unchanged
    }

    /**
     * Test setPixel with Pixel2D parameter
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSetPixelWithPixel2D() {
        Map map = new Map(3, 3, 0);
        Pixel2D p = new Index2D(2, 0);
        map.setPixel(p, 77);
        assertEquals(77, map.getPixel(p));
    }

    /**
     * Test setPixel with negative value
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSetPixelNegative() {
        Map map = new Map(3, 3, 0);
        map.setPixel(1, 1, -42);
        assertEquals(-42, map.getPixel(1, 1));
    }

    /**
     * Test setPixel at multiple positions
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSetPixelMultiple() {
        Map map = new Map(3, 3, 0);
        map.setPixel(0, 0, 1);
        map.setPixel(1, 1, 2);
        map.setPixel(2, 2, 3);
        assertEquals(1, map.getPixel(0, 0));
        assertEquals(2, map.getPixel(1, 1));
        assertEquals(3, map.getPixel(2, 2));
    }

    // ==================== IsInside Tests ====================

    /**
     * Test isInside with valid coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testIsInsideValid() {
        Map map = new Map(5, 5, 0);
        assertTrue(map.isInside(new Index2D(0, 0)));
        assertTrue(map.isInside(new Index2D(4, 4)));
        assertTrue(map.isInside(new Index2D(2, 3)));
    }

    /**
     * Test isInside with invalid coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testIsInsideInvalid() {
        Map map = new Map(5, 5, 0);
        assertFalse(map.isInside(new Index2D(5, 0)));   // x equals width
        assertFalse(map.isInside(new Index2D(0, 5)));   // y equals height
        assertFalse(map.isInside(new Index2D(-1, 0)));  // negative x
        assertFalse(map.isInside(new Index2D(0, -1)));  // negative y
        assertFalse(map.isInside(new Index2D(10, 10))); // both out of bounds
    }

    /**
     * Test isInside at boundaries
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testIsInsideBoundary() {
        Map map = new Map(3, 3, 0);
        assertTrue(map.isInside(new Index2D(2, 2)));    // Last valid pixel
        assertFalse(map.isInside(new Index2D(3, 2)));   // x = width
        assertFalse(map.isInside(new Index2D(2, 3)));   // y = height
    }

    // ==================== SameDimensions Tests ====================

    /**
     * Test sameDimensions with equal dimensions
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSameDimensionsEqual() {
        Map map1 = new Map(5, 3, 0);
        Map map2 = new Map(5, 3, 0);
        assertTrue(map1.sameDimensions(map2));
    }

    /**
     * Test sameDimensions with different dimensions
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSameDimensionsDifferent() {
        Map map1 = new Map(5, 3, 0);
        Map map2 = new Map(3, 5, 0);
        assertFalse(map1.sameDimensions(map2));
        
        Map map3 = new Map(5, 3, 0);
        Map map4 = new Map(5, 4, 0);
        assertFalse(map3.sameDimensions(map4));
    }

    /**
     * Test sameDimensions with null
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testSameDimensionsNull() {
        Map map = new Map(5, 3, 0);
        assertFalse(map.sameDimensions(null));
    }

    // ==================== AddMap2D Tests ====================

    /**
     * Test addMap2D with same dimensions
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAddMap2D() {
        Map map1 = new Map(new int[][]{{1, 2}, {3, 4}});
        Map map2 = new Map(new int[][]{{5, 6}, {7, 8}});
        map1.addMap2D(map2);
        assertEquals(6, map1.getPixel(0, 0));  // 1 + 5
        assertEquals(8, map1.getPixel(1, 0));  // 2 + 6
        assertEquals(10, map1.getPixel(0, 1)); // 3 + 7
        assertEquals(12, map1.getPixel(1, 1)); // 4 + 8
    }

    /**
     * Test addMap2D with different dimensions (should do nothing)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAddMap2DDifferentDimensions() {
        Map map1 = new Map(new int[][]{{1, 2}, {3, 4}});
        Map map2 = new Map(3, 3, 1);
        int[][] original = map1.getMap();
        map1.addMap2D(map2);
        // Map1 should remain unchanged
        assertArrayEquals(original, map1.getMap());
    }

    /**
     * Test addMap2D with negative values
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAddMap2DNegative() {
        Map map1 = new Map(new int[][]{{5, -3}, {10, -7}});
        Map map2 = new Map(new int[][]{{-2, 4}, {-8, 1}});
        map1.addMap2D(map2);
        assertEquals(3, map1.getPixel(0, 0));   // 5 + (-2)
        assertEquals(1, map1.getPixel(1, 0));   // -3 + 4
        assertEquals(2, map1.getPixel(0, 1));   // 10 + (-8)
        assertEquals(-6, map1.getPixel(1, 1));  // -7 + 1
    }

    // ==================== Mul Tests ====================

    /**
     * Test mul with positive scalar
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testMulPositive() {
        Map map = new Map(new int[][]{{1, 2}, {3, 4}});
        map.mul(2.0);
        assertEquals(2, map.getPixel(0, 0));
        assertEquals(4, map.getPixel(1, 0));
        assertEquals(6, map.getPixel(0, 1));
        assertEquals(8, map.getPixel(1, 1));
    }

    /**
     * Test mul with negative scalar
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testMulNegative() {
        Map map = new Map(new int[][]{{2, 4}, {6, 8}});
        map.mul(-1.0);
        assertEquals(-2, map.getPixel(0, 0));
        assertEquals(-4, map.getPixel(1, 0));
        assertEquals(-6, map.getPixel(0, 1));
        assertEquals(-8, map.getPixel(1, 1));
    }

    /**
     * Test mul with fractional scalar (truncation)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testMulFractional() {
        Map map = new Map(new int[][]{{5, 7}, {9, 11}});
        map.mul(1.7);
        // Values should be truncated: 5*1.7=8.5 -> 8, 7*1.7=11.9 -> 11, etc.
        assertEquals(8, map.getPixel(0, 0));  // 5 * 1.7 = 8.5 -> 8
        assertEquals(11, map.getPixel(1, 0)); // 7 * 1.7 = 11.9 -> 11
    }

    /**
     * Test mul with zero scalar
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testMulZero() {
        Map map = new Map(new int[][]{{1, 2}, {3, 4}});
        map.mul(0.0);
        assertEquals(0, map.getPixel(0, 0));
        assertEquals(0, map.getPixel(1, 0));
        assertEquals(0, map.getPixel(0, 1));
        assertEquals(0, map.getPixel(1, 1));
    }

    // ==================== Rescale Tests ====================

    /**
     * Test rescale with scale factor 1.0 (no change)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testRescaleNoChange() {
        Map map = new Map(new int[][]{{1, 2}, {3, 4}});
        map.rescale(1.0, 1.0);
        assertEquals(2, map.getWidth());
        assertEquals(2, map.getHeight());
    }

    /**
     * Test rescale with scale factor 2.0 (enlarge)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testRescaleEnlarge() {
        Map map = new Map(new int[][]{{1, 2}, {3, 4}});
        map.rescale(2.0, 2.0);
        assertEquals(4, map.getWidth());
        assertEquals(4, map.getHeight());
    }

    /**
     * Test rescale with scale factor 0.5 (shrink)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testRescaleShrink() {
        Map map = new Map(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        map.rescale(0.5, 0.5);
        assertEquals(2, map.getWidth());
        assertEquals(2, map.getHeight());
    }

    /**
     * Test rescale with different x and y scales
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testRescaleDifferentScales() {
        Map map = new Map(new int[][]{{1, 2}, {3, 4}});
        map.rescale(2.0, 3.0);
        assertEquals(4, map.getWidth());
        assertEquals(6, map.getHeight());
    }

    /**
     * Test rescale with fractional scale
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testRescaleFractional() {
        Map map = new Map(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        map.rescale(1.5, 1.5);
        assertEquals(4, map.getWidth());  // 3 * 1.5 = 4.5 -> 4
        assertEquals(4, map.getHeight()); // 3 * 1.5 = 4.5 -> 4
    }

    // ==================== DrawCircle Tests ====================

    /**
     * Test drawCircle at center of map
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawCircleCenter() {
        Map map = new Map(10, 10, 0);
        Pixel2D center = new Index2D(5, 5);
        map.drawCircle(center, 3.0, 1);
        // Check that pixels inside circle are colored
        assertTrue(map.getPixel(5, 5) == 1);  // Center
        assertTrue(map.getPixel(7, 5) == 1);  // Right edge
        assertTrue(map.getPixel(5, 7) == 1);  // Bottom edge
        // Check that pixels outside circle are not colored
        assertTrue(map.getPixel(8, 5) == 0);  // Outside
    }

    /**
     * Test drawCircle at corner (clipped)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawCircleCorner() {
        Map map = new Map(5, 5, 0);
        Pixel2D center = new Index2D(0, 0);
        map.drawCircle(center, 5.0, 1);
        // Circle should be clipped to map bounds
        assertTrue(map.getPixel(0, 0) == 1);
        assertTrue(map.getPixel(4, 0) == 1);
    }

    /**
     * Test drawCircle with small radius
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawCircleSmall() {
        Map map = new Map(10, 10, 0);
        Pixel2D center = new Index2D(5, 5);
        map.drawCircle(center, 0.5, 1);
        // Only center pixel should be colored
        assertEquals(1, map.getPixel(5, 5));
        assertEquals(0, map.getPixel(5, 6));
    }

    /**
     * Test drawCircle with large radius
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawCircleLarge() {
        Map map = new Map(10, 10, 0);
        Pixel2D center = new Index2D(5, 5);
        map.drawCircle(center, 10.0, 1);
        // Most pixels should be colored (clipped to map bounds)
        assertTrue(map.getPixel(0, 0) == 1);
        assertTrue(map.getPixel(9, 9) == 1);
    }

    // ==================== DrawLine Tests ====================

    /**
     * Test drawLine horizontal
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawLineHorizontal() {
        Map map = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(2, 5);
        Pixel2D p2 = new Index2D(7, 5);
        map.drawLine(p1, p2, 1);
        // Check that line pixels are colored
        for (int x = 2; x <= 7; x++) {
            assertEquals(1, map.getPixel(x, 5));
        }
    }

    /**
     * Test drawLine vertical
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawLineVertical() {
        Map map = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(5, 2);
        Pixel2D p2 = new Index2D(5, 7);
        map.drawLine(p1, p2, 1);
        // Check that line pixels are colored
        for (int y = 2; y <= 7; y++) {
            assertEquals(1, map.getPixel(5, y));
        }
    }

    /**
     * Test drawLine diagonal
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawLineDiagonal() {
        Map map = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(1, 1);
        Pixel2D p2 = new Index2D(5, 5);
        map.drawLine(p1, p2, 1);
        // Check that diagonal pixels are colored
        assertEquals(1, map.getPixel(1, 1));
        assertEquals(1, map.getPixel(3, 3));
        assertEquals(1, map.getPixel(5, 5));
    }

    /**
     * Test drawLine same point
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawLineSamePoint() {
        Map map = new Map(10, 10, 0);
        Pixel2D p = new Index2D(5, 5);
        map.drawLine(p, p, 1);
        assertEquals(1, map.getPixel(5, 5));
        assertEquals(0, map.getPixel(5, 4));
    }

    /**
     * Test drawLine reverse direction
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawLineReverse() {
        Map map = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(7, 5);
        Pixel2D p2 = new Index2D(2, 5);
        map.drawLine(p1, p2, 1);
        // Should draw same line as forward direction
        for (int x = 2; x <= 7; x++) {
            assertEquals(1, map.getPixel(x, 5));
        }
    }

    // ==================== DrawRect Tests ====================

    /**
     * Test drawRect with standard rectangle
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawRect() {
        Map map = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(2, 3);
        Pixel2D p2 = new Index2D(5, 6);
        map.drawRect(p1, p2, 1);
        // Check all pixels in rectangle are colored
        for (int x = 2; x <= 5; x++) {
            for (int y = 3; y <= 6; y++) {
                assertEquals(1, map.getPixel(x, y));
            }
        }
        // Check pixels outside are not colored
        assertEquals(0, map.getPixel(1, 2));
        assertEquals(0, map.getPixel(6, 7));
    }

    /**
     * Test drawRect with reversed corners
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawRectReversed() {
        Map map = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(5, 6);
        Pixel2D p2 = new Index2D(2, 3);
        map.drawRect(p1, p2, 1);
        // Should draw same rectangle
        for (int x = 2; x <= 5; x++) {
            for (int y = 3; y <= 6; y++) {
                assertEquals(1, map.getPixel(x, y));
            }
        }
    }

    /**
     * Test drawRect single pixel
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawRectSinglePixel() {
        Map map = new Map(10, 10, 0);
        Pixel2D p = new Index2D(5, 5);
        map.drawRect(p, p, 1);
        assertEquals(1, map.getPixel(5, 5));
        assertEquals(0, map.getPixel(4, 5));
    }

    /**
     * Test drawRect covering entire map
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDrawRectFullMap() {
        Map map = new Map(5, 5, 0);
        Pixel2D p1 = new Index2D(0, 0);
        Pixel2D p2 = new Index2D(4, 4);
        map.drawRect(p1, p2, 1);
        // All pixels should be colored
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertEquals(1, map.getPixel(x, y));
            }
        }
    }

    // ==================== Equals Tests ====================

    /**
     * Test equals with identical maps
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsIdentical() {
        Map map1 = new Map(new int[][]{{1, 2}, {3, 4}});
        Map map2 = new Map(new int[][]{{1, 2}, {3, 4}});
        assertEquals(map1, map2);
    }

    /**
     * Test equals with different values
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsDifferentValues() {
        Map map1 = new Map(new int[][]{{1, 2}, {3, 4}});
        Map map2 = new Map(new int[][]{{1, 2}, {3, 5}});
        assertNotEquals(map1, map2);
    }

    /**
     * Test equals with different dimensions
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsDifferentDimensions() {
        Map map1 = new Map(new int[][]{{1, 2}, {3, 4}});
        Map map2 = new Map(new int[][]{{1, 2, 3}, {4, 5, 6}});
        assertNotEquals(map1, map2);
    }

    /**
     * Test equals with null
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsNull() {
        Map map = new Map(3, 3, 0);
        assertNotEquals(map, null);
        assertFalse(map.equals(null));
    }

    /**
     * Test equals with same object
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsSameObject() {
        Map map = new Map(3, 3, 0);
        assertEquals(map, map);
    }

    // ==================== Fill Tests ====================

    /**
     * Test fill with simple connected component
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillSimple() {
        Map map = new Map(5, 5, 0);
        // Create a 3x3 block of 1s
        map.drawRect(new Index2D(1, 1), new Index2D(3, 3), 1);
        int filled = map.fill(new Index2D(2, 2), 2, false);
        assertEquals(9, filled);  // 3x3 = 9 pixels
        // Check that the block is filled with 2
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                assertEquals(2, map.getPixel(x, y));
            }
        }
    }

    /**
     * Test fill with disconnected regions
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillDisconnected() {
        Map map = new Map(5, 5, 0);
        map.setPixel(1, 1, 1);
        map.setPixel(3, 3, 1);
        int filled = map.fill(new Index2D(1, 1), 2, false);
        assertEquals(1, filled);  // Only one pixel filled
        assertEquals(2, map.getPixel(1, 1));
        assertEquals(1, map.getPixel(3, 3));  // Other region not filled
    }

    /**
     * Test fill with cyclic mode
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillCyclic() {
        Map map = new Map(3, 3, 1);
        // Break middle
        map.setPixel(0, 1, 0);
        map.setPixel(1, 1, 0);
        map.setPixel(2, 1, 0);
        int filled = map.fill(new Index2D(1, 0), 2, true);
        // In cyclic mode, should fill all connected 1s including wrapping
        assertEquals(6, filled);  // All pixels should be filled
    }

    /**
     * Test fill with same color (should return 0)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillSameColor() {
        Map map = new Map(5, 5, 1);
        int filled = map.fill(new Index2D(2, 2), 1, false);
        assertEquals(0, filled);  // No pixels filled
        assertEquals(1, map.getPixel(2, 2));  // Unchanged
    }

    /**
     * Test fill with L-shaped region
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillLShape() {
        Map map = new Map(5, 5, 0);
        // Draw L shape
        map.drawRect(new Index2D(1, 1), new Index2D(3, 1), 1);
        map.drawRect(new Index2D(1, 1), new Index2D(1, 3), 1);
        int filled = map.fill(new Index2D(1, 1), 2, false);
        assertEquals(5, filled);  // 5 pixels in L shape
    }

    // ==================== ShortestPath Tests ====================

    /**
     * Test shortestPath with direct path
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPathDirect() {
        Map map = new Map(5, 5, 0);
        Pixel2D start = new Index2D(0, 0);
        Pixel2D end = new Index2D(4, 4);
        Pixel2D[] path = map.shortestPath(start, end, -1, false);
        assertNotNull(path);
        assertEquals(start, path[0]);
        assertEquals(end, path[path.length - 1]);
    }

    /**
     * Test shortestPath with obstacles
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPathWithObstacles() {
        Map map = new Map(5, 5, 0);
        // Create obstacle wall
        map.drawLine(new Index2D(2, 0), new Index2D(2, 3), 1);
        Pixel2D start = new Index2D(0, 2);
        Pixel2D end = new Index2D(4, 2);
        Pixel2D[] path = map.shortestPath(start, end, 1, false);
        assertNotNull(path);
        // Path should go around obstacle
        assertTrue(path.length == 9);
        assertEquals(start, path[0]);
        assertEquals(end, path[path.length - 1]);
    }

    /**
     * Test shortestPath with no path (blocked)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPathBlocked() {
        Map map = new Map(5, 5, 1);  // All obstacles
        map.setPixel(0, 0, 0);  // Start
        map.setPixel(4, 4, 0);  // End
        Pixel2D start = new Index2D(0, 0);
        Pixel2D end = new Index2D(4, 4);
        Pixel2D[] path = map.shortestPath(start, end, 1, false);
        assertNull(path);  // No path exists
    }

    /**
     * Test shortestPath with same start and end
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPathSamePoint() {
        Map map = new Map(5, 5, 0);
        Pixel2D p = new Index2D(2, 2);
        Pixel2D[] path = map.shortestPath(p, p, -1, false);
        assertNotNull(path);
        assertEquals(1, path.length);
        assertEquals(p, path[0]);
    }

    /**
     * Test shortestPath with cyclic mode
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPathCyclic() {
        Map map = new Map(5, 5, 0);
        Pixel2D start = new Index2D(0, 2);
        Pixel2D end = new Index2D(4, 2);
        Pixel2D[] path = map.shortestPath(start, end, -1, true);
        assertNotNull(path);
        assertEquals(start, path[0]);
        assertEquals(end, path[path.length - 1]);
    }

    // ==================== AllDistance Tests ====================

    /**
     * Test allDistance with simple case
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAllDistanceSimple() {
        Map map = new Map(5, 5, 0);
        Pixel2D start = new Index2D(2, 2);
        Map2D distances = map.allDistance(start, -1, false);
        assertNotNull(distances);
        assertEquals(0, distances.getPixel(start));
        assertEquals(1, distances.getPixel(new Index2D(2, 1)));
        assertEquals(1, distances.getPixel(new Index2D(2, 3)));
        assertEquals(2, distances.getPixel(new Index2D(0, 2)));
    }

    /**
     * Test allDistance with obstacles
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAllDistanceWithObstacles() {
        Map map = new Map(5, 5, 0);
        map.setPixel(2, 1, 1);  // Obstacle
        map.setPixel(2, 3, 1);  // Obstacle
        Pixel2D start = new Index2D(2, 2);
        Map2D distances = map.allDistance(start, 1, false);
        assertNotNull(distances);
        assertEquals(0, distances.getPixel(start));
        assertEquals(-1, distances.getPixel(new Index2D(2, 1)));  // Obstacle
        assertEquals(-1, distances.getPixel(new Index2D(2, 3)));  // Obstacle
        assertEquals(1, distances.getPixel(new Index2D(1, 2)));   // Regular way
        assertEquals(2, distances.getPixel(new Index2D(4, 2)));   // Regular way
        assertEquals(4, distances.getPixel(new Index2D(2, 0)));   // Around obstacle
    }

    /**
     * Test allDistance with starting obstacle (should return all -1)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAllDistanceStartObstacle() {
        Map map = new Map(5, 5, 1);  // All obstacles
        Pixel2D start = new Index2D(2, 2);
        Map2D distances = map.allDistance(start, 1, false);
        assertNotNull(distances);
        // All distances should be -1
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertEquals(-1, distances.getPixel(x, y));
            }
        }
    }

    /**
     * Test allDistance with cyclic mode
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAllDistanceCyclic() {
        Map map = new Map(5, 5, 0);
        Pixel2D start = new Index2D(0, 0);
        Map2D distances = map.allDistance(start, -1, true);
        assertNotNull(distances);
        assertEquals(0, distances.getPixel(start));
        // In cyclic mode, should be able to reach opposite corner
        assertTrue(distances.getPixel(new Index2D(4, 4)) >= 0);
    }

    /**
     * Test allDistance with isolated region
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAllDistanceIsolated() {
        Map map = new Map(5, 5, 1);  // All obstacles
        map.setPixel(1, 1, 0);  // Small region
        map.setPixel(1, 2, 0);
        map.setPixel(3, 3, 0);  // Isolated pixel
        Pixel2D start = new Index2D(1, 1);
        Map2D distances = map.allDistance(start, 1, false);
        assertNotNull(distances);
        assertEquals(0, distances.getPixel(start));
        assertEquals(1, distances.getPixel(new Index2D(1, 2)));
        assertEquals(-1, distances.getPixel(new Index2D(3, 3)));  // Isolated
    }

    // ==================== Edge Case Tests ====================

    /**
     * Test operations on 1x1 map
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void test1x1Map() {
        Map map = new Map(1, 1, 5);
        assertEquals(1, map.getWidth());
        assertEquals(1, map.getHeight());
        assertEquals(5, map.getPixel(0, 0));
        map.setPixel(0, 0, 10);
        assertEquals(10, map.getPixel(0, 0));
        assertTrue(map.isInside(new Index2D(0, 0)));
        assertFalse(map.isInside(new Index2D(1, 0)));
    }

    /**
     * Test multiple operations in sequence
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testMultipleOperations() {
        Map map = new Map(10, 10, 0);
        // Draw circle
        map.drawCircle(new Index2D(5, 5), 3.0, 1);
        // Draw line
        map.drawLine(new Index2D(0, 0), new Index2D(9, 9), 2);
        // Draw rectangle
        map.drawRect(new Index2D(1, 1), new Index2D(3, 3), 3);
        // Fill
        map.fill(new Index2D(7, 7), 4, false);
        // Operations should complete without error
        assertNotNull(map.getMap());
    }
}
