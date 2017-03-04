package bean;

public class Facets {
	String organism;
	ObjectType objectType = ObjectType.FACET_SET;
	String location;
	String cellLine;
	String tissueType;
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
