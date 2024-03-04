package edu.ufl.ctsi.rts.text.template.dataevent;

public enum DataEventType {
	/*IMPLICIT("IM"),
	CODED_VALUE("CV"),
	LITERAL_VALUE("LV"),
	JUSTIFIED_ABSENCE("JA"),
	UNJUSTIFIED_ABSENCE("UA"),
	REDUNDANT_PRESENCE("RP"),
	UNJUSTIFIED_PRESENCE("UP"),
	DISALLOWED_VALUE("DV");
	*/
	
	IM("implicit"),
	CV("coded value"),
	LV("literal value"),
	JA("justified absence"),
	UA("unjustified absence"),
	RP("redundant presence"),
	UP("unjustified presence"),
	DV("disallowed value");
	
	String description;
	
	DataEventType(String description) {
		this.description = description;
	}
	
	public String toString() {
		return description;
	}
}
