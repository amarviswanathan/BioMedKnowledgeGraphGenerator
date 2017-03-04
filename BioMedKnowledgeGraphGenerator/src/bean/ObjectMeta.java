package bean;

public class ObjectMeta {
	String component;
	ObjectType objectType = ObjectType.META_INFO;
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
}
