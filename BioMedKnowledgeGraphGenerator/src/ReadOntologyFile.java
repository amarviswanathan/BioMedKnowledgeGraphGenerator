import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;

import org.apache.jena.ontology.Individual;
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
		
		//Properties prop = new Properties();
		//InputStream input = new FileInputStream("properties/config.properties");
		//prop.load(input);
		
		//InputStream in = new FileInputStream(prop.getProperty("ontologyFileDirectory"));
		//System.out.println("Reading: " + prop.getProperty("ontologyFileDirectory"));
		
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		
		
		PropertyRead properties = new PropertyRead();
		InputStream in = new FileInputStream(properties.rdfFileDirectory);
		System.out.println("Reading: " + properties.rdfFileDirectory);
		
		model.read(in,null);
		Iterator<OntClass> classes = model.listClasses();
		int i = 0;
		System.out.println("Printing Classes: ");
		while(classes.hasNext()) {
			i++;
			OntClass ontclass = classes.next();
			
			System.out.println(ontclass.toString());
		}
		System.out.println("Printing Individuals: ");
		Iterator<Individual> instances = model.listIndividuals();
		while(instances.hasNext()) {
			Individual ontindv = instances.next();
			System.out.println(ontindv.toString());
		}
		System.out.println("Done");
	}
}
