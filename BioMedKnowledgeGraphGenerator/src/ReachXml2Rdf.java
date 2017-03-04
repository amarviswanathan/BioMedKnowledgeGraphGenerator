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
	public static final String READ_LOCATION = "/home/sabbir/Programs/xml/";
	public static final String WRITE_LOCATION = "home/sabbir/Programs/rdf/";
	static Set<String> types = new HashSet<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Files.newDirectoryStream(Paths.get(READ_LOCATION),
				path -> path.toString().contains(".sentences"))
		.forEach(s -> {
			try {
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
		
		NodeList nodeList = doc.getElementsByTagName("argument-type");
		
		for(int i = 0; i<nodeList.getLength(); i++) {
			
			Node node = nodeList.item(i);
//			System.out.println(node.getTextContent());
			types.add(node.getTextContent());
			
		}
		
	}

}
