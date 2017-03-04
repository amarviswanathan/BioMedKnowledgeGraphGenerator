package bean;

public class Sentence {
	Position startPos;
	Position endPos;
	String passageID;//
	Passage passage;//
	FrameType frameType = FrameType.SENTENCE;//
	ObjectType objectType;//
	ObjectMeta objectMeta;//
	String frameID;//
	String text;//
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
	public Passage getPassage() {
		return passage;
	}
	public void setPassage(Passage passage) {
		this.passage = passage;
	}
	public String getPassageID() {
		return passageID;
	}
	public void setPassageID(String passageID) {
		if(passageID!=null)
			this.passageID = passageID;
	}
	public FrameType getFrameType() {
		return frameType;
	}
/*	public void setFrameType(FrameType frameType) {
		this.frameType = frameType;
	}*/
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
}
