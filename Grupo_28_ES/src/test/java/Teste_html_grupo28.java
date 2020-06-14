import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author freit
 *
 */

public class Teste_html_grupo28 {

	HTMLTableBuilder html;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		html = new HTMLTableBuilder(null, true, 2,4);
	}
	
	/**
	 * 
	 */
	@Test
	public void testHTMLTableBuilder() {
		html = new HTMLTableBuilder(null, true, 2,4);
	}

	@Test
	public void testHTMLTableBuilder2() throws Exception {
		html = new HTMLTableBuilder("something", true, 2, 4);
	}

	@Test
	public void testAddTableHeader() {
		html.addTableHeader("Journal", "Article", "Year", "Author");
	}

	@Test
	public void testAddTableHeader1() {
		html.addTableHeader("Journal", "Article", "Year", "Authors", "EXTRA");
	}

	
	@Test
	public void testAddRowValues() {
		html.addRowValues("Nome-Jornal","Nome_artigo", "Ano_publicação", "Nome_autores");
	}
	
	@Test
	public void testAddRowValues1() {
		html.addRowValues();
		
	}

	@Test
	public void testBuild() {
		html.build();
		
	}

	@Test
	public void testCreate_file() {
		html.create_file();
	}

}
