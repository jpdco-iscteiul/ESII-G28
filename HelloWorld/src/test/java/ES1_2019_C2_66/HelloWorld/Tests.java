package ES1_2019_C2_66.HelloWorld;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {

	private App test;
	
	@Before
	public void setUp() throws Exception{
		test = new App();
	}
	

	@Test
	public void test() {
		Assert.assertFalse(test.containString("Filarmonica", "pastel"));
		Assert.assertTrue(test.containString("Filarmonica", "monica"));
		
		Assert.assertEquals(0, test.comparatorInt(3, 3));
		Assert.assertEquals(1, test.comparatorInt(4, 3));
		Assert.assertEquals(-1, test.comparatorInt(3, 4));
		
		double result =28.27;
		Assert.assertEquals(result, test.circleArea(3));
	}

}
