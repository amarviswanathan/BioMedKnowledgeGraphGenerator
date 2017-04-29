import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 */

/**
 * @author sabbir
 *
 */
public class Utility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		getUniqueFileNames("/home/sabbir/Programs/xml/fries-PM1s/");
	}
	
	public static Set<String> getUniqueFileNames(String folderLocation){
		
		File[] listOfFiles = new File(folderLocation).listFiles();
		Set<String> uniqueFileNames = new HashSet<String>();
		
		for(File file : listOfFiles){
			String name = file.getName();
			int index = name.indexOf('.');
			uniqueFileNames.add(name.substring(0, index));
			
		}
		
		System.out.println("#Files = " + uniqueFileNames.size());
		
		return uniqueFileNames;
	}

}
