package ES2.Complemento_4;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	private static App app;
    public AppTest( String testName )
    {
        super( testName );
        app = new App();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
    	File f = new File("repositorio/cloneES");
		app.deleteFolder(f);
		app=new App();
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
