package ES1_2019_C2_66.HelloWorld;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    
    public boolean containString(String word, String keyWord) {
    	return word.contains(keyWord);
    }
    
    public int comparatorInt(int a, int b) {
    	if(a>b) {
    		return 1;
    	}
    	else {
    		if(a<b) {
    			return -1;
    		}
    		else {
    			return 0;
    		}
    	}
    }
    
    public double circleArea(double r) {
    	return Math.PI*Math.pow(r, 2);
    }
    
    
}
