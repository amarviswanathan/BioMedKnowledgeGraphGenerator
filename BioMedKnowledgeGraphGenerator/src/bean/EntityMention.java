package bean;

public class EntityMention {
	Position startPos;
	Position endPos;
	FrameType frameType = FrameType.ENTITY_MENTION;
	String sentenceID;
	Sentence sentence;
	ObjectType objectType;
	ObjectMeta objectMeta;
	DocumentMeta documentMeta;
	XRefs xref;
	String frameID;
	String text;
	String type;
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
	}*/
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
	public XRefs getXref() {
		return xref;
	}
	public void setXref(XRefs xref) {
		this.xref = xref;
	}
	public String getFrameID() {
		return frameID;
	}
	public void setFrameID(String frameID) {
		if(frameID!=null)
			this.frameID = frameID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		if(text!=null)
			this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type!=null)
			this.type = type;
	}
}
