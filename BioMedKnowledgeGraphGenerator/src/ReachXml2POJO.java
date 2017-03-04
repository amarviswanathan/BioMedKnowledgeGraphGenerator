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
	public static final String WRITE_LOCATION = "home/sabbir/Programs/rdf/";
	
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
		
		for(EntityMention entity_mention : entity_mentions) {
			System.out.println("Entity Mention: " + entity_mention.getFrameID());
		}
		
		for(EventMention event_mention : event_mentions) {
			System.out.println("Event Mention: " + event_mention.getFrameID());
		}
		
		for(Context context : contexts) {
			System.out.println("Context: " + context.getFrameID());
		}
		
		for(Sentence sentence : sentences) {
			System.out.println("Sentence: " + sentence.getFrameID());
		}
		
		for(Passage passage : passages) {
			System.out.println("Passage: " + passage.getFrameID());
		}
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
					XRefs xrefs = new XRefs();
					xrefs.setID(xElement.getElementsByTagName("id").item(0).getTextContent());
					xrefs.setNamespace(xElement.getElementsByTagName("namespace").item(0).getTextContent());
					eventM.setXref(xrefs);
					// Set Text
					eventM.setText(fElement.getElementsByTagName("text").item(0).getTextContent());
					// Set Verbose Text
					eventM.setVerboseText(fElement.getElementsByTagName("verbose-text").item(0).getTextContent());
					// Set Type
					eventM.setType(fElement.getElementsByTagName("type").item(0).getTextContent());
					// Set SubType
					eventM.setSubType(fElement.getElementsByTagName("subtype").item(0).getTextContent());
					// Set Trigger
					eventM.setTrigger(fElement.getElementsByTagName("trigger").item(0).getTextContent());
					// Set Arguments
					Element aElement = (Element) fElement.getElementsByTagName("xrefs").item(0);
					Arguments arguments = new Arguments();
					arguments.setArg(aElement.getElementsByTagName("arg").item(0).getTextContent());
					arguments.setIndex(Integer.parseInt(aElement.getElementsByTagName("index").item(0).getTextContent()));
					arguments.setText(aElement.getElementsByTagName("text").item(0).getTextContent());
					arguments.setType(aElement.getElementsByTagName("type").item(0).getTextContent());
					if (aElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("complex")) {
						arguments.setArgumentType(ArgumentType.COMPLEX);
					} else if (aElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("entity")) {
						arguments.setArgumentType(ArgumentType.ENTITY);
					} else if (aElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("event")) {
						arguments.setArgumentType(ArgumentType.EVENT);
					} 
					eventM.setArguments(arguments);
					// Set isDirect
					if(fElement.getElementsByTagName("is-direct").item(0).getTextContent().contentEquals("true")){
						eventM.setIsDirect(true);
					} else if (fElement.getElementsByTagName("is-direct").item(0).getTextContent().contentEquals("false")) {
						eventM.setIsDirect(false);
					}
					// Set isHypothesis
					if(fElement.getElementsByTagName("is-hypothesis").item(0).getTextContent().contentEquals("true")){
						eventM.setIsHypothesis(true);
					} else if (fElement.getElementsByTagName("is-hypothesis").item(0).getTextContent().contentEquals("false")) {
						eventM.setIsHypothesis(false);
					}
					// Set Found By
					eventM.setFoundBy(fElement.getElementsByTagName("found-by").item(0).getTextContent());
					// Set Context ID String
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
					facets.setLocation(facetElement.getElementsByTagName("location").item(0).getTextContent());
					facets.setCellLine(facetElement.getElementsByTagName("cell-line").item(0).getTextContent());
					facets.setOrganism(facetElement.getElementsByTagName("organism").item(0).getTextContent());
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
