package ex2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the Index2D class.
 * This class tests all methods and constructors of Index2D, including
 * edge cases and various scenarios.
 */
class Index2DTest {

    /**
     * Test the constructor Index2D(int x, int y) with positive coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithPositiveCoordinates() {
        Index2D index = new Index2D(5, 10);
        assertEquals(5, index.getX());
        assertEquals(10, index.getY());
    }

    /**
     * Test the constructor Index2D(int x, int y) with negative coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithNegativeCoordinates() {
        Index2D index = new Index2D(-3, -7);
        assertEquals(-3, index.getX());
        assertEquals(-7, index.getY());
    }

    /**
     * Test the constructor Index2D(int x, int y) with zero coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithZeroCoordinates() {
        Index2D index = new Index2D(0, 0);
        assertEquals(0, index.getX());
        assertEquals(0, index.getY());
    }

    /**
     * Test the constructor Index2D(int x, int y) with mixed positive and negative coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithMixedCoordinates() {
        Index2D index1 = new Index2D(-5, 10);
        assertEquals(-5, index1.getX());
        assertEquals(10, index1.getY());

        Index2D index2 = new Index2D(5, -10);
        assertEquals(5, index2.getX());
        assertEquals(-10, index2.getY());
    }

    /**
     * Test the constructor Index2D(Pixel2D other) with another Index2D object
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithPixel2D() {
        Index2D original = new Index2D(15, 25);
        Index2D copy = new Index2D(original);
        assertEquals(15, copy.getX());
        assertEquals(25, copy.getY());
        // Verify it's a copy, not the same reference
        assertNotSame(original, copy);
    }

    /**
     * Test the constructor Index2D(Pixel2D other) with ORIGIN constant
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testConstructorWithOrigin() {
        Index2D copy = new Index2D(Index2D.ORIGIN);
        assertEquals(0, copy.getX());
        assertEquals(0, copy.getY());
    }

    /**
     * Test the getX() method returns the correct X coordinate
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetX() {
        Index2D index1 = new Index2D(42, 100);
        assertEquals(42, index1.getX());

        Index2D index2 = new Index2D(-99, 50);
        assertEquals(-99, index2.getX());

        Index2D index3 = new Index2D(0, 0);
        assertEquals(0, index3.getX());
    }

    /**
     * Test the getY() method returns the correct Y coordinate
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testGetY() {
        Index2D index1 = new Index2D(100, 42);
        assertEquals(42, index1.getY());

        Index2D index2 = new Index2D(50, -99);
        assertEquals(-99, index2.getY());

        Index2D index3 = new Index2D(0, 0);
        assertEquals(0, index3.getY());
    }

    /**
     * Test distance2D() between two points with positive coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DWithPositiveCoordinates() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);
        // Distance should be 5 (3-4-5 right triangle)
        assertEquals(5.0, p1.distance2D(p2), 0.0001);
    }

    /**
     * Test distance2D() between origin and a point
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DFromOrigin() {
        Index2D p1 = Index2D.ORIGIN;
        Index2D p2 = new Index2D(0, 5);
        assertEquals(5.0, p1.distance2D(p2), 0.0001);

        Index2D p3 = new Index2D(5, 0);
        assertEquals(5.0, p1.distance2D(p3), 0.0001);
    }

    /**
     * Test distance2D() between two identical points (should be 0)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DBetweenIdenticalPoints() {
        Index2D p1 = new Index2D(10, 20);
        Index2D p2 = new Index2D(10, 20);
        assertEquals(0.0, p1.distance2D(p2), 0.0001);
    }

    /**
     * Test distance2D() with negative coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DWithNegativeCoordinates() {
        Index2D p1 = new Index2D(-3, -4);
        Index2D p2 = new Index2D(0, 0);
        assertEquals(5.0, p1.distance2D(p2), 0.0001);
    }

    /**
     * Test distance2D() symmetry - distance from A to B should equal distance from B to A
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DSymmetry() {
        Index2D p1 = new Index2D(1, 2);
        Index2D p2 = new Index2D(4, 6);
        double dist1 = p1.distance2D(p2);
        double dist2 = p2.distance2D(p1);
        assertEquals(dist1, dist2, 0.0001);
    }

    /**
     * Test distance2D() with large coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DWithLargeCoordinates() {
        Index2D p1 = new Index2D(100, 200);
        Index2D p2 = new Index2D(300, 400);
        // Distance should be sqrt((300-100)^2 + (400-200)^2) = sqrt(200^2 + 200^2) = sqrt(80000) ≈ 282.84
        double expected = Math.sqrt(Math.pow(200, 2) + Math.pow(200, 2));
        assertEquals(expected, p1.distance2D(p2), 0.0001);
    }

    /**
     * Test toString() method returns correct format
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testToString() {
        Index2D index1 = new Index2D(5, 10);
        assertEquals("(5,10)", index1.toString());

        Index2D index2 = new Index2D(-3, 7);
        assertEquals("(-3,7)", index2.toString());

        Index2D index3 = new Index2D(0, 0);
        assertEquals("(0,0)", index3.toString());

        Index2D index4 = new Index2D(100, -200);
        assertEquals("(100,-200)", index4.toString());
    }

    /**
     * Test equals() with two identical Index2D objects
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithIdenticalObjects() {
        Index2D p1 = new Index2D(5, 10);
        Index2D p2 = new Index2D(5, 10);
        assertEquals(p1, p2);
    }

    /**
     * Test equals() with same object reference
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithSameReference() {
        Index2D p1 = new Index2D(5, 10);
        assertEquals(p1, p1);
    }

    /**
     * Test equals() with ORIGIN constant
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithOrigin() {
        Index2D p1 = new Index2D(0, 0);
        assertEquals(Index2D.ORIGIN, p1);
        assertEquals(p1, Index2D.ORIGIN);
    }

    /**
     * Test equals() with null (should return false)
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithNull() {
        Index2D p1 = new Index2D(5, 10);
        assertNotEquals(p1, null);
        assertFalse(p1.equals(null));
    }

    /**
     * Test equals() with different X coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithDifferentX() {
        Index2D p1 = new Index2D(5, 10);
        Index2D p2 = new Index2D(6, 10);
        assertNotEquals(p1, p2);
    }

    /**
     * Test equals() with different Y coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithDifferentY() {
        Index2D p1 = new Index2D(5, 10);
        Index2D p2 = new Index2D(5, 11);
        assertNotEquals(p1, p2);
    }

    /**
     * Test equals() with both different X and Y coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithDifferentCoordinates() {
        Index2D p1 = new Index2D(5, 10);
        Index2D p2 = new Index2D(6, 11);
        assertNotEquals(p1, p2);
    }

    /**
     * Test equals() with negative coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEqualsWithNegativeCoordinates() {
        Index2D p1 = new Index2D(-5, -10);
        Index2D p2 = new Index2D(-5, -10);
        assertEquals(p1, p2);

        Index2D p3 = new Index2D(-5, -10);
        Index2D p4 = new Index2D(-5, -11);
        assertNotEquals(p3, p4);
    }

    /**
     * Test ORIGIN constant has correct coordinates
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testOriginConstant() {
        assertEquals(0, Index2D.ORIGIN.getX());
        assertEquals(0, Index2D.ORIGIN.getY());
    }

    /**
     * Test ORIGIN constant is consistent
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testOriginConstantConsistency() {
        Index2D p1 = Index2D.ORIGIN;
        Index2D p2 = Index2D.ORIGIN;
        assertSame(p1, p2); // Should be the same reference
        assertEquals(p1, p2);
    }

    /**
     * Test creating multiple Index2D objects and verifying immutability
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testImmutability() {
        Index2D p1 = new Index2D(5, 10);
        Index2D p2 = new Index2D(p1);
        // Both should have same values
        assertEquals(p1, p2);
        // But modifying one shouldn't affect the other (if fields were mutable)
        // Since fields are final, this test verifies the object maintains its state
        assertEquals(5, p1.getX());
        assertEquals(10, p1.getY());
        assertEquals(5, p2.getX());
        assertEquals(10, p2.getY());
    }

    /**
     * Test distance2D() with points at same X but different Y
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DSameX() {
        Index2D p1 = new Index2D(5, 0);
        Index2D p2 = new Index2D(5, 10);
        assertEquals(10.0, p1.distance2D(p2), 0.0001);
    }

    /**
     * Test distance2D() with points at same Y but different X
     */
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testDistance2DSameY() {
        Index2D p1 = new Index2D(0, 5);
        Index2D p2 = new Index2D(10, 5);
        assertEquals(10.0, p1.distance2D(p2), 0.0001);
    }
}