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
	String mentionmapFileLocation;
	String propertiesFileDirectory;
	
	public PropertyRead() {
		Properties prop = new Properties();
		InputStream input = null;
		
		//Potential TODO - Pre-specify data locations in the project and automatically retrieve the path name
		
		try {
			//Load the properties file
			input = new FileInputStream("C:/Users/grossi2/Documents/GitHub/BioMedKnowledgeGraphGenerator/BioMedKnowledgeGraphGenerator/properties/config.properties");
			//input = new FileInputStream("/home/sabbir/Programs/BioMedKnowledgeGraphGenerator/BioMedKnowledgeGraphGenerator/properties/config.properties");
			prop.load(input);

			//Get and store each file directory/location
			this.jsonFileDirectory = prop.getProperty("jsonFileDirectory");
			this.xmlFileDirectory = prop.getProperty("xmlFileDirectory");
			this.rdfFileDirectory = prop.getProperty("rdfFileDirectory");
			this.ttlFileDirectory = prop.getProperty("ttlFileDirectory");
			this.trigFileDirectory = prop.getProperty("trigFileDirectory");
			this.mentionmapFileLocation = prop.getProperty("mentionmapFileLocation");
			this.propertiesFileDirectory = prop.getProperty("propertiesFileDirectory");

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
}