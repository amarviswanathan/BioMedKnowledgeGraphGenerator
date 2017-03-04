package bean;

public class Passage {
	String sectionName;
	String sectionID;
	Boolean isTitle;
	FrameType frameType = FrameType.PASSAGE;
	ObjectType objectType;
	ObjectMeta objectMeta;
	String frameID;
	String text;
	int index;
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getSectionID() {
		return sectionID;
	}
	public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}
	public Boolean getIsTitle() {
		return isTitle;
	}
	public void setIsTitle(Boolean isTitle) {
		this.isTitle = isTitle;
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
		this.frameID = frameID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
