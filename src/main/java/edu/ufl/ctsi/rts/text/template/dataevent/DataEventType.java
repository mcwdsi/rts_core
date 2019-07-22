package edu.ufl.ctsi.rts.text.template.dataevent;

public enum DataEventType {
	IMPLICIT("IM"),
	CODED_VALUE("CV"),
	LITERAL_VALUE("LV"),
	JUSTIFIED_ABSENCE("JA"),
	UNJUSTIFIED_ABSENCE("UA"),
	REDUNDANT_PRESENCE("RP"),
	UNJUSTIFIED_PRESENCE("UP"),
	DISALLOWED_VALUE("DV");
	
	String code;
	
	DataEventType(String code) {
		this.code = code;
	}
	
	public String toString() {
		return code;
	}
}
