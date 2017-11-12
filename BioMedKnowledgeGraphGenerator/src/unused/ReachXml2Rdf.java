package unused;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import com.sun.xml.internal.ws.util.xml.NodeListIterator;

/**
 * 
 */

/**
 * @author Amar Viswanathan
 *
 */
public class ReachXml2Rdf {

	//TODO move these to a property file
	//	public static final String READ_LOCATION = "/home/amar/Data/xml/";
	//	public static final String WRITE_LOCATION = "/home/amar/Data/rdf/";
	//public static final String READ_LOCATION = "/home/sabbir/Programs/xml/";
	//public static final String WRITE_LOCATION = "home/sabbir/Programs/rdf/";
	
	static PropertyRead properties = new PropertyRead();
	public static final String READ_LOCATION = properties.xmlFileDirectory;
	public static final String WRITE_LOCATION = properties.rdfFileDirectory;
	
	static Set<String> types = new HashSet<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		Files.newDirectoryStream(Paths.get(READ_LOCATION),
				path -> path.toString().contains(".entities"))
		.forEach(s -> {
			try {
				System.out.println(s.toString());
				readXml(s.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		for(String type : types) {
			System.out.println(type);
		}
	}

	public static void readXml(String fileName) throws Exception {

		File xmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();

		Document doc = builder.parse(xmlFile);

		NodeList nodeList = doc.getElementsByTagName("location");

		for(int i = 0; i<nodeList.getLength(); i++) {

			Node node = nodeList.item(i);
			types.add(node.getTextContent());

		}

	}

}
