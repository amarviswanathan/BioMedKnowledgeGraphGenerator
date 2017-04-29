import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * @author Amar Viswanathan
 *
 */
public class ReachJson2Xml {

	//TODO move these to a property file
	public static final String WRITE_LOCATION = "/home/sabbir/Programs/xml/fries-PM1s/";
	public static final String READ_LOCATION = "/home/sabbir/Programs/json/fries-PM1s/";

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
		int firstIndex = jsonFileName.lastIndexOf("/");
		String baseFileName = jsonFileName.substring(firstIndex+1,lastIndex);
		System.out.println(baseFileName);
		String content = new String(Files.readAllBytes(Paths.get(jsonFileName)));
		JSONObject o = new JSONObject(content);
		System.out.println("Writing : " + Paths.get(WRITE_LOCATION + baseFileName + ".xml"));
		Files.write(Paths.get(WRITE_LOCATION + baseFileName + ".xml"), XML.toString(o,"root").getBytes(),StandardOpenOption.CREATE_NEW);

	}

}
