package lab01;

import org.junit.Assert;
import org.junit.Test;

public class TriangleTest {

	AreaCalculation triangle = new AreaCalculation(12.9,8.3,5.1);
	AreaCalculation notATriangle = new AreaCalculation(17.2,5.4,3.8);
	AreaCalculation zeroNoTriangle = new AreaCalculation(0,1.5,9.8);
	AreaCalculation triangle2 = new AreaCalculation(3,4,5);

	@Test
	public void testIsTriangle(){
		Assert.assertTrue(triangle.isTriangle(triangle.getA(),triangle.getB(),triangle.getC()));
		Assert.assertFalse(notATriangle.isTriangle(notATriangle.getA(), notATriangle.getB(), notATriangle.getC()));
	}

	@Test(expected = NotATriangleException.class)
	public void calculationFailure() throws Exception{
		zeroNoTriangle.calculateTriangle(zeroNoTriangle.getA(),zeroNoTriangle.getB(),zeroNoTriangle.getC());
		notATriangle.calculateTriangle(notATriangle.getA(), notATriangle.getB(), notATriangle.getC());
	}

	@Test
	public void testAreaCalculation() throws Exception{
		Assert.assertEquals(6, triangle2.calculateTriangle(triangle2.getA(), triangle2.getB(), triangle2.getC()),0);
	}
}
