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
		if(arg!=null)
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
	public ObjectType getObjectType() {
		return objectType;
	}
}
