package bean;

public class DocumentMeta {
	//Information about the information extraction tool used, located in the meta-data of each document
	String component; //Component is the Information Extraction tool
	String componentType; //Describes information about the IE tool (completed by a machine)
	String documentID; //documentID is the PubMed Central ID
	String processingStart; //Processing start time of the IE tool
	String processingEnd; //Processing end time of the IE tool
	ObjectType objectType = ObjectType.META_INFO;
	String organization; //The organization that created the IE tool (ex: University of Arizona, Rensselaer Polytechnic Institute, etc.)
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		if(component!=null)
			this.component = component;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		if(componentType!=null)
			this.componentType = componentType;
	}
	public String getDocumentID() {
		return documentID;
	}
	public void setDocumentID(String documentID) {
		if(documentID!=null)
			this.documentID = documentID;
	}
	public String getProcessingStartTime() {
		return processingStart;
	}
	public String getProcessingEndTime() {
		return processingEnd;
	}
	public void setProcessingTime(String processingStart, String processingEnd) {
		if(processingStart!=null && processingEnd!=null) {
			this.processingStart = processingStart;
			this.processingEnd = processingEnd;
		}
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		if(organization!=null)
			this.organization = organization;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
}
