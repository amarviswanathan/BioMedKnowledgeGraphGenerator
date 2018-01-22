import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.print.attribute.HashAttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.XML;
import org.w3c.dom.*;

import com.sun.xml.internal.ws.util.xml.NodeListIterator;

import bean.ArgumentType;
import bean.Arguments;
import bean.Context;
import bean.EntityMention;
import bean.EventMention;
import bean.Facets;
import bean.FrameType;
import bean.ObjectMeta;
import bean.ObjectType;
import bean.Passage;
import bean.Position;
import bean.Sentence;
import bean.XRefs;

/**
 * 
 */

/**
 * @author Sabbir Rashid
 *
 */
public class ReachXml2Trig extends ReachParseXml {
	
	//obtain read, write, and mention map location from config.properties
	static PropertyRead mydirs = new PropertyRead();
	public static final String READ_LOCATION = mydirs.xmlFileDirectory;
	public static final String WRITE_LOCATION = mydirs.trigFileDirectory;
	public static final String MENTIONMAP_LOCATION = mydirs.mentionmapFileLocation;
	
	
	static Set<EntityMention> entity_mentions = new HashSet<EntityMention>();
	static Set<EventMention> event_mentions = new HashSet<EventMention>();
	static Set<Context> contexts = new HashSet<Context>();
	static Set<Sentence> sentences = new HashSet<Sentence>();
	static Set<Passage> passages = new HashSet<Passage>();
	/**
	 * @param args
	 */
	
	public static void main(String[] args) throws Exception{
		
		// Read the values from Mention Map csv file and save to HashMap
		HashMap<String,String> mentionTypeMap = extractMentionMap();
		
	    // TODO Auto-generated method stub
		ReachXmlContentClass reachxmlcontent = ReachParseXml.parseReachXml(READ_LOCATION);
		entity_mentions = reachxmlcontent.entity_mentions;
		event_mentions = reachxmlcontent.event_mentions;
		contexts = reachxmlcontent.contexts;
		sentences = reachxmlcontent.sentences;
		passages = reachxmlcontent.passages;
		
		//System.exit(0);
		
		System.out.println("Printing Entity Mention TRIG to file");
		String kb="kgcs-kb:";
		String kgcs="http://tw.rpi.edu/web/Courses/Ontologies/2017/KGCS/KGCS/";
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    String date;
		String prefixes="@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n" +
				"@prefix sio:     <http://semanticscience.org/resource/> .\n" + 
				"@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
				"@prefix kgcs: 	<" + kgcs + ">.\n" + 
				"@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"+ 
				"@prefix owl: <http://www.w3.org/2002/07/owl#> .\n" +
				"@prefix GO: <http://purl.obolibrary.org/obo/GO_#> .\n" +
				"@prefix np: <http://www.nanopub.org/nschema#> .\n" +
				"@prefix pubchem: <http://rdf.ncbi.nlm.nih.gov/pubchem/compound/> .\n" + 
				"@prefix hmdb: <http://identifiers.org/hmdb/> .\n" + 
				"@prefix be: <https://github.com/johnbachman/bioentities/> .\n" +
				"@prefix pfam: <http://identifiers.org/pfam/> . \n" +
				"@prefix interpro: <http://identifiers.org/interpro/> .\n" +
				"@prefix cellosaurus: <https://web.expasy.org/cellosaurus/> . \n"  +
				"@prefix cl: <http://purl.obolibrary.org/obo/CL_#> . \n" +
				"@prefix tissuelist: <http://identifiers.org/tissuelist/> . \n" + 
				"@prefix taxonomy: <http://purl.obolibrary.org/obo/NCBITaxon/> .\n" + 
				"@prefix uniprot: <http://www.unitprot.org/uniprot/> .\n" +
				"@prefix mi: <http://purl.obolibrary.org/obo/mi/> . \n" + 
				"@prefix ncit: <http://purl.obolibrary.org/obo/ncit/> . \n" +
				"@prefix uberon: <http://purl.obolibrary.org/obo/uberon/> . \n";
		
		try{
		    int counter = 0;
		    int index = 0;
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "entity_mentions-" + index + ".trig", "UTF-8");
		    writer.println(prefixes);
		    for(EntityMention entity_mention : entity_mentions) {
		    	if(counter == entity_mentions.size()/30){
		    		index++;
		    		writer.close();
		    		counter=0;
		    		writer = new PrintWriter(WRITE_LOCATION + "entity_mentions-" + index + ".trig", "UTF-8");
		    		writer.println(prefixes);
		    	}
		    	System.out.println("Entity Mention: " + entity_mention.getFrameID());
		    	writer.println(kb + "head-" + entity_mention.getFrameID() + " {");
		    	writer.println("\t" + kb + "nanoPub-" + entity_mention.getFrameID() + "\trdf:type\tnp:Nanopublication ;");
		    	writer.println("\t\tnp:hasAssertion\t" + kb + "assertion-" + entity_mention.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasProvenance\t" + kb + "provenance-" + entity_mention.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasPublicationInfo\t" + kb + "pubInfo-" + entity_mention.getFrameID() + " .");
		    	writer.println("}\n");
		    	writer.println(kb + "assertion-" + entity_mention.getFrameID() + " {");
		    	if(entity_mention.getXref()!=null) {
		    		if(entity_mention.getXref().getID().contains(":")){
		    			writer.println("\t" + kb + entity_mention.getFrameID() + "\trdf:type\t"+ entity_mention.getXref().getID() + " ;");
		    		} else {
		    			writer.println("\t" + kb + entity_mention.getFrameID() + "\trdf:type\t"+ entity_mention.getXref().getNamespace() + ":" + entity_mention.getXref().getID() + " ;");
		    		}	
		    		if(mentionTypeMap.containsKey(entity_mention.getType())){
		    			if(mentionTypeMap.get(entity_mention.getType()).contains(";")){
		    				String types[] = mentionTypeMap.get(entity_mention.getType()).split(";", -1);
		    				for(String type : types){
		    					writer.println("\t\trdf:type\t" + type.replaceAll(" ", "") + " ;");
		    				}
		    			} else {
			    			writer.println("\t\trdf:type\t" + mentionTypeMap.get(entity_mention.getType()) + " ;");
		    			}
		    		}
		    		else {
			    		writer.println("\t\tkges:hasMentionType\t\"" + entity_mention.getType() + "\"^^xsd:string ;");
		    		}
		    		//writer.println("\t\trdf:type\t" + entity_mention.getType() + " ;");
		    		writer.println("\t\trdfs:label\t\"" + entity_mention.getText().replace("\"", "'").replace("\\", "/") + "\" .");
		    	}
		    	writer.println("}\n");
		    	writer.println(kb + "provenance-" + entity_mention.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "assertion-" + entity_mention.getFrameID() );
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + entity_mention.getFrameID());
		    	writer.println("\t\tkgcs:hasFrameType\tkgcs:Frame-" + entity_mention.getFrameType().toString() + " ;");
		    	if(entity_mention.getObjectMeta()!=null){
		    		writer.println("\t\tkgcs:hasMetaObjectComponent\t\"" + entity_mention.getObjectMeta().getComponent() + "\" ;");
		    		writer.println("\t\tkgcs:hasMetaObjectType\tkgcs:" + entity_mention.getObjectMeta().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
		    	}
			    writer.println("\t\tkgcs:hasObjectType\tkgcs:" + entity_mention.getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
			    writer.println("\t\tkgcs:fromSentence\t" + kb + entity_mention.getSentenceID() + " .");
			    writer.println("}\n");
		    	writer.println(kb + "pubInfo-" + entity_mention.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "nanoPub-" + entity_mention.getFrameID() );
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + entity_mention.getFrameID());
			    writer.println("\t\tkgcs:hasStartPositionReference\t" + kb + entity_mention.getStartPos().getReference() + " ;");
                writer.println("\t\tkgcs:hasStartPositionOffset\t\"" + entity_mention.getStartPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\t\tkgcs:hasStartPositionObjectType\tkgcs:" + entity_mention.getStartPos().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
			    writer.println("\t\tkgcs:hasEndPositionReference\t" + kb + entity_mention.getEndPos().getReference() + " ;");
                writer.println("\t\tkgcs:hasEndPositionOffset\t\"" + entity_mention.getEndPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\t\tkgcs:hasEndPositionObjectType\tkgcs:" + entity_mention.getEndPos().getObjectType().toString().toLowerCase().replaceAll("_","-") + " .");
                writer.println("}\n");
			    counter++;
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something
		}
		System.out.println("Printing Event Mention TRIG to file");
		
		try{
			int counter = 0;
			int index = 0;
			PrintWriter writer = new PrintWriter(WRITE_LOCATION + "event_mentions-" + index + ".trig", "UTF-8");
			writer.println(prefixes);
		    for(EventMention event_mention : event_mentions) {
		    	if(counter == event_mentions.size()/5){
		    		index++;
		    		writer.close();
		    		counter=0;
		    		writer = new PrintWriter(WRITE_LOCATION + "event_mentions-" + index + ".trig", "UTF-8");
		    		writer.println(prefixes);
		    	}
				System.out.println("Event Mention: " + event_mention.getFrameID());
				//--------------------------------------------------------------------------------------------------------------
				//Written by Ian Gross
				//	Requires remodeling of our output to include multiple arguments
				//for(int i = 0; i < event_mention.getArgumentList().size(); i++) {
				//	System.out.println(event_mention.getArgumentList().get(i).getElements());
				//}
				//System.out.println(event_mention.getArgumentList().getElements());
				//--------------------------------------------------------------------------------------------------------------
		    	writer.println(kb + "head-" + event_mention.getFrameID() + " {");
		    	writer.println("\t" + kb + "nanoPub-" + event_mention.getFrameID() + "\trdf:type np:Nanopublication ;");
		    	writer.println("\t\tnp:hasAssertion\t" + kb + "assertion-" + event_mention.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasProvenance\t" + kb + "provenance-" + event_mention.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasPublicationInfo\t" + kb + "pubInfo-" + event_mention.getFrameID() + " .");
		    	writer.println("}\n");
		    	writer.println(kb + "assertion-" + event_mention.getFrameID() + " {");
		    	writer.println("\t" + kb + event_mention.getFrameID() );
		    	
		    	if(mentionTypeMap.containsKey(event_mention.getType())){
		    		if(mentionTypeMap.get(event_mention.getType()).contains(";")){
	    				String types[] = mentionTypeMap.get(event_mention.getType()).split(";", -1);
	    				for(String type : types){
	    		    		writer.println("\t\trdf:type\t" + type.replaceAll(" ", "") + " ;");
	    				}
	    			} else {
			    		writer.println("\t\trdf:type\t" + mentionTypeMap.get(event_mention.getType()) + " ;");
	    			}
	    		}
	    		else {
		    		writer.println("\t\tkges:hasMentionType\t\"" + event_mention.getType() + "\"^^xsd:string ;");
	    		}
		    	if(mentionTypeMap.containsKey(event_mention.getSubType())){
		    		if(mentionTypeMap.get(event_mention.getSubType()).contains(";")){
	    				String types[] = mentionTypeMap.get(event_mention.getSubType()).split(";", -1);
	    				for(String type : types){
	    		    		writer.println("\t\trdf:type\t" + type.replaceAll(" ", "") + " ;");
	    				}
	    			} else {
			    		writer.println("\t\trdf:type\t" + mentionTypeMap.get(event_mention.getSubType()) + " ;");
	    			}
		    		//writer.println("\t\tkges:hasMentionSubType\t\"" + event_mention.getSubType() + "\"^^xsd:string ;");
		    	}
	    		writer.println("\t\trdfs:label \"" + event_mention.getText().replace("\"", "'").replace("\\", "/") + "\" ;");
				writer.println("\t\trdfs:comment \"" + event_mention.getVerboseText().replace("\"", "'").replace("\\", "/") + "\" ;");
				writer.println("\t\tkgcs:hasTrigger\t\"" + event_mention.getTrigger() + "\" ;");
				writer.println("\t\tkgcs:hasArgument\t");
				for(int i = 0; i < event_mention.getArgumentList().size(); i++) {
					writer.println("\t\t\t [ kgcs:hasArgumentID\t" + kb + event_mention.getArgumentList().get(i).getArg()+ " ;");
					writer.println("\t\t\t\tkgcs:hasArgumentType\t" + kb + event_mention.getArgumentList().get(i).getArgumentType() + " ;");
					writer.println("\t\t\t\tkgcs:hasObjectType\tkgcs:" + event_mention.getArgumentList().get(i).getObjectType() + " ;");
				    writer.println("\t\t\t\tkgcs:hasIndex\t" + event_mention.getArgumentList().get(i).getIndex() + " ;");
				    if (i == event_mention.getArgumentList().size() - 1 ){
				    	writer.println("\t\t\t\trdfs:label\t\"" + event_mention.getArgumentList().get(i).getText() + "\" ] . ");
				    } else {
				    	writer.println("\t\t\t\trdfs:label\t\"" + event_mention.getArgumentList().get(i).getText() + "\" ] , ");
				    }
//				    writer.println("\t\tkgcs:hasArgumentArgumentType\tkgcs:Argument-" + event_mention.getArguments().getArgumentType() + " ;");
//				    writer.println("\t\tkgcs:hasArgumentType\t\"" + event_mention.getArguments().getArgumentType() + "\" ;");
//				    writer.println("\t\tkgcs:hasArgumentLabel\t\"" + event_mention.getArguments().getText().replace("\"", "'").replace("\\", "/") + "\" ;");
					//	System.out.println(event_mention.getArgumentList().get(i).getElements());
				}
		    	writer.println("}\n");
		    	writer.println(kb + "provenance-" + event_mention.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "assertion-" + event_mention.getFrameID() ); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + event_mention.getFrameID());
				writer.println("\t\tkgcs:hasFrameType\tkgcs:Frame-" + event_mention.getFrameType().toString() + " ;");
				if(event_mention.getObjectMeta()!=null){
		    		writer.println("\t\tkgcs:hasMetaObjectComponent\t\"" + event_mention.getObjectMeta().getComponent() + "\" ;");
		    		writer.println("\t\tkgcs:hasMetaObjectType\tkgcs:" + event_mention.getObjectMeta().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
		    	}
				writer.println("\t\tkgcs:hasObjectType\tkgcs:" + event_mention.getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
                if (event_mention.getContextID() != null){
                	writer.println("\t\tkgcs:hasContext\t" + kb + event_mention.getContextID() + " ;");
                }
                writer.println("\t\tkgcs:foundBy\t\"" + event_mention.getFoundBy() + "\" ;");
/*			    writer.println("\t\tkgcs:hasTrigger\t\"" + event_mention.getTrigger() + "\" ;");
			    writer.println("\t\tkgcs:hasArgument\t" + kb + event_mention.getArguments().getArg() + " ;");
			    writer.println("\t\tkgcs:hasArgumentObjectType\tkgcs:" + event_mention.getArguments().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
			    writer.println("\t\tkgcs:hasArgumentIndex\t" + event_mention.getArguments().getIndex() + " ;");
			    writer.println("\t\tkgcs:hasArgumentArgumentType\tkgcs:Argument-" + event_mention.getArguments().getArgumentType() + " ;");
			    writer.println("\t\tkgcs:hasArgumentType\t\"" + event_mention.getArguments().getArgumentType() + "\" ;");
			    writer.println("\t\tkgcs:hasArgumentLabel\t\"" + event_mention.getArguments().getText().replace("\"", "'").replace("\\", "/") + "\" ;");
*/			    if (event_mention.getIsDirect() != null){
			    	writer.println("\t\tkgcs:boolIsDirect\t\"" + event_mention.getIsDirect() + "\" ;");
			    }
			    if (event_mention.getIsHypothesis() != null){
			    	writer.println("\t\tkgcs:boolIsHypothesis\t \"" + event_mention.getIsHypothesis()+ "\" ;");
			    }
			    writer.println("\t\tkgcs:fromSentence\t" + kb + event_mention.getSentenceID() + " .");
			    writer.println("}\n");
		    	writer.println(kb + "pubInfo-" + event_mention.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "nanoPub-" + event_mention.getFrameID() );
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + event_mention.getFrameID());
				writer.println("\t\tkgcs:hasStartPositionReference\t" + kb + event_mention.getStartPos().getReference() + " ;");
                writer.println("\t\tkgcs:hasStartPositionOffset\t\"" + event_mention.getStartPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\t\tkgcs:hasStartPositionObjectType\tkgcs:" + event_mention.getStartPos().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
			    writer.println("\t\tkgcs:hasEndPositionReference\t" + kb + event_mention.getEndPos().getReference() + " ;");
                writer.println("\t\tkgcs:hasEndPositionOffset\t\"" + event_mention.getEndPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\t\tkgcs:hasEndPositionObjectType\tkgcs:" + event_mention.getEndPos().getObjectType().toString().toLowerCase().replaceAll("_","-") + " .");
			    writer.println("}\n");
			    counter++;
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something
		}
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "contexts.trig", "UTF-8");
		    writer.println(prefixes);
		    for(Context context : contexts) {
				System.out.println("Context: " + context.getFrameID());
		    	writer.println(kb + "head-" + context.getFrameID() + " {");
		    	writer.println("\t" + kb + "nanoPub-" + context.getFrameID() + "\trdf:type np:Nanopublication ;");
		    	writer.println("\t\tnp:hasAssertion\t" + kb + "assertion-" + context.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasProvenance\t" + kb + "provenance-" + context.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasPublicationInfo\t" + kb + "pubInfo-" + context.getFrameID() + " .");
		    	writer.println("}\n");
		    	writer.println(kb + "assertion-" + context.getFrameID() + " {");
				writer.println("\t" + kb + context.getFrameID() );
				if(context.getFacets()!=null){
			    	writer.println("\t\tkgcs:hasFacetObjectType\tkgcs:" + context.getFacets().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
			    	if(context.getFacets().getLocation()!=null)
			    		writer.println("\t\tkgcs:hasFacetLocation\t" + context.getFacets().getLocation() + " ;");
			    	if(context.getFacets().getCellLine()!=null)
			    		writer.println("\t\tkgcs:hasFacetCellLine\t" + context.getFacets().getCellLine() + " ;");
			    	if(context.getFacets().getOrganism()!=null)
			    		writer.println("\t\tkgcs:hasFacetOrganism\t" + context.getFacets().getOrganism() + " ;");
			    	if(context.getFacets().getTissueType()!=null)
			    		writer.println("\t\tkgcs:hasFacetTissueType\t" + context.getFacets().getTissueType() + " ;");
			    }
				writer.println("\t\trdf:type\tkgcs:Context .");
				writer.println("}\n");
		    	writer.println(kb + "provenance-" + context.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "assertion-" + context.getFrameID()); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + context.getFrameID() );
		    	writer.println("\t\tkgcs:hasFrameType\tkgcs:Frame-" + context.getFrameType().toString() + " ;");
				writer.println("\t\tkgcs:hasScope\t" + kb + context.getScopeID() + " ;");
				writer.println("\t\tkgcs:hasObjectType\tkgcs:" + context.getObjectType().toString().toLowerCase().replaceAll("_","-") + " .");
				writer.println("}\n");
		    	writer.println(kb + "pubInfo-" + context.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "nanoPub-" + context.getFrameID() ); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime . ");
		    	writer.println("}\n");
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something		
		}
		
		try{
			int counter = 0;
			int index = 0;
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "sentences-" + index + ".trig", "UTF-8");
		    writer.println(prefixes);
		    for(Sentence sentence : sentences) {
		    	if(counter == sentences.size()/20){
		    		index++;
		    		writer.close();
		    		counter=0;
		    		writer = new PrintWriter(WRITE_LOCATION + "sentences-" + index + ".trig", "UTF-8");
		    		writer.println(prefixes);
		    	}
		    	System.out.println("Sentence: " + sentence.getFrameID());
		    	writer.println(kb + "head-" + sentence.getFrameID() + " {");
		    	writer.println("\t" + kb + "nanoPub-" + sentence.getFrameID() + "\trdf:type np:Nanopublication ;");
		    	writer.println("\t\tnp:hasAssertion\t" + kb + "assertion-" + sentence.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasProvenance\t" + kb + "provenance-" + sentence.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasPublicationInfo\t" + kb + "pubInfo-" + sentence.getFrameID() + " .");
		    	writer.println("}\n");
		    	writer.println(kb + "assertion-" + sentence.getFrameID() + " {");
				writer.println("\t" + kb + sentence.getFrameID() + "\trdf:type\tkgcs:Sentence ;" );
				writer.println("\t\tkgcs:hasContent\t\"" + sentence.getText().replace("\"", "'").replace("\\", "/") + "\" .");
		    	writer.println("}\n");
		    	writer.println(kb + "provenance-" + sentence.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "assertion-" + sentence.getFrameID()); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + sentence.getFrameID() );
		    	writer.println("\t\tkgcs:hasFrameType\tkgcs:Frame-" + sentence.getFrameType().toString() + " ;");
				writer.println("\t\tkgcs:fromPassage\t" + kb + sentence.getPassageID() + " ;");
		    	if(sentence.getObjectMeta()!=null){
		    		writer.println("\t\tkgcs:hasMetaObjectComponent\t\"" + sentence.getObjectMeta().getComponent() + "\" ;");
		    		writer.println("\t\tkgcs:hasMetaObjectType\tkgcs:" + sentence.getObjectMeta().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
		    	}
		    	writer.println("\t\tkgcs:hasObjectType\tkgcs:" + sentence.getObjectType().toString().toLowerCase().replaceAll("_","-") + " .");
		    	writer.println("}\n");
		    	writer.println(kb + "pubInfo-" + sentence.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "nanoPub-" + sentence.getFrameID() ); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + sentence.getFrameID() );
		    	writer.println("\t\tkgcs:hasStartPositionReference\t" + kb + sentence.getStartPos().getReference() + " ;");
                writer.println("\t\tkgcs:hasStartPositionOffset\t\"" + sentence.getStartPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\t\tkgcs:hasStartPositionObjectType\tkgcs:" + sentence.getStartPos().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
			    writer.println("\t\tkgcs:hasEndPositionReference\t" + kb + sentence.getEndPos().getReference() + " ;");
                writer.println("\t\tkgcs:hasEndPositionOffset\t\"" + sentence.getEndPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\t\tkgcs:hasEndPositionObjectType\tkgcs:" + sentence.getEndPos().getObjectType().toString().toLowerCase().replaceAll("_","-") + " .");
		    	writer.println("}\n");
			    counter++;
			    }
		    writer.close();
		} catch (IOException e) {
		   // do something		
		}    
		
		try{
			int counter = 0;
			int index = 0;
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "passages-" + index + ".trig", "UTF-8");
		    writer.println(prefixes);
		    for(Passage passage : passages) {
		    	if(counter == passages.size()/3){
		    		index++;
		    		writer.close();
		    		counter=0;
		    		writer = new PrintWriter(WRITE_LOCATION + "passages-" + index + ".trig", "UTF-8");
		    		writer.println(prefixes);
		    	}
		    	System.out.println("Passage: " + passage.getFrameID());
		    	writer.println(kb + "head-" + passage.getFrameID() + " {");
		    	writer.println("\t" + kb + "nanoPub-" + passage.getFrameID() + "\trdf:type np:Nanopublication ;");
		    	writer.println("\t\tnp:hasAssertion\t" + kb + "assertion-" + passage.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasProvenance\t" + kb + "provenance-" + passage.getFrameID()+ " ;");
		    	writer.println("\t\tnp:hasPublicationInfo\t" + kb + "pubInfo-" + passage.getFrameID() + " .");
		    	writer.println("}\n");
		    	writer.println(kb + "assertion-" + passage.getFrameID() + " {");
				writer.println("\t" + kb + passage.getFrameID() + "\trdf:type\t" + kb + "Passage> ;");
		    	writer.println("\t\tkgcs:hasContent\t\"" + passage.getText().replace("\"", "'").replace("\\", "/")+ "\" .");
				writer.println("}\n");
		    	writer.println(kb + "provenance-" + passage.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "assertion-" + passage.getFrameID()); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + passage.getFrameID() );
		    	writer.println("\t\tkgcs:hasFrameType\tkgcs:Frame-" + passage.getFrameType().toString() + " ;");
		    	if(passage.getObjectMeta()!=null){
		    		writer.println("\t\tkgcs:hasMetaObjectComponent\t\"" + passage.getObjectMeta().getComponent() + "\" ;");
		    		writer.println("\t\tkgcs:hasMetaObjectType\tkgcs:" + passage.getObjectMeta().getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
		    	}
		    	writer.println("\t\tkgcs:hasObjectType\tkgcs:" + passage.getObjectType().toString().toLowerCase().replaceAll("_","-") + " ;");
		    	writer.println("}\n");
		    	writer.println(kb + "pubInfo-" + passage.getFrameID() + " {");
		    	date = sdf.format(new Date());
		    	writer.println("\t" + kb + "nanoPub-" + passage.getFrameID() ); 
		    	writer.println("\t\tprov:generatedAtTime\t\"" + date + "Z\"^^xsd:dateTime .\n");
		    	writer.println("\t" + kb + passage.getFrameID() );
				writer.println("\t\tkgcs:hasIndex\t\"" + passage.getIndex() + "\"^^xsd:integer ;");
				if (passage.getSectionID()!= null && passage.getSectionName().length()!=0){
					writer.println("\t\tkgcs:hasSectionID\t\"" + passage.getSectionID()  + "\" ;");
				}
				if (passage.getSectionName()!=null && passage.getSectionName().length()!=0) {
					writer.println("\t\tkgcs:hasSectionName\t\"" + passage.getSectionName() + "\" ;");
				}
			    writer.println("\t\tkgcs:boolIsTitle\t\"" + passage.getIsTitle() + "\" .");
		    	writer.println("}\n");
			    writer.println("");
			    counter++;
		    }
		    writer.close();
		} catch (IOException e) {
		   // do something		
		}
		System.out.println("Done");
	}
	
	public static HashMap<String,String> extractMentionMap() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(MENTIONMAP_LOCATION));
	    String line =  null;
	    HashMap<String,String> mentionTypeMapRead = new HashMap<String, String>();
	    line=br.readLine(); // skip first line
	    while((line=br.readLine())!=null){
	    	String str[] = line.split(",", -1);
	    	mentionTypeMapRead.put(str[0], str[1]);
	    }
	    br.close();
	    return mentionTypeMapRead;
	}

}
