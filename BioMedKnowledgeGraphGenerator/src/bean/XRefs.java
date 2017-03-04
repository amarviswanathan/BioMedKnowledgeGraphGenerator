package bean;

public class XRefs {
	ObjectType objectType = ObjectType.DB_REFERENCE;
	String namespace;
	String ID;
	public ObjectType getObjectType() {
		return objectType;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getID() {
		return this.ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
}
