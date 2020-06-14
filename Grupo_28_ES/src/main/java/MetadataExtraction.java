import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Desktop;
import java.io.File;
import org.jdom.Element;
import org.w3c.dom.Node;

import oracle.net.aso.a;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.bibref.CRFBibReferenceParser;
import pl.edu.icm.cermine.bibref.model.BibEntry;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.metadata.affiliation.CRFAffiliationParser;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.metadata.model.DocumentDate;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;
import pl.edu.icm.cermine.metadata.transformers.NLMToMetadataConverter;
import pl.edu.icm.cermine.tools.XMLTools;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;

import java.util.ArrayList;
import pl.edu.icm.cermine.content.cleaning.ContentCleaner;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;
import pl.edu.icm.cermine.tools.timeout.TimeoutRegister;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MetadataExtraction {
	
	/**
	 * @param args
	 * @throws AnalysisException
	 * @throws IOException
	 * @throws TransformationException
	 */
	
	
	public static void main(String[] args) throws AnalysisException, IOException, TransformationException {
		//System.out.println("Content-type: text/html\n");
		//System.out.println("<html>\n<head>\n<title>Projeto ESII</title>\n</head>\n<body>\n<h2>Projeto ESII</h2>");
		//System.out.println("<table border=\"1px\" height=\"auto\" length=\"auto\" align=\"left\"bgcolor=\"white\">\n<tr bgcolor=\"lightyellow\">\n<th>Journal</th>\n<th>Article</th>\n<th>Year</th>\n<th>Author</th>\n</tr>\n<tr>");
		File [] files = new File ("./pdf").listFiles();
		HTMLTableBuilder html = new HTMLTableBuilder ("Projeto ESII", true, 2, 4);
		html.addTableHeader("Journal", "Article", "Year", "Authors");
		
		for (File a : files) {
			InputStream inputStream = new FileInputStream(a);

			ContentExtractor extractor = new ContentExtractor();
			extractor.setPDF(inputStream);
			DocumentMetadata doc = extractor.getMetadata();
			DocumentDate date = doc.getDate(DateType.PUBLISHED);
			String year = date.getYear();
			List<DocumentAuthor> authors = doc.getAuthors();
			String string = new String("");
			
			for(DocumentAuthor autor : authors) {
				string += autor.getName() + ";";
			}
			
			String link = "\"" + a + "\"";
			String final_link = "<a href=" + link + ">" + doc.getTitle() + "</a>";
		
			html.addRowValues(doc.getJournal(), final_link, year, string);
			//System.out.println("<td>"+doc.getJournal()+"</td>\n<td>"+final_link+"</td>\n<td>"+year+"</td>\n<td>"+string+"</td>\n</tr>\n<tr>");
		}
		//System.out.println("</tr>\n</table>\n</body>\n</html>");
		
		File file = html.create_file();
		Desktop.getDesktop().browse(file.toURI());
		
	}
	
	
	
	
	
}
