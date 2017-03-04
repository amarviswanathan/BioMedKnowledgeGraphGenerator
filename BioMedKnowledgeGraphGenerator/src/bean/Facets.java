package bean;

public class Facets {
	String organism;
	ObjectType objectType;
	String location;
	String cellLine;
	String tissueType;
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCellLine() {
		return cellLine;
	}
	public void setCellLine(String cellLine) {
		this.cellLine = cellLine;
	}
	public String getTissueType() {
		return tissueType;
	}
	public void setTissueType(String tissueType) {
		this.tissueType = tissueType;
	}
}
