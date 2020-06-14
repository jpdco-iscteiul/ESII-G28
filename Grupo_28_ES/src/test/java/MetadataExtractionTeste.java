import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;

/**
 * 
 */

/**
 * @author freit
 *
 */
public class MetadataExtractionTeste {

	HTMLTableBuilder html;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		html = new HTMLTableBuilder("Projeto ESII", true, 2, 4);
	}

	/**
	 * Test method for {@link MetadataExtraction#main(java.lang.String[])}.
	 * @throws TransformationException 
	 * @throws IOException 
	 * @throws AnalysisException 
	 */
	@Test
	public void testMain() throws AnalysisException, IOException, TransformationException {
		String [] args=null;
		MetadataExtraction.main(args);
			}

	
}
