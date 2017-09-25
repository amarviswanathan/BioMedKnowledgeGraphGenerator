import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

//import PropertyRead;

/**
 * @author Amar Viswanathan
 *
 */
public class ReachJson2Xml {

	//obtain read and write location from config.properties
	static PropertyRead properties = new PropertyRead();
	public static final String READ_LOCATION = properties.jsonFileDirectory;
	public static final String WRITE_LOCATION = properties.xmlFileDirectory;
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */	
	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		
		Files.newDirectoryStream(Paths.get(READ_LOCATION),
				path -> path.toString().endsWith(".json"))
		.forEach(s -> {
			try {
				writeXML(s.toString());
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("Done converting to XML");
		
	}
	/**
	 * @param jsonFileName "The name of the input json file name. The same name is used for conversion to xml"
	 * @throws IOException "IOException is thrown because of the file write operation for this function."
	 * @throws JSONException 
	 */

	static void writeXML(String jsonFileName) throws IOException, JSONException {

		//TODO should be a better method to do this
		int lastIndex = jsonFileName.lastIndexOf(".");
		//int firstIndex = jsonFileName.lastIndexOf("/");
		int firstIndex = jsonFileName.lastIndexOf("\\");
		String baseFileName = jsonFileName.substring(firstIndex+1,lastIndex);
		System.out.println(baseFileName);
		String content = new String(Files.readAllBytes(Paths.get(jsonFileName)));
		JSONObject o = new JSONObject(content);
		System.out.println("Writing : " + Paths.get(WRITE_LOCATION + baseFileName + ".xml"));
		System.out.println(READ_LOCATION);
		System.out.println(WRITE_LOCATION);
		Files.write(Paths.get(WRITE_LOCATION + baseFileName + ".xml"), XML.toString(o,"root").getBytes(),StandardOpenOption.CREATE);

	}

}
