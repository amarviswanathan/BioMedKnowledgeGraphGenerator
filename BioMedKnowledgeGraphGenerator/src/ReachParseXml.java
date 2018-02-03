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
 * @author Sabbir Rashid & Ian Gross
 *
 */
public class ReachParseXml {
	
	//obtain read, write, and mention map location from config.properties
	static PropertyRead mydirs = new PropertyRead();
	public static String READ_LOCATION = mydirs.xmlFileDirectory;
	
	
	static Set<EntityMention> entity_mentions = new HashSet<EntityMention>();
	static Set<EventMention> event_mentions = new HashSet<EventMention>();
	static Set<Context> contexts = new HashSet<Context>();
	static Set<Sentence> sentences = new HashSet<Sentence>();
	static Set<Passage> passages = new HashSet<Passage>();
	/**
	 * @param args
	 */
	public static class ReachXmlContentClass {
		public Set<EntityMention> entity_mentions = new HashSet<EntityMention>();
		public Set<EventMention> event_mentions = new HashSet<EventMention>();
		public Set<Context> contexts = new HashSet<Context>();
		public Set<Sentence> sentences = new HashSet<Sentence>();
		public Set<Passage> passages = new HashSet<Passage>();
		
		public ReachXmlContentClass(Set<EntityMention> ent_m, Set<EventMention> evn_m, Set<Context> ctx, Set<Sentence> sent, Set<Passage> pass){
			entity_mentions = ent_m;
			event_mentions = evn_m;
			contexts = ctx;
			sentences = sent;
			passages = pass;
		}
	}
	
	public static ReachXmlContentClass parseReachXml(String Read_Loc) throws Exception {
		//Re-specify the read location
		READ_LOCATION = Read_Loc;
		//Iterate over every file in read location directory
		Files.newDirectoryStream(Paths.get(READ_LOCATION),
				path -> path.toString().endsWith(".xml"))
		.forEach(s -> {
			try {
				System.out.println("Current File: " + s.toString());
				parseReachXmlFile(s.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return new ReachXmlContentClass(entity_mentions, event_mentions, contexts, sentences, passages);
	}
	
	public static void parseReachXmlFile(String fileName) throws Exception {
		
		File xmlFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		NodeList frameList = doc.getElementsByTagName("frames");
		
		for(int i = 0; i<frameList.getLength(); i++) {
			Node frame = frameList.item(i);
			if (frame.getNodeType() != Node.ELEMENT_NODE) {
				System.err.println("Error: Search node not of element type");
		        System.exit(22);
			}
			Element fElement = (Element)frame;
			//Get Object Frame
			String frameType = fElement.getElementsByTagName("frame-type").item(0).getTextContent();
			if(frameType.contentEquals("entity-mention")){
				System.out.println("Found Entity Mention");
				EntityMention entityM = new EntityMention();
				// Set Frame ID
				entityM.setFrameID(fElement.getElementsByTagName("frame-id").item(0).getTextContent());
				// Set Sentence ID String
				entityM.setSentenceID(fElement.getElementsByTagName("sentence").item(0).getTextContent());
				// Set Object Type (which always a frame, in this case)
				entityM.setObjectType(ObjectType.FRAME);
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
				//Get and Set Object Meta Info
				if(fElement.getElementsByTagName("object-meta").item(0)!=null){
					Element omElement = (Element) fElement.getElementsByTagName("object-meta").item(0);
					ObjectMeta objmeta = new ObjectMeta();
					objmeta.setComponent(omElement.getElementsByTagName("component").item(0).getTextContent());
					entityM.setObjectMeta(objmeta);
				}
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
				// Set Object Type (which always a frame, in this case)
				eventM.setObjectType(ObjectType.FRAME);
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
				
				//Get and Set Object Meta Info
				if(fElement.getElementsByTagName("object-meta").item(0)!=null){
				Element omElement = (Element) fElement.getElementsByTagName("object-meta").item(0);
					ObjectMeta objmeta = new ObjectMeta();
					objmeta.setComponent(omElement.getElementsByTagName("component").item(0).getTextContent());
					eventM.setObjectMeta(objmeta);
				}
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
				// Set Arguments - Single Argument Approach (old), pending delete after new implementation complete
				Element aElement = (Element) fElement.getElementsByTagName("arguments").item(0);
				Arguments arguments = new Arguments();
				if(aElement.getElementsByTagName("arg").item(0)!=null)
					arguments.setArg(aElement.getElementsByTagName("arg").item(0).getTextContent());
				if(aElement.getElementsByTagName("index").item(0)!=null)
					arguments.setIndex(Integer.parseInt(aElement.getElementsByTagName("index").item(0).getTextContent()));
				if(aElement.getElementsByTagName("text").item(0)!=null)
					arguments.setText(aElement.getElementsByTagName("text").item(0).getTextContent());
				if(aElement.getElementsByTagName("object-type").item(0)!=null)
					arguments.setType(aElement.getElementsByTagName("object-type").item(0).getTextContent());
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
				
				//Set Arguments - New Approach
				ArrayList<Arguments> myArgList = new ArrayList<Arguments>();
				for (int argPos = 0; argPos < fElement.getElementsByTagName("arguments").getLength(); argPos++) {
					Element argElement = (Element) fElement.getElementsByTagName("arguments").item(argPos);
					//System.out.println(fElement.getElementsByTagName("arguments").item(0).getTextContent());
					Arguments argument = new Arguments();
					//System.out.println(argElement.getTextContent());
					/*
					System.out.println("----------------");
					System.out.println(argElement.getElementsByTagName("text").item(0).getTextContent());
					System.out.println(argElement.getElementsByTagName("argument-type").item(0).getTextContent());
					System.out.println(argElement.getElementsByTagName("index").item(0).getTextContent());
					System.out.println(argElement.getElementsByTagName("argument-label").item(0).getTextContent());
					System.out.println(argElement.getElementsByTagName("arg").item(0).getTextContent());
					System.out.println("----------------");
					*/
					
					if(argElement.getElementsByTagName("text").item(0)!=null)
						argument.setText(argElement.getElementsByTagName("text").item(0).getTextContent());
					if(argElement.getElementsByTagName("argument-type").item(0)!=null){
						if (argElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("complex")) {
							argument.setArgumentType(ArgumentType.COMPLEX);
						} else if (argElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("entity")) {
							argument.setArgumentType(ArgumentType.ENTITY);
						} else if (argElement.getElementsByTagName("argument-type").item(0).getTextContent().contentEquals("event")) {
							argument.setArgumentType(ArgumentType.EVENT);
						}
					}
					if(argElement.getElementsByTagName("index").item(0)!=null)
						argument.setIndex(Integer.parseInt(argElement.getElementsByTagName("index").item(0).getTextContent()));
					if(argElement.getElementsByTagName("argument-label").item(0)!=null)
						argument.setType(argElement.getElementsByTagName("argument-label").item(0).getTextContent());
					if(argElement.getElementsByTagName("type").item(0)!=null)
						argument.setType(argElement.getElementsByTagName("type").item(0).getTextContent());
					if(argElement.getElementsByTagName("arg").item(0)!=null)
						argument.setArg(argElement.getElementsByTagName("arg").item(0).getTextContent());
					
					myArgList.add(argument);
				}
				eventM.setArgumentList(myArgList);
				
				
				
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
				switch (fElement.getElementsByTagName("object-type").item(1).getTextContent()) {
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
				//Get and Set Object Meta Info
				Element omElement = (Element) fElement.getElementsByTagName("object-meta").item(0);
				ObjectMeta objmeta = new ObjectMeta();
				objmeta.setComponent(omElement.getElementsByTagName("component").item(0).getTextContent());
				sentence.setObjectMeta(objmeta);
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
				//Get and Set Object Meta Info
				Element omElement = (Element) fElement.getElementsByTagName("object-meta").item(0);
				ObjectMeta objmeta = new ObjectMeta();
				objmeta.setComponent(omElement.getElementsByTagName("component").item(0).getTextContent());
				passage.setObjectMeta(objmeta);
				// Set Text
				passage.setText(fElement.getElementsByTagName("text").item(0).getTextContent());
				passages.add(passage);
			} else {
				System.out.println("Found Unknown Frame Type");
			}
		}
	}
}
