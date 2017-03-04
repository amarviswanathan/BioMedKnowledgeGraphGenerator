package bean;

public class Context {
	Sentence scope;
	FrameType frameType = FrameType.CONTEXT;
	ObjectType objectType;
	String frameID;
	Facets facets;
	public Sentence getScope() {
		return scope;
	}
	public void setScope(Sentence scope) {
		this.scope = scope;
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
	public void setObjectType(ObjectType objextType) {
		this.objectType = objextType;
	}
	public String getFrameID() {
		return frameID;
	}
	public void setFrameID(String frameID) {
		this.frameID = frameID;
	}
	public Facets getFacets() {
		return facets;
	}
	public void setFacets(Facets facets) {
		this.facets = facets;
	}
}
