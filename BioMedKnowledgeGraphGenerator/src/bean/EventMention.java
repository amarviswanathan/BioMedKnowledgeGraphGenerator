package bean;

public class EventMention {
	Boolean isHypothesis;
	String sentenceID;
	Sentence sentence;
	Position startPos;
	Position endPos;
	FrameType frameType = FrameType.EVENT_MENTION;
	ObjectType objectType;
	XRef xref;
	String frameID;
	String text;
	String verboseText;
	String type;
	String trigger;
	String subType;
	String contextID;
	Context context;
	Arguments arguments;
	Boolean isDirect;
	String foundBy;
	public Boolean getIsHypothesis() {
		return isHypothesis;
	}
	public void setIsHypothesis(Boolean isHypothesis) {
		this.isHypothesis = isHypothesis;
	}
	public String getSentenceID() {
		return sentenceID;
	}
	public void setSentenceID(String sentence) {
		this.sentenceID = sentence;
	}
	public Sentence getSentence() {
		return sentence;
	}
	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}
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
	public FrameType getFrameType() {
		return frameType;
	}
/*	public void setFrameType(FrameType frameType) {
		this.frameType = frameType;
	} */
	public ObjectType getObjectType() {
		return objectType;
	}
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	public XRef getXref() {
		return xref;
	}
	public void setXref(XRef xref) {
		this.xref = xref;
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
	public String getVerboseText() {
		return verboseText;
	}
	public void setVerboseText(String verboseText) {
		this.verboseText = verboseText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public String getContextID() {
		return contextID;
	}
	public void setContextID(String contextID) {
		this.contextID = contextID;
	}
	public Arguments getArguments() {
		return arguments;
	}
	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}
	public Boolean getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}
	public String getFoundBy() {
		return foundBy;
	}
	public void setFoundBy(String foundBy) {
		this.foundBy = foundBy;
	}
}
