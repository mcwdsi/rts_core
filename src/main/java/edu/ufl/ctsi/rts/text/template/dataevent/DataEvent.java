package edu.ufl.ctsi.rts.text.template.dataevent;

public class DataEvent {
	String fieldName;
	DataEventType eventType;
	int recordNumber;
	String fieldValue;
	
	public DataEvent(String fieldName, String fieldValue, String parseEventTypeTxt, int recordNumber) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.eventType = DataEventType.valueOf(parseEventTypeTxt);
		if (this.eventType == null) {
			throw new IllegalArgumentException("Unknown data event type: " + parseEventTypeTxt);
		}
		this.recordNumber = recordNumber;
	}
	
	public DataEvent(String fieldName, String fieldValue, DataEventType eventType, int recordNumber) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.eventType = eventType;
		if (this.eventType == null) {
			throw new IllegalArgumentException("Unknown data event type: " + eventType);
		}
		this.recordNumber = recordNumber;
	}
	
	public int getRecordNumber() {
		return recordNumber;
	}
	
	public DataEventType getDataEventType() {
		return eventType;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(fieldName);
		sb.append("=");
		sb.append(fieldValue);
		sb.append("\t");
		sb.append(eventType);
		sb.append("\t");
		sb.append(Integer.toString(recordNumber));
		
		return sb.toString();
	}
}
