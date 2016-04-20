package lab01;

import org.junit.Assert;
import org.junit.Test;

public class TriangleTest {

/*	AreaCalculation triangle = new AreaCalculation();
	AreaCalculation notATriangle = new AreaCalculation();
	AreaCalculation zeroNoTriangle = new AreaCalculation();
	AreaCalculation triangle2 = new AreaCalculation();*/

	@Test
	public void testIsTriangle(){
		Assert.assertTrue(AreaCalculation.isTriangle(12.9,8.3,5.1));
		Assert.assertFalse(AreaCalculation.isTriangle(17.2,5.4,3.8));
	}

	@Test(expected = NotATriangleException.class)
	public void calculationFailure() throws Exception{
		AreaCalculation.calculateTriangle(0,1.5,9.8);
		AreaCalculation.calculateTriangle(17.2,5.4,3.8);
	}

	@Test
	public void testAreaCalculation() throws Exception{
		Assert.assertEquals(6, AreaCalculation.calculateTriangle(3,4,5),0);
	}
}
