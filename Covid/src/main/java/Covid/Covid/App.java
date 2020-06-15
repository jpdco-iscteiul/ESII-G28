package Covid.Covid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

public class App {

	public Git git;
	static String REMOTE_URL="https://github.com/vbasto-iscte/ESII1920.git";
	static File path = new File("fileES");

	public void init() throws InvalidRemoteException, TransportException, GitAPIException, IOException, SAXException, ParserConfigurationException {
		if (path.exists()) {
			git=Git.open(path);
			git.pull().call();
		}
		else {
			git = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(path).call();
		}

	}



	void deleteDirectory(File file) throws IOException {
		if (file.isDirectory()) {
			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					deleteDirectory(entry);
				}
			}
		}
		if (!file.delete()) {
			throw new IOException("Failed to delete " + file);
		}
	}


	public static void main( String[] args ) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		try {	

			System.out.println(cgi_lib.Header());
			Hashtable form_data = cgi_lib.ReadParse(System.in);
			//System.out.println(cgi_lib.Variables(form_data));


			App a = new App();
			a.init();
			File inputFile = new File("fileES/covid19spreading.rdf");    	      	  
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();         


			String regiao = ((String) form_data.get("regiao"));
			String info = ((String) form_data.get("info"));



			//VALOR RAPIDO
			String query = "/RDF/NamedIndividual/@*";
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile(query);
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				//System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
			}	



			query = "//*[contains(@about,'"+ regiao +"')]/"+ info +"/text()";  
			expr = xpath.compile(query);     
			String valor1 = (String) expr.evaluate(doc, XPathConstants.STRING);
			System.out.println("Numero de " + info + " na regiao " + regiao + " sao: " + valor1);

			System.out.println("<p>");
			System.out.println("<p>");

			// QUERY COMPARADOR
			String regiao1 = ((String) form_data.get("regiao1"));
			String info1 = ((String) form_data.get("info1"));
			String regiao2 = ((String) form_data.get("regiao2"));




			query = "//*[contains(@about,'"+ regiao1 +"')]/"+ info1 +"/text()";  
			expr = xpath.compile(query);     
			String valor2 = (String) expr.evaluate(doc, XPathConstants.STRING);

			query = "//*[contains(@about,'"+ regiao2 +"')]/"+ info1 +"/text()";  
			expr = xpath.compile(query);     
			String valor3 = (String) expr.evaluate(doc, XPathConstants.STRING);

			if ((Integer.valueOf(valor3)) == (Integer.valueOf(valor2)) ) {
				System.out.println("Numero de " + info1 + " na regiao " + regiao1 + " e na regiao " + regiao2 +" sao iguais");
			}

			else if ((Integer.valueOf(valor3)) > (Integer.valueOf(valor2))) {
				System.out.println("Numero de " + info1 + " na regiao " + regiao2 + " e maior que e na regiao " + regiao1);
			}	
			else if ((Integer.valueOf(valor3)) < (Integer.valueOf(valor2))) {
				System.out.println("Numero de " + info1 + " na regiao " + regiao2 + " e menor que e na regiao " + regiao1);
			}


			System.out.println("<p>");
			System.out.println("<p>");

			//QUERY SOMA
			String regiao3 = ((String) form_data.get("regiao3"));
			String info2 = ((String) form_data.get("info2"));
			String regiao4 = ((String) form_data.get("regiao4")); 

			query = "//*[contains(@about,'"+ regiao3 +"')]/"+ info2 +"/text()";  
			expr = xpath.compile(query);     
			String valor4 = (String) expr.evaluate(doc, XPathConstants.STRING);

			query = "//*[contains(@about,'"+ regiao4 +"')]/"+ info2 +"/text()";  
			expr = xpath.compile(query);     
			String valor5 = (String) expr.evaluate(doc, XPathConstants.STRING);

			int soma = (Integer.valueOf(valor4)) + (Integer.valueOf(valor5));
			System.out.println("A soma de " + info2 + " na regiao " + regiao3 + " e " + regiao4 + " e de " + soma);



			System.out.println(cgi_lib.HtmlBot());

			a.git.close();

		} catch (Exception e) { 
			e.printStackTrace(); 
			System.out.println("Nao existem valores registados para as regioes pedidas");
		}

	}
}
