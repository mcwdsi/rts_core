package edu.ufl.ctsi.rts.text.template.dataevent;

public class DataEventFilter extends AbstractDataEventFilter {
	
	
	
	public DataEventFilter(DataEventType eventType) {
		this.eventType = eventType;
		this.fieldName = null;
		this.fieldValue = null;
	}
	
	public DataEventFilter(String fieldName) {
		this.fieldName = fieldName;
		this.fieldValue = null;
		this.eventType = null;
	}
	
	public DataEventFilter(String fieldName, String fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.eventType = null;
	}
	
	public DataEventFilter(DataEventType eventType, String fieldName, String fieldValue) {
		this.eventType = eventType;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	@Override
	public boolean passes(DataEvent e) {
		boolean matches = false;
		if (eventType != null && eventType.equals(e.getDataEventType())) {
			if (fieldName != null && fieldName.equals(e.getFieldName())) {
				matches = (fieldValue == null || fieldValue.equals(e.getFieldValue()));
			} else if (fieldName == null) {
				matches = true;
			}
		} else if (fieldName != null && fieldName.equals(e.getFieldName())) {
			matches = (fieldValue == null || fieldValue.equals(e.getFieldValue()));
		}
		
		return matches;
	}
	
	public DataEventType getDataEventType() {
		return eventType;
	}

}
