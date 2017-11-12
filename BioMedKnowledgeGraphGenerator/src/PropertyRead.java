//import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyRead {
	String jsonFileDirectory;
	String xmlFileDirectory;
	String rdfFileDirectory;
	String ttlFileDirectory;
	String trigFileDirectory;
	
	public PropertyRead() {
		Properties prop = new Properties();
		InputStream input = null;
		
		//Potential TODO - Pre-specify data locations in the project and automatically retrieve the path name
		
		try {
			input = new FileInputStream("/home/sabbir/Programs/BioMedKnowledgeGraphGenerator/BioMedKnowledgeGraphGenerator/properties/config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and store it
			this.jsonFileDirectory = prop.getProperty("jsonFileDirectory");
			this.xmlFileDirectory = prop.getProperty("xmlFileDirectory");
			this.rdfFileDirectory = prop.getProperty("rdfFileDirectory");
			this.ttlFileDirectory = prop.getProperty("ttlFileDirectory");
			this.trigFileDirectory = prop.getProperty("trigFileDirectory");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*public static void main(String[] args) {
		PropertyRead mydirs = new PropertyRead();
		System.out.println(mydirs.inputFileDirectory);
		System.out.println(mydirs.outFileDirectory);
		System.out.println(mydirs.ontologyFileDirectory);
	}*/
}