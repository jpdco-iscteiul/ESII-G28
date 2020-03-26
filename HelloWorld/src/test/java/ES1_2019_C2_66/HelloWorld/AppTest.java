package ES1_2019_C2_66.HelloWorld;

import org.junit.Assert;
import org.junit.Before;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public AppTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
private App test;
	
	@Before
	public void setUp() throws Exception{
		test = new App();
	}
	
	
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
	
	
