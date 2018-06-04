package bean;

import java.util.ArrayList;

public class EventMention {
	Boolean isHypothesis;
	String sentenceID;//
	Sentence sentence;//
	Position startPos;//
	Position endPos;//
	FrameType frameType = FrameType.EVENT_MENTION; //
	ObjectType objectType; //
	ObjectMeta objectMeta;//
	DocumentMeta documentMeta;
	String frameID; //
	String text; //
	String verboseText; //
	String type;//
	String trigger;
	String subType;//
	String contextID;
	Context context;
	Arguments arguments;
	ArrayList<Arguments> argumentList;
	Boolean isDirect;
	String foundBy;
	public Boolean getIsHypothesis() {
		return isHypothesis;
	}
	public void setIsHypothesis(Boolean isHypothesis) {
		this.isHypothesis = isHypothesis;
	}
	public String getSentenceID() {
		return sentenceID;
	}
	public void setSentenceID(String sentenceID) {
		if(sentenceID!=null)
			this.sentenceID = sentenceID;
	}
	public Sentence getSentence() {
		return sentence;
	}
	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}
	public Position getStartPos() {
		return startPos;
	}
	public void setStartPos(Position startPos) {
		this.startPos = startPos;
	}
	public Position getEndPos() {
		return endPos;
	}
	public void setEndPos(Position endPos) {
		this.endPos = endPos;
	}
	public FrameType getFrameType() {
		return frameType;
	}
/*	public void setFrameType(FrameType frameType) {
		this.frameType = frameType;
	} */
	public ObjectType getObjectType() {
		return objectType;
	}
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	public ObjectMeta getObjectMeta() {
		return objectMeta;
	}
	public void setObjectMeta(ObjectMeta objectMeta) {
		this.objectMeta = objectMeta;
	}
	public DocumentMeta getDocumentMeta() {
		return documentMeta;
	}
	public void setDocumentMeta(DocumentMeta documentMeta) {
		this.documentMeta = documentMeta;
	}
/*	public XRefs getXref() {
		return xref;
	}
	public void setXref(XRefs xref) {
		this.xref = xref;
	}*/
	public String getFrameID() {
		return frameID;
	}
	public void setFrameID(String frameID) {
		this.frameID = frameID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		if(text!=null)
			this.text = text;
	}
	public String getVerboseText() {
		return verboseText;
	}
	public void setVerboseText(String verboseText) {
		if(verboseText!=null)
			this.verboseText = verboseText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type!=null)
			this.type = type;
	}
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		if(trigger!=null)
			this.trigger = trigger;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		if(subType!=null)
			this.subType = subType;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public String getContextID() {
		return contextID;
	}
	public void setContextID(String contextID) {
		if(contextID!=null)
			this.contextID = contextID;
	}
	public Arguments getArguments() {
		return arguments;
	}
	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}
	public ArrayList<Arguments> getArgumentList() {
		return argumentList;
	}
	public void setArgumentList(ArrayList<Arguments> argumentList) {
		this.argumentList = argumentList;
	}
	public Boolean getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}
	public String getFoundBy() {
		return foundBy;
	}
	public void setFoundBy(String foundBy) {
		if(foundBy!=null)
			this.foundBy = foundBy;
	}
}
