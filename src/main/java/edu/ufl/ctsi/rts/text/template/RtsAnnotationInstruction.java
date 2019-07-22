package edu.ufl.ctsi.rts.text.template;

import edu.ufl.ctsi.rts.text.template.dataevent.DataEvent;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventType;

public class RtsAnnotationInstruction {
	DataEventType type;
	String fieldName;
	
	public RtsAnnotationInstruction(DataEventType type, String fieldName) {
		this.type = type;
		this.fieldName = fieldName;
	}
	
	public boolean execute(String fieldValue, int recordNumber) {
		DataEvent de = new DataEvent(fieldName, fieldValue, type, recordNumber);
		
		
		return true;
	}
	
}
