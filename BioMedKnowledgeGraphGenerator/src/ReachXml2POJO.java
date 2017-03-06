import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
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
public class ReachXml2POJO {
	
	//TODO move these to a property file
//	public static final String READ_LOCATION = "/home/amar/Data/xml/";
//	public static final String WRITE_LOCATION = "/home/amar/Data/rdf/";
	public static final String READ_LOCATION = "/home/sabbir/Programs/xml/";
	public static final String WRITE_LOCATION = "/home/sabbir/Programs/rdf/";
	
	
	static Set<EntityMention> entity_mentions = new HashSet<EntityMention>();
	static Set<EventMention> event_mentions = new HashSet<EventMention>();
	static Set<Context> contexts = new HashSet<Context>();
	static Set<Sentence> sentences = new HashSet<Sentence>();
	static Set<Passage> passages = new HashSet<Passage>();
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//Files.newDirectoryStream(Paths.get(READ_LOCATION),path -> path.toString().contains(".entities"))
		Files.newDirectoryStream(Paths.get(READ_LOCATION))
		.forEach(s -> {
			try {
				readXml(s.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("Printing Entity Mention TTL to file");
		String prefixes="@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n" +
				"@prefix sio:     <http://semanticscience.org/resource/> .\n" + 
				"@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
				"@prefix kgeds: 	<http://localhost/kged/schema#>.\n" + 
				"@prefix kged-kd: 	<http://localhost/kged/kb#>.\n" +
				"@prefix kged: 	<http://localhost/kged/ont#>.\n" + 
				"@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n";
		try{
		    int counter = 0;
		    int index = 0;
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "entity_mentions-" + index + ".ttl", "UTF-8");
		    writer.println(prefixes);
		    for(EntityMention entity_mention : entity_mentions) {
		    	if(counter == entity_mentions.size()/5){
		    		index++;
		    		writer.close();
		    		counter=0;
		    		writer = new PrintWriter(WRITE_LOCATION + "entity_mentions-" + index + ".ttl", "UTF-8");
		    		writer.println(prefixes);
		    	}
		    	System.out.println("Entity Mention: " + entity_mention.getFrameID());
			    writer.println("<kged-kb:" + entity_mention.getFrameID() + "> a <kged:EntityMention> ;");
			    writer.println("\trdfs:label \"" + entity_mention.getText().replace("\"", "'").replace("\\", "/") + "\" ;");
			    writer.println("\tkgeds:hasType \"" + entity_mention.getType() + "\" ;");
			    writer.println("\tkgeds:hasFrameType <kged:Frame-" + entity_mention.getFrameType().toString() + "> ;");
			    writer.println("\tkgeds:hasObjectType <kged:ObjectType-" + entity_mention.getObjectType().toString() + "> ;");
			    writer.println("\tkgeds:hasStartPositionReference <kged-kb:" + entity_mention.getStartPos().getReference() + "> ;");
                writer.println("\tkgeds:hasStartPositionOffset \"" + entity_mention.getStartPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\tkgeds:hasStartPositionObjectType <kged:ObjectType-" + entity_mention.getStartPos().getObjectType().toString() + "> ;");
			    writer.println("\tkgeds:hasEndPositionReference <kged-kb:" + entity_mention.getEndPos().getReference() + "> ;");
                writer.println("\tkgeds:hasEndPositionOffset \"" + entity_mention.getEndPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\tkgeds:hasEndPositionObjectType <kged:ObjectType-" + entity_mention.getEndPos().getObjectType().toString() + "> ;");
                writer.println("\tkgeds:fromSentence <kged-kb:" + entity_mention.getSentenceID() + "> ;");
			    writer.println("\tkgeds:hasXrefID \"" + entity_mention.getXref().getID() + "\" ;");
			    writer.println("\tkgeds:hasXrefNamespace \"" + entity_mention.getXref().getNamespace() + "\" ;");
			    writer.println("\tkgeds:hasXrefObjectType <kged:ObjectType-" + entity_mention.getXref().getObjectType().toString() + "> .");
			    writer.println("");
			    counter++;
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something
		}
		
		System.out.println("Printing Event Mention TTL to file");
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "event_mentions.ttl", "UTF-8");
		    writer.println(prefixes);
		    for(EventMention event_mention : event_mentions) {
				System.out.println("Event Mention: " + event_mention.getFrameID());
				writer.println("<kged-kb:" + event_mention.getFrameID() + "> a <kged:EventMention> ;");
				writer.println("\tkgeds:hasFrameType <kged:Frame-" + event_mention.getFrameType().toString() + "> ;");
				writer.println("\tkgeds:hasObjectType <kged:ObjectType-" + event_mention.getObjectType().toString() + "> ;");
			    writer.println("\trdfs:label \"" + event_mention.getText() + "\" ;");
				writer.println("\trdfs:comment \"" + event_mention.getVerboseText().replace("\"", "'").replace("\\", "/") + "\" ;");
				writer.println("\tkgeds:hasType \"" + event_mention.getType() + "\" ;");
			    writer.println("\tkgeds:hasSubType \"" + event_mention.getSubType() + "\" ;");
			    writer.println("\tkgeds:hasStartPositionReference <kged-kb:" + event_mention.getStartPos().getReference() + "> ;");
                writer.println("\tkgeds:hasStartPositionOffset \"" + event_mention.getStartPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\tkgeds:hasStartPositionObjectType <kged:ObjectType-" + event_mention.getStartPos().getObjectType().toString() + "> ;");
			    writer.println("\tkgeds:hasEndPositionReference <kged-kb:" + event_mention.getEndPos().getReference() + "> ;");
                writer.println("\tkgeds:hasEndPositionOffset \"" + event_mention.getEndPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\tkgeds:hasEndPositionObjectType <kged:ObjectType-" + event_mention.getEndPos().getObjectType().toString() + "> ;");
                writer.println("\tkgeds:fromSentence <kged-kb:" + event_mention.getSentenceID() + "> ;");
			    writer.println("\tkgeds:hasContext <kged-kb:" + event_mention.getContextID() + "> ;");
			    writer.println("\tkgeds:foundBy \"" + event_mention.getFoundBy() + "\" ;");
			    writer.println("\tkgeds:hasTrigger \"" + event_mention.getTrigger() + "\" ;");
			    writer.println("\tkgeds:hasArgument <kged-kb:" + event_mention.getArguments().getArg() + "> ;");
			    writer.println("\tkgeds:hasArgumentObjectType <kged:ObjectType-" + event_mention.getArguments().getObjectType().toString() + "> ;");
			    writer.println("\tkgeds:hasArgumentIndex " + event_mention.getArguments().getIndex() + " ;");
			    writer.println("\tkgeds:hasArgumentArgumentType <kged-Argument:" + event_mention.getArguments().getArgumentType() + "> ;");
			    writer.println("\tkgeds:hasArgumentType \"" + event_mention.getArguments().getType() + "\" ;");
			    writer.println("\tkgeds:hasArgumentLabel \"" + event_mention.getArguments().getText() + "\" ;");
			    writer.println("\tkgeds:boolIsDirect \"" + event_mention.getIsDirect() + "\" ;");
			    writer.println("\tkgeds:boolIsHypothesis \"" + event_mention.getIsHypothesis()+ "\" .");
			    writer.println("");
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something
		}
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "contexts.ttl", "UTF-8");
		    writer.println(prefixes);
		    for(Context context : contexts) {
				System.out.println("Context: " + context.getFrameID());
				writer.println("<kged-kb:" + context.getFrameID() + "> a <kged:Context> ;");
				writer.println("\tkgeds:hasFrameType <kged:Frame-" + context.getFrameType().toString() + "> ;");
				writer.println("\tkgeds:hasScope <kged-kb:" + context.getScopeID() + "> ;");
			    if(context.getFacets()!=null){
			    	writer.println("\tkgeds:hasFacetObjectType <kged:ObjectType-" + context.getFacets().getObjectType().toString() + "> ;");
			    	if(context.getFacets().getLocation()!=null)
			    		writer.println("\tkgeds:hasFacetLocation \"" + context.getFacets().getLocation() + "\" ;");
			    	if(context.getFacets().getCellLine()!=null)
			    		writer.println("\tkgeds:hasFacetCellLine \"" + context.getFacets().getCellLine() + "\" ;");
			    	if(context.getFacets().getOrganism()!=null)
			    		writer.println("\tkgeds:hasFacetOrganism \"" + context.getFacets().getOrganism() + "\" ;");
			    	if(context.getFacets().getTissueType()!=null)
			    		writer.println("\tkgeds:hasFacetTissueType \"" + context.getFacets().getTissueType() + "\" ;");
			    }
				writer.println("\tkgeds:hasObjectType <kged:ObjectType-" + context.getObjectType().toString() + "> .");
			    writer.println("");
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something		
		}
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "sentences.ttl", "UTF-8");
		    writer.println(prefixes);
		    for(Sentence sentence : sentences) {
		    	System.out.println("Sentence: " + sentence.getFrameID());
				writer.println("<kged-kb:" + sentence.getFrameID() + "> a <kged:Sentence> ;");
				writer.println("\tkgeds:hasFrameType <kged:Frame-" + sentence.getFrameType().toString() + "> ;");
				writer.println("\tkgeds:fromPassage <kged-kb:" + sentence.getPassageID() + "> ;");
		    	writer.println("\tkgeds:hasContent \"" + sentence.getText().replace("\"", "'").replace("\\", "/") + "\" ;");
		    	if(sentence.getObjectMeta()!=null){
		    		writer.println("\tkgeds:hasMetaObjectComponent \"" + sentence.getObjectMeta().getComponent() + "\" ;");
		    		writer.println("\tkgeds:hasMetaObjectType <kged:ObjectType-" + sentence.getObjectMeta().getObjectType().toString() + "> ;");
		    	}
		    	writer.println("\tkgeds:hasStartPositionReference <kged-kb:" + sentence.getStartPos().getReference() + "> ;");
                writer.println("\tkgeds:hasStartPositionOffset \"" + sentence.getStartPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\tkgeds:hasStartPositionObjectType <kged:ObjectType-" + sentence.getStartPos().getObjectType().toString() + "> ;");
			    writer.println("\tkgeds:hasEndPositionReference <kged-kb:" + sentence.getEndPos().getReference() + "> ;");
                writer.println("\tkgeds:hasEndPositionOffset \"" + sentence.getEndPos().getOffset() + "\"^^xsd:integer ;");
                writer.println("\tkgeds:hasEndPositionObjectType <kged:ObjectType-" + sentence.getEndPos().getObjectType().toString() + "> ;");
                writer.println("\tkgeds:hasObjectType <kged:ObjectType-" + sentence.getObjectType().toString() + "> .");
			    writer.println("");
			    }
		    writer.close();
		} catch (IOException e) {
		   // do something		
		}    
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "passages.ttl", "UTF-8");
		    writer.println(prefixes);
		    for(Passage passage : passages) {
		    	System.out.println("Passage: " + passage.getFrameID());
		    	writer.println("<kged-kb:" + passage.getFrameID() + "> a <kged:Passage> ;");
		    	writer.println("\tkgeds:hasFrameType <kged:Frame-" + passage.getFrameType().toString() + "> ;");
				writer.println("\tkgeds:hasIndex \"" + passage.getIndex() + "\"^^xsd:integer ;");
		    	writer.println("\tkgeds:hasSectionID \"" + passage.getSectionID()  + "\" ;");
		    	writer.println("\tkgeds:hasSectionName \"" + passage.getSectionName() + "\" ;");
		    	writer.println("\tkgeds:hasContent \"" + passage.getText().replace("\"", "'").replace("\\", "/")+ "\" ;");
		    	if(passage.getObjectMeta()!=null){
		    		writer.println("\tkgeds:hasMetaObjectComponent \"" + passage.getObjectMeta().getComponent() + "\" ;");
		    		writer.println("\tkgeds:hasMetaObjectType <kged:ObjectType-" + passage.getObjectMeta().getObjectType().toString() + "> ;");
		    	}
		    	writer.println("\tkgeds:hasObjectType <kged:ObjectType-" + passage.getObjectType().toString() + "> ;");
			    writer.println("\tkgeds:boolIsTitle \"" + passage.getIsTitle() + "\" .");
			    writer.println("");
		    }
		    writer.close();
		} catch (IOException e) {
		   // do something		
		}
		
/*		System.out.println("Assigning Passages to Sentences");
		for(Sentence sentence : sentences) {
			if(sentence.getPassageID()!=null){
				System.out.println("Searching for: " + sentence.getPassageID());
				for (Passage passage : passages){
					if(passage.getFrameID().equals(sentence.getPassageID())){
						System.out.println("Found " + passage.getFrameID());
						sentence.setPassage(passage);
						break;
					}
				}
			}
		}
		
		System.out.println("Assigning Sentences to Contexts");
		for(Context context : contexts) {
			if(context.getScopeID()!=null){
				System.out.println("Searching for: " + context.getScopeID());
				for (Sentence sentence : sentences){
					if(sentence.getFrameID().equals(context.getScopeID())){
						System.out.println("Found " + sentence.getFrameID());
						context.setScope(sentence);
						break;
					}
				}
			}
		}
		
		System.out.println("Assigning Sentences to Entity Mentions");
		for(EntityMention entity_mention : entity_mentions) {
			if(entity_mention.getSentenceID()!=null){
				System.out.println("Searching for: " + entity_mention.getSentenceID());
				for (Sentence sentence : sentences){
					if(sentence.getFrameID().equals(entity_mention.getSentenceID())){
						System.out.println("Found " + sentence.getFrameID());
						entity_mention.setSentence(sentence);
						break;
					}
				}
			}
		}
		
		System.out.println("Assigning Sentences and Contexts to Event Mentions");
		for(EventMention event_mention : event_mentions) {
			if(event_mention.getSentenceID()!=null){
				System.out.println("Searching for: " + event_mention.getSentenceID());
				for (Sentence sentence : sentences){
					if(sentence.getFrameID().equals(event_mention.getSentenceID())){
						System.out.println("Found " + sentence.getFrameID());
						event_mention.setSentence(sentence);
						break;
					}
				}
				System.out.println(": " + event_mention.getSentenceID());
			}
			if(event_mention.getContextID()!=null){
				System.out.println("Searching for: " + event_mention.getContextID());
				for (Context context : contexts){
					if(context.getFrameID().equals(event_mention.getContextID())){
						System.out.println("Found " + context.getFrameID());
						event_mention.setContext(context);
						break;
					}
				}
				System.out.println(": " + event_mention.getContextID());
				
			}
		}
		
		System.out.println("Printing txt to file");
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "entity_mentions.txt", "UTF-8");
		    for(EntityMention entity_mention : entity_mentions) {
		    	System.out.println("Entity Mention: " + entity_mention.getFrameID());
			    writer.println("Frame ID: " + entity_mention.getFrameID());
			    writer.println("Frame Type: " + entity_mention.getFrameType().toString());
			    writer.println("Object Type: " + entity_mention.getObjectType().toString());
			    writer.println("Text: " + entity_mention.getText());
			    writer.println("Type: " + entity_mention.getType());
			    writer.println("Start Position: " + entity_mention.getStartPos());
			    writer.println("\tOffset: " + entity_mention.getStartPos().getOffset());
			    writer.println("\tReference: " + entity_mention.getStartPos().getReference());
			    writer.println("\tObject Type: " + entity_mention.getStartPos().getObjectType().toString());
			    writer.println("End Position: " + entity_mention.getEndPos());
			    writer.println("\tOffset: " + entity_mention.getEndPos().getOffset());
			    writer.println("\tReference: " + entity_mention.getEndPos().getReference());
			    writer.println("\tObject Type: " + entity_mention.getEndPos().getObjectType().toString());
			    writer.println("Sentence ID: " + entity_mention.getSentenceID());
			    writer.println("Xref: " + entity_mention.getXref());
			    writer.println("\tID: " + entity_mention.getXref().getID());
			    writer.println("\tNamespace: " + entity_mention.getXref().getNamespace());
			    writer.println("\tObject Type: " + entity_mention.getXref().getObjectType().toString());
			    writer.println("");
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something
		}
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "event_mentions.txt", "UTF-8");
		    for(EventMention event_mention : event_mentions) {
				System.out.println("Event Mention: " + event_mention.getFrameID());
			    writer.println("Frame ID: " + event_mention.getFrameID());
			    writer.println("Frame Type: " + event_mention.getFrameType().toString());
			    writer.println("Text: " + event_mention.getText());
			    writer.println("Verbose Text: " + event_mention.getVerboseText());
			    writer.println("Type: " + event_mention.getType());
			    writer.println("SubType: " + event_mention.getSubType());
			    writer.println("Start Position: " + event_mention.getStartPos());
			    writer.println("\tOffset: " + event_mention.getStartPos().getOffset());
			    writer.println("\tReference: " + event_mention.getStartPos().getReference());
			    writer.println("\tObject Type: " + event_mention.getStartPos().getObjectType().toString());
			    writer.println("End Position: " + event_mention.getEndPos());
			    writer.println("\tOffset: " + event_mention.getEndPos().getOffset());
			    writer.println("\tReference: " + event_mention.getEndPos().getReference());
			    writer.println("\tObject Type: " + event_mention.getEndPos().getObjectType().toString());
			    writer.println("Object Type: " + event_mention.getObjectType().toString());
			    writer.println("Sentence ID: " + event_mention.getSentenceID());
			    //writer.println("\tFrame ID: " + event_mention.getSentence().getFrameID());
			    //writer.println("\tPassage ID: " + event_mention.getSentence().getPassageID());
			    //writer.println("\tText: " + event_mention.getSentence().getText());
			    //writer.println("\tFrameType: " + event_mention.getSentence().getFrameType());
			    writer.println("Context ID: " + event_mention.getContextID());
			    writer.println("Context:" + event_mention.getContext());
			    writer.println("Found By: " + event_mention.getFoundBy());
			    writer.println("Trigger: " + event_mention.getTrigger());
			    writer.println("Arguments: " + event_mention.getArguments());
			    writer.println(event_mention.getArguments().getElements());
			    writer.println("Is Direct?: " + event_mention.getIsDirect());
			    writer.println("Is Hypothesis?: " + event_mention.getIsHypothesis());
			    writer.println("");
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something
		}
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "contexts.txt", "UTF-8");
		    for(Context context : contexts) {
				System.out.println("Context: " + context.getFrameID());
			    writer.println("Frame ID: " + context.getFrameID());
			    writer.println("Frame Type: " + context.getFrameType().toString());
			    writer.println("Scope ID: " + context.getScopeID());
			    writer.println("Scope: " + context.getScope());
			    writer.println("Object Type: " + context.getObjectType().toString());
			    writer.println("Facets: " + context.getFacets());
			    writer.println(context.getFacets().getElements());
			    writer.println("");
		    }
		    writer.close();
		} catch (IOException e) {
			   // do something		
		}
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "sentences.txt", "UTF-8");
		    for(Sentence sentence : sentences) {
		    	System.out.println("Sentence: " + sentence.getFrameID());
		    	writer.println("Frame ID: " + sentence.getFrameID());
		    	writer.println("Frame Type: " + sentence.getFrameType());
		    	writer.println("Passage ID: " + sentence.getPassageID());
		    	writer.println("Passage: " + sentence.getPassage());
		    	writer.println("Text: " + sentence.getText());
		    	writer.println("Object Meta: " + sentence.getObjectMeta());
		    	writer.println("Object Type: " + sentence.getObjectType());
		    	writer.println("Start Position: " + sentence.getStartPos());
			    writer.println("\tOffset: " + sentence.getStartPos().getOffset());
			    writer.println("\tReference: " + sentence.getStartPos().getReference());
			    writer.println("\tObject Type: " + sentence.getStartPos().getObjectType().toString());
			    writer.println("End Position: " + sentence.getEndPos());
			    writer.println("\tOffset: " + sentence.getEndPos().getOffset());
			    writer.println("\tReference: " + sentence.getEndPos().getReference());
			    writer.println("\tObject Type: " + sentence.getEndPos().getObjectType().toString());
		    }
		    writer.close();
		} catch (IOException e) {
		   // do something		
		}    
		
		try{
		    PrintWriter writer = new PrintWriter(WRITE_LOCATION + "passages.txt", "UTF-8");
		    for(Passage passage : passages) {
		    	System.out.println("Passage: " + passage.getFrameID());
		    	writer.println("Frame ID: " + passage.getFrameID());
		    	writer.println("Frame Type: " + passage.getFrameType());
		    	writer.println("Index: " + passage.getIndex());
		    	writer.println("Section ID: " + passage.getSectionID());
		    	writer.println("Section Name: " + passage.getSectionName());
		    	writer.println("Text: " + passage.getText());
		    	writer.println("Object Type: " + passage.getObjectType());
		    	writer.println("Object Meta: " + passage.getObjectMeta());
		    	writer.println("Is Title?: " + passage.getIsTitle());
		    }
		    writer.close();
		} catch (IOException e) {
		   // do something		
		}
		*/
		System.out.println("Done");
	}
	
	public static void readXml(String fileName) throws Exception {
		
		File xmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		
		Document doc = builder.parse(xmlFile);
		
		NodeList frameList = doc.getElementsByTagName("frames");
		
		for(int i = 0; i<frameList.getLength(); i++) {
			Node frame = frameList.item(i);
			if (frame.getNodeType() == Node.ELEMENT_NODE) {
				Element fElement = (Element)frame;
				String frameType = fElement.getElementsByTagName("frame-type").item(0).getTextContent();
				if(frameType.contentEquals("entity-mention")){
					System.out.println("Found Entity Mention");
					EntityMention entityM = new EntityMention();
					// Set Frame ID
					entityM.setFrameID(fElement.getElementsByTagName("frame-id").item(0).getTextContent());
					// Set Sentence ID String
					entityM.setSentenceID(fElement.getElementsByTagName("sentence").item(0).getTextContent());
					// Set Object Type
					switch (fElement.getElementsByTagName("object-type").item(0).getTextContent()) {
					case "frame" : entityM.setObjectType(ObjectType.FRAME);
					break;
					case "frame-collection" : entityM.setObjectType(ObjectType.FRAME_COLLECTION);
					break;
					case "meta-info" : entityM.setObjectType(ObjectType.META_INFO);
					break;
					case "relative-pos" : entityM.setObjectType(ObjectType.RELATIVE_POS);
					break;
					case "db-reference" : entityM.setObjectType(ObjectType.DB_REFERENCE);
					break;
					case "facet-set" : entityM.setObjectType(ObjectType.FACET_SET);
					break;
					case "modification" : entityM.setObjectType(ObjectType.MODIFICATION);
					break;
					case "argument" : entityM.setObjectType(ObjectType.ARGUMENT);
					break;
					}
					// Set Start Position
					Element spElement = (Element) fElement.getElementsByTagName("start-pos").item(0);
					Position sp = new Position();
					sp.setReference(spElement.getElementsByTagName("reference").item(0).getTextContent());
					sp.setOffset(Integer.parseInt(spElement.getElementsByTagName("offset").item(0).getTextContent()));
					entityM.setStartPos(sp);
					// Set End Position
					Element epElement = (Element) fElement.getElementsByTagName("end-pos").item(0);
					Position ep = new Position();
					ep.setReference(epElement.getElementsByTagName("reference").item(0).getTextContent());
					ep.setOffset(Integer.parseInt(epElement.getElementsByTagName("offset").item(0).getTextContent()));
					entityM.setEndPos(ep);
					// Set Xref
					Element xElement = (Element) fElement.getElementsByTagName("xrefs").item(0);
					XRefs xrefs = new XRefs();
					xrefs.setID(xElement.getElementsByTagName("id").item(0).getTextContent());
					xrefs.setNamespace(xElement.getElementsByTagName("namespace").item(0).getTextContent());
					entityM.setXref(xrefs);
					// Set Text
					entityM.setText(fElement.getElementsByTagName("text").item(0).getTextContent());
					// Set Type
					entityM.setType(fElement.getElementsByTagName("type").item(0).getTextContent());
					entity_mentions.add(entityM);
				} else if(frameType.contentEquals("event-mention")) {
					System.out.println("Found Event Mention");
					EventMention eventM = new EventMention();
					// Set Frame ID
					eventM.setFrameID(fElement.getElementsByTagName("frame-id").item(0).getTextContent());
					// Set Sentence ID String
					eventM.setSentenceID(fElement.getElementsByTagName("sentence").item(0).getTextContent());
					// Set Object Type
					switch (fElement.getElementsByTagName("object-type").item(0).getTextContent()) {
					case "frame" : eventM.setObjectType(ObjectType.FRAME);
					break;
					case "frame-collection" : eventM.setObjectType(ObjectType.FRAME_COLLECTION);
					break;
					case "meta-info" : eventM.setObjectType(ObjectType.META_INFO);
					break;
					case "relative-pos" : eventM.setObjectType(ObjectType.RELATIVE_POS);
					break;
					case "db-reference" : eventM.setObjectType(ObjectType.DB_REFERENCE);
					break;
					case "facet-set" : eventM.setObjectType(ObjectType.FACET_SET);
					break;
					case "modification" : eventM.setObjectType(ObjectType.MODIFICATION);
					break;
					case "argument" : eventM.setObjectType(ObjectType.ARGUMENT);
					break;
					}
					// Set Start Position
					Element spElement = (Element) fElement.getElementsByTagName("start-pos").item(0);
					Position sp = new Position();
					sp.setReference(spElement.getElementsByTagName("reference").item(0).getTextContent());
					sp.setOffset(Integer.parseInt(spElement.getElementsByTagName("offset").item(0).getTextContent()));
					eventM.setStartPos(sp);
					// Set End Position
					Element epElement = (Element) fElement.getElementsByTagName("end-pos").item(0);
					Position ep = new Position();
					ep.setReference(epElement.getElementsByTagName("reference").item(0).getTextContent());
					ep.setOffset(Integer.parseInt(epElement.getElementsByTagName("offset").item(0).getTextContent()));
					eventM.setEndPos(ep);
					// Set Xref
					Element xElement = (Element) fElement.getElementsByTagName("xrefs").item(0);
					/*XRefs xrefs = new XRefs();
					if(xElement.getElementsByTagName("id").item(0)!=null)
						xrefs.setID(xElement.getElementsByTagName("id").item(0).getTextContent());
					if(xElement.getElementsByTagName("namespace").item(0)!=null)
						xrefs.setNamespace(xElement.getElementsByTagName("namespace").item(0).getTextContent());
					eventM.setXref(xrefs);*/
					// Set Text
					eventM.setText(fElement.getElementsByTagName("text").item(0).getTextContent());
					// Set Verbose Text
					eventM.setVerboseText(fElement.getElementsByTagName("verbose-text").item(0).getTextContent());
					// Set Type
					eventM.setType(fElement.getElementsByTagName("type").item(0).getTextContent());
					// Set SubType
					if(fElement.getElementsByTagName("subtype").item(0)!=null)
						eventM.setSubType(fElement.getElementsByTagName("subtype").item(0).getTextContent());
					// Set Trigger
					if(fElement.getElementsByTagName("trigger").item(0)!=null)
						eventM.setTrigger(fElement.getElementsByTagName("trigger").item(0).getTextContent());
					// Set Arguments
					Element aElement = (Element) fElement.getElementsByTagName("arguments").item(0);
					Arguments arguments = new Arguments();
					if(aElement.getElementsByTagName("arg").item(0)!=null)
						arguments.setArg(aElement.getElementsByTagName("arg").item(0).getTextContent());
					if(aElement.getElementsByTagName("index").item(0)!=null)
						arguments.setIndex(Integer.parseInt(aElement.getElementsByTagName("index").item(0).getTextContent()));
					if(aElement.getElementsByTagName("text").item(0)!=null)
						arguments.setText(aElement.getElementsByTagName("text").item(0).getTextContent());
					if(aElement.getElementsByTagName("type").item(0)!=null)
						arguments.setType(aElement.getElementsByTagName("type").item(0).getTextContent());
					if(aElement.getElementsByTagName("argument-type").item(0)!=null){
						if (aElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("complex")) {
							arguments.setArgumentType(ArgumentType.COMPLEX);
						} else if (aElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("entity")) {
							arguments.setArgumentType(ArgumentType.ENTITY);
						} else if (aElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("event")) {
							arguments.setArgumentType(ArgumentType.EVENT);
						}
					}
					eventM.setArguments(arguments);
					// Set isDirect
					if(fElement.getElementsByTagName("is-direct").item(0)!=null){
						if(fElement.getElementsByTagName("is-direct").item(0).getTextContent().contentEquals("true")){
							eventM.setIsDirect(true);
						} else if (fElement.getElementsByTagName("is-direct").item(0).getTextContent().contentEquals("false")) {
							eventM.setIsDirect(false);
						}
					}
					// Set isHypothesis
					if(fElement.getElementsByTagName("is-hypothesis").item(0)!=null){
						if(fElement.getElementsByTagName("is-hypothesis").item(0).getTextContent().contentEquals("true")){
							eventM.setIsHypothesis(true);
						} else if (fElement.getElementsByTagName("is-hypothesis").item(0).getTextContent().contentEquals("false")) {
							eventM.setIsHypothesis(false);
						}
					}
					// Set Found By
					if(fElement.getElementsByTagName("found-by").item(0)!=null)
						eventM.setFoundBy(fElement.getElementsByTagName("found-by").item(0).getTextContent());
					// Set Context ID String
					if(fElement.getElementsByTagName("context").item(0)!=null)
						eventM.setContextID(fElement.getElementsByTagName("context").item(0).getTextContent());
					event_mentions.add(eventM);
				} else if(frameType.contentEquals("context")) {
					System.out.println("Found Context");
					Context context = new Context();
					// Set Frame ID
					context.setFrameID(fElement.getElementsByTagName("frame-id").item(0).getTextContent());
					// Set Scope ID
					context.setScopeID(fElement.getElementsByTagName("scope").item(0).getTextContent());
					// Set Object Type
					switch (fElement.getElementsByTagName("object-type").item(0).getTextContent()) {
					case "frame" : context.setObjectType(ObjectType.FRAME);
					break;
					case "frame-collection" : context.setObjectType(ObjectType.FRAME_COLLECTION);
					break;
					case "meta-info" : context.setObjectType(ObjectType.META_INFO);
					break;
					case "relative-pos" : context.setObjectType(ObjectType.RELATIVE_POS);
					break;
					case "db-reference" : context.setObjectType(ObjectType.DB_REFERENCE);
					break;
					case "facet-set" : context.setObjectType(ObjectType.FACET_SET);
					break;
					case "modification" : context.setObjectType(ObjectType.MODIFICATION);
					break;
					case "argument" : context.setObjectType(ObjectType.ARGUMENT);
					break;
					}
					// Set Facets
					Element facetElement = (Element) fElement.getElementsByTagName("facets").item(0);
					Facets facets = new Facets();
					if(facetElement.getElementsByTagName("location").item(0)!=null)
						facets.setLocation(facetElement.getElementsByTagName("location").item(0).getTextContent());
					if(facetElement.getElementsByTagName("cell-line").item(0)!=null)
						facets.setCellLine(facetElement.getElementsByTagName("cell-line").item(0).getTextContent());
					if(facetElement.getElementsByTagName("organism").item(0)!=null)
						facets.setOrganism(facetElement.getElementsByTagName("organism").item(0).getTextContent());
					if(facetElement.getElementsByTagName("tissue-type").item(0)!=null)
						facets.setTissueType(facetElement.getElementsByTagName("tissue-type").item(0).getTextContent());
					context.setFacets(facets);
					contexts.add(context);
				} else if(frameType.contentEquals("sentence")) {
					System.out.println("Found Sentence");
					Sentence sentence = new Sentence();
					// Set Frame ID
					sentence.setFrameID(fElement.getElementsByTagName("frame-id").item(0).getTextContent());
					// Set Passage ID String
					sentence.setPassageID(fElement.getElementsByTagName("passage").item(0).getTextContent());
					// Set Object Type
					switch (fElement.getElementsByTagName("object-type").item(0).getTextContent()) {
					case "frame" : sentence.setObjectType(ObjectType.FRAME);
					break;
					case "frame-collection" : sentence.setObjectType(ObjectType.FRAME_COLLECTION);
					break;
					case "meta-info" : sentence.setObjectType(ObjectType.META_INFO);
					break;
					case "relative-pos" : sentence.setObjectType(ObjectType.RELATIVE_POS);
					break;
					case "db-reference" : sentence.setObjectType(ObjectType.DB_REFERENCE);
					break;
					case "facet-set" : sentence.setObjectType(ObjectType.FACET_SET);
					break;
					case "modification" : sentence.setObjectType(ObjectType.MODIFICATION);
					break;
					case "argument" : sentence.setObjectType(ObjectType.ARGUMENT);
					break;
					}
					// Set Start Position
					Element spElement = (Element) fElement.getElementsByTagName("start-pos").item(0);
					Position sp = new Position();
					sp.setReference(spElement.getElementsByTagName("reference").item(0).getTextContent());
					sp.setOffset(Integer.parseInt(spElement.getElementsByTagName("offset").item(0).getTextContent()));
					sentence.setStartPos(sp);
					// Set End Position
					Element epElement = (Element) fElement.getElementsByTagName("end-pos").item(0);
					Position ep = new Position();
					ep.setReference(epElement.getElementsByTagName("reference").item(0).getTextContent());
					ep.setOffset(Integer.parseInt(epElement.getElementsByTagName("offset").item(0).getTextContent()));
					sentence.setEndPos(ep);
					// Set Object Meta
					Element omElement = (Element) fElement.getElementsByTagName("object-meta").item(0);
					ObjectMeta om = new ObjectMeta();
					om.setComponent(omElement.getElementsByTagName("component").item(0).getTextContent());
					sentence.setObjectMeta(om);
					// Set Text
					sentence.setText(fElement.getElementsByTagName("text").item(0).getTextContent());
					sentences.add(sentence);
				} else if(frameType.contentEquals("passage")) {
					System.out.println("Found Passage");
					Passage passage = new Passage();
					// Set Frame ID
					passage.setFrameID(fElement.getElementsByTagName("frame-id").item(0).getTextContent());
					// Set Section Name
					passage.setSectionName(fElement.getElementsByTagName("section-name").item(0).getTextContent());
					// Set Section ID
					passage.setSectionID(fElement.getElementsByTagName("section-id").item(0).getTextContent());
					// Set isTitle
					if(fElement.getElementsByTagName("is-title").item(0).getTextContent().contentEquals("true")){
						passage.setIsTitle(true);
					} else if (fElement.getElementsByTagName("is-title").item(0).getTextContent().contentEquals("false")) {
						passage.setIsTitle(false);
					}
					// Set Object Type
					switch (fElement.getElementsByTagName("object-type").item(0).getTextContent()) {
						case "frame" : passage.setObjectType(ObjectType.FRAME);
						break;
						case "frame-collection" : passage.setObjectType(ObjectType.FRAME_COLLECTION);
						break;
						case "meta-info" : passage.setObjectType(ObjectType.META_INFO);
						break;
						case "relative-pos" : passage.setObjectType(ObjectType.RELATIVE_POS);
						break;
						case "db-reference" : passage.setObjectType(ObjectType.DB_REFERENCE);
						break;
						case "facet-set" : passage.setObjectType(ObjectType.FACET_SET);
						break;
						case "modification" : passage.setObjectType(ObjectType.MODIFICATION);
						break;
						case "argument" : passage.setObjectType(ObjectType.ARGUMENT);
						break;
					}
					// Set Object Meta
					Element omElement = (Element) fElement.getElementsByTagName("object-meta").item(0);
					ObjectMeta om = new ObjectMeta();
					om.setComponent(omElement.getElementsByTagName("component").item(0).getTextContent());
					passage.setObjectMeta(om);
					// Set Text
					passage.setText(fElement.getElementsByTagName("text").item(0).getTextContent());
					passages.add(passage);
				} else {
					System.out.println("Found Unknown Frame Type");
				}
			}
			
		}
		
	}

}
