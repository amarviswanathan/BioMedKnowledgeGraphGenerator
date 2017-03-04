package bean;

public class Position {
	String referenceID;
	int offset;
	ObjectType objectType = ObjectType.RELATIVE_POS;
	public String getReference() {
		return referenceID;
	}
	public void setReference(String referenceID) {
		this.referenceID = referenceID;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
}
