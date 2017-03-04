package bean;

public class Arguments {
	ObjectType objectType = ObjectType.ARGUMENT;
	String arg;
	int index;
	ArgumentType argumentType;
	String text;
	String type;
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public ArgumentType getArgumentType() {
		return argumentType;
	}
	public void setArgumentType(ArgumentType argumentType) {
		this.argumentType = argumentType;
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
	public ObjectType getObjectType() {
		return objectType;
	}
}
