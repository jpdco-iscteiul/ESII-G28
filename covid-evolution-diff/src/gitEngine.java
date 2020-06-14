import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;


public class gitEngine {
	
	private Git git;
	private File localPath = new File("C:\\Users\\marga\\Desktop\\GIT");
	private Repository repository;
	private List<ObjectLoader> ficheiros = new ArrayList<ObjectLoader>(); //Lista para guardar a informa��o dos ficherios
	private List<ObjectLoader> UltimosDoisFicheiros = new ArrayList<ObjectLoader>();
	private List<Ref> tags = new ArrayList<Ref>(); // Lista para guardar as tags feitas no reposit�rio

	
	public gitEngine() {
		
	}

	/**
	 * Vai buscar os ficheiros ao reposit�rio do Git e cria um HTML com a informa��o distinta presente nestes fihceiros
	 */
	public void getData () { 
			
			
			  try {
				
					git =Git.open(localPath); //abre o git que esta no localpath (pasta GIT)
					git.pull();
					git.checkout();
			
				
				repository = git.getRepository(); // vai ao git buscar o reposit�rio
				
				
				tags = git.tagList().call(); //Vai igualar � lista de todas as tags  , vai busca-las e coloca-as aqui
				
				for (Ref tag : tags) {
					//percorre lista das tags
					
					ObjectId tagID = repository.resolve(tag.getName()); //Vai buscar o Id do commit � tag
					RevWalk revWalk = new RevWalk(repository);
					RevCommit commit = revWalk.parseCommit(tagID); 
					RevTree tree = commit.getTree();
					
						
					TreeWalk treeWalk = new TreeWalk(repository);
					treeWalk.addTree(tree);
					treeWalk.setRecursive(true);
					treeWalk.setFilter(PathFilter.create("covid19spreading.rdf")); //vai procurar por ficheiro covid19
					if(!treeWalk.next()) {
						throw new IllegalStateException("N�o encontrou o ficheiro do covid!");		//caso nao encontre				
					}
						
					ObjectId objectId = treeWalk.getObjectId(0); // vai buscar o id do objeto
					ObjectLoader loader = repository.open(objectId); //vai bbuscar o conteudo do objeto
					
					ficheiros.add(loader); //adiciona o conteudo � lista dos ficheiros
					
					}
				
					
			for (int ultimo = ficheiros.size()-1; ultimo!=0; ultimo--) {
				UltimosDoisFicheiros.add(ficheiros.get(ultimo));
			}
				UltimosDoisFicheiros.get(0).copyTo(System.out); //mostra o conte�do dos ficheiros na consola
				UltimosDoisFicheiros.get(1).copyTo(System.out);
				PrintStream DiferencasHTML = new PrintStream(new FileOutputStream("C:\\Users\\marga\\Desktop\\GIT\\DiferencasHTML.html")); //Cria o ficheiro html
				DiferencasHTML.println("ANTES - Regi�o Alentejo: Internamentos / Infe��es / Testes | Regi�o Algarve: Internamentos / Infe��es / Testes <br>"); //Acrescenta a linha de introdu��o n HTML
				UltimosDoisFicheiros.get(1).copyTo(DiferencasHTML); //vai buscar a informa��o do ficheiro mais antigo dentro dos ultimos dois
				DiferencasHTML.println("<br> DEPOIS - Regi�o Alentejo: Internamentos / Infe��es / Testes | Regi�o Algarve: Internamentos / Infe��es / Testes <br>");
				UltimosDoisFicheiros.get(0).copyTo(DiferencasHTML); //vai buscar a informa��o do ficheiro mais recente
				File htmlFile = new File("C:\\Users\\marga\\Desktop\\GIT\\DiferencasHTML.html"); //Vai buscar o ficheito
				Desktop.getDesktop().browse(htmlFile.toURI()); //abrir o ficheiro pelo browser default

				System.out.println("Os ficheiros s�o iguais?" + UltimosDoisFicheiros.get(0).equals(UltimosDoisFicheiros.get(1)));
			} catch (IOException | GitAPIException e) {
				getClone();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
	          
	}
	
	/**
	 * Faz clone do repositorio
	 */
	public void getClone () { 
		try {
			Git.cloneRepository()
			 .setURI("https://github.com/vbasto-iscte/ESII1920.git")
			 .setDirectory(new File("C:\\Users\\marga\\Desktop\\GIT"))  
			 .call();
			getData();
		} catch (GitAPIException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
