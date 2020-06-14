package ES2.Complemento_4;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

/**
 * Hello world!
 *
 */
public class App 
{
	private Git git;
	private final String TEMP="html/template.html";
//	private final String HTML="html/covid_4.html";
	private final String REP="repositorio/cloneES";
	private final String URL="https://github.com/vbasto-iscte/ESII1920.git";
	private String link ="http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf";

	public App() {
		try {
			connectWithGit();
//			String template = getTemplateString();
			ArrayList<String> list = getDataFromRepository();
			generateHTML(list);
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

//	private void generateHTML(ArrayList<String> list, String template) {
//		String body = "<h1>Evolução do Covid-19</h1> <table border=\"2\" width=\"900\" align=\"center\">"
//				+ "<tr align=\"center\" bgcolor=\"lightblue\"" + 
//				"> <td> File Timestamp </td> <td> File Name </td> <td> File Tag </td> <td> Tag Description </td> <td> Spread Visualization Link </td> </tr>";
//		for (String string : list) {
//			String[] data = string.split("£");
//			
//			String row ="<tr> <td>"+data[2]+"</td> <td>"+data[3]+"</td> <td>"+data[0]+"</td> <td>"+data[1]+"</td> <td> <a href="+link.replace("master", data[0])+">Dados relacionados</a> </td> </tr>";
//			body+=row;
//		}
//		body+="</table>";
//		
//		try
//	    {
//			String finalhtml = template.replace("$body", body);
//	        PrintWriter printWriter = new PrintWriter (HTML);
//	        printWriter.print(finalhtml);
//	        printWriter.close ();        
//	        System.out.println(finalhtml);
//	    }
//	    catch(Exception exception)
//	    {
//	        exception.printStackTrace();
//	    }
//		File html = new File(HTML);
//		try {
//			Desktop.getDesktop().browse(html.toURI());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	private void generateHTML(ArrayList<String> list) {
		String template="Content-type: text/html\r\n" + 
				"<html>\r\n" + 
				"	<head>\r\n" + 
				"		<title> Complemento 4 </title>\r\n" + 
				"	</head>\r\n" +
				"	<body>\r\n" + 
				"		$body\r\n" + 
				"	</body>\r\n" + 
				"</html>";
		String body = "<h1>Evolução do Covid-19</h1> "
				+ "<table border=\"2\" width=\"900\" align=\"center\">"
				+ "<tr align=\"center\" bgcolor=\"lightblue\"> "
				+ "<td> File Timestamp </td> <td> File Name </td> <td> File Tag </td> <td> Tag Description </td> <td> Spread Visualization Link </td> "
				+ "</tr>";
		for (String string : list) {
			String[] data = string.split("£");
			
			String row ="<tr> <td>"+data[2]+"</td> <td>"+data[3]+"</td> <td>"+data[0]+"</td> <td>"+data[1]+"</td> <td> <a href="+link.replace("master", data[0])+">Dados relacionados</a> </td> </tr>";
			body+=row;
		}
		body+="</table>";
		
		try
	    {
			String finalhtml = template.replace("$body", body);
//	        PrintWriter printWriter = new PrintWriter (HTML);
//	        printWriter.print(finalhtml);
//	        printWriter.close ();        
	        System.out.println(finalhtml);
	    }
	    catch(Exception exception)
	    {
	        exception.printStackTrace();
	    }
//		File html = new File(HTML);
//		try {
//			Desktop.getDesktop().browse(html.toURI());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	//obter repositorio e tags para obter as informaçoes
	private ArrayList<String> getDataFromRepository() throws MissingObjectException, IncorrectObjectTypeException, IOException {
		Repository repository = git.getRepository();

		Map<String, Ref> tags = repository.getTags();

		List<Ref> list = new ArrayList<Ref>();
		Iterator it = tags.entrySet().iterator();
		ArrayList<String> rows = new ArrayList<String>();
		int aux = 0;

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			list.add((Ref) pair.getValue());
			String data = pair.getKey().toString();
			try (RevWalk revWalk = new RevWalk(repository)) {
				RevCommit commit = revWalk.parseCommit(((Ref)pair.getValue()).getObjectId());
				data+="£"+commit.getFullMessage();
				RevTree tree = commit.getTree();
				PersonIdent authorIdent = commit.getAuthorIdent();
				Date authorDate = authorIdent.getWhen();
				data+="£"+authorDate;
				try(TreeWalk treeWalk = new TreeWalk(repository)){
					treeWalk.addTree(tree);
					treeWalk.setRecursive(true);
					treeWalk.setFilter(PathFilter.create("covid19spreading.rdf"));

					if (!treeWalk.next()) {
						throw new IllegalStateException("nao existe");
					}
					data+="£"+treeWalk.getPathString();
					ObjectId objectId = treeWalk.getObjectId(0);
					ObjectLoader loader = repository.open(objectId);
				}
				revWalk.dispose();
			}
			rows.add(data);
			aux++;
		}
		return rows;
	}

	//obtem a estrutura base de html (template.html)
	private String getTemplateString() {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(TEMP));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		
		String html = contentBuilder.toString();
		return html;
	}

	//Clonar repositorio git/pull caso já exista o clone
	void connectWithGit() throws IOException, InvalidRemoteException, TransportException, GitAPIException {		
		if(new File(REP).exists()) {
			git =Git.open(new File(REP));
			git.pull();
		}
		else {
			git = Git.cloneRepository()
					.setURI(URL)
					.setDirectory(new File(REP))
					.setCloneAllBranches(true)
					.call();
		}
	}

	public static void main( String[] args ) throws InvalidRemoteException, TransportException, 
	GitAPIException, IOException {
		App app = new App();
	}


	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files) {
				if(f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		if (folder.delete()) { 
			System.out.println("Deleted the file: " + folder.getName());
		} else {
			System.out.println("Failed to delete the file.");
		} 
	}
}
