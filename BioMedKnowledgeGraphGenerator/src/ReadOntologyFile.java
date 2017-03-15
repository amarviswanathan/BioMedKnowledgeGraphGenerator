import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
/**
 * 
 */

/**
 * @author Amar Viswanathan
 *
 */
public class ReadOntologyFile {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Properties prop = new Properties();
		InputStream input = new FileInputStream("properties/config.properties");
		
		prop.load(input);
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		InputStream in = new FileInputStream(prop.getProperty("ontologyFileDirectory"));
		model.read(in,null);
		
		Iterator<OntClass> it = model.listClasses();
		int i = 0;
		while(it.hasNext()) {
			i++;
			OntClass ontclass = it.next();
			
			System.out.println(ontclass.toString());
		}

	}

}
