import java.io.IOException;
import org.eclipse.jgit.api.errors.GitAPIException;

public class Main {
	
	public static void main(String[] args) throws IOException, IllegalStateException, GitAPIException{
      gitEngine gE= new gitEngine();
      gE.getData();
		
	}

}
