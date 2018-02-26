package bean;

import java.util.ArrayList;

public class Arguments {
	ObjectType objectType = ObjectType.ARGUMENT;
	String arg;
	int index;
	ArgumentType argumentType;
	String text;
	String type;
	ArrayList<String> args;
	
	
	public String getElements(){
		return "\tObject Type: " + objectType +
				"\n\tArg: " + arg +
				"\n\tIndex: " + index + 
				"\n\tArgument Type: " + argumentType.toString() + 
				"\n\tType: " + type +
				"\n\tText: " + text;
	}
	
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}
	public ArrayList<String> getArgs() {
		return args;
	}
	public void setArgs(ArrayList<String> args) {
		this.args = args;
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
