package bean;

public class EntityMention {
	Position startPos;
	Position endPos;
	FrameType frameType = FrameType.ENTITY_MENTION;
	String sentenceID;
	Sentence sentence;
	ObjectType objectType;
	XRef xref;
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
	public XRef getXref() {
		return xref;
	}
	public void setXref(XRef xref) {
		this.xref = xref;
	}
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
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
