package lab02;

import org.junit.Test;

import static org.junit.Assert.*;

public class AreaCalculationTest {

    private AreaCalculation calculation = new AreaCalculation();

    @Test
    public void testCalculateTriangle() throws Exception {
        assertEquals("Triangle is viable and all sides are greater than zero", 6, calculation.calculateTriangle(3,4,5),0.001);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testOneSideGreaterThanZero() throws Exception {
        calculation.calculateTriangle(3,0,5);
    }

    @Test
    public void testCalculateTriangleAllUnordered() throws Exception {
        assertEquals("Triangle is viable and all sides are greater than zero. b is greatest side. a > c", 6, calculation.calculateTriangle(4,5,3),0.001);
    }

    @Test
    public void testCalculateTriangleUnordered() throws Exception {
        assertEquals("Triangle is viable and all sides are greater than zero. a is greatest side. b smallest.", 6, calculation.calculateTriangle(5,3,4),0.001);
    }
}