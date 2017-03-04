package bean;

public class Context {
	String scopeID;
	Sentence scope;
	FrameType frameType = FrameType.CONTEXT;
	ObjectType objectType = ObjectType.FRAME;
	String frameID;
	Facets facets;
	public Sentence getScope() {
		return scope;
	}
	public void setScope(Sentence scope) {
		this.scope = scope;
	}
	public String getScopeID() {
		return scopeID;
	}
	public void setScopeID(String scopeID) {
		if(scopeID!=null)
			this.scopeID = scopeID;
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
		if(frameID!=null)
			this.frameID = frameID;
	}
	public Facets getFacets() {
		return facets;
	}
	public void setFacets(Facets facets) {
		this.facets = facets;
	}
}
