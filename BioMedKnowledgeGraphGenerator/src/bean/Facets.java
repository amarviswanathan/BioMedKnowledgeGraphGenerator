package bean;

public class Facets {
	String organism;
	ObjectType objectType = ObjectType.FACET_SET;
	String location;
	String cellLine;
	String tissueType;
	
	public String getElements(){
		String elements = "\tObject Type: " + objectType.toString();
		if(location!=null)
			elements += "\n\tLocation: " + location;
		if(organism!=null)
			elements += "\n\tOrganism: " + organism;
		if(cellLine!=null)
			elements += "\n\tCell Line: " + cellLine;
		if(tissueType!=null)
			elements += "\n\tTissue Type: " + tissueType;
		return elements;
	}
	
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		if(organism!=null)
			this.organism = organism;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		if(location!=null)
			this.location = location;
	}
	public String getCellLine() {
		return cellLine;
	}
	public void setCellLine(String cellLine) {
		if(cellLine!=null)
			this.cellLine = cellLine;
	}
	public String getTissueType() {
		return tissueType;
	}
	public void setTissueType(String tissueType) {
		if(tissueType!=null)
			this.tissueType = tissueType;
	}
}
