package edu.ufl.ctsi.rts.text.template.dataevent;

public class DataEventFilter extends AbstractDataEventFilter {
	
	
	
	public DataEventFilter(DataEventType eventType) {
		if (eventType == null) throw new IllegalArgumentException("Single argument constructor for filters must receive non-null value.");
		this.eventType = eventType;
		this.fieldName = null;
		this.fieldValue = null;
	}
	
	public DataEventFilter(String fieldName) {
		if (fieldName == null) throw new IllegalArgumentException("Single argument constructor for filters must receive non-null value.");
		this.fieldName = fieldName;
		this.fieldValue = null;
		this.eventType = null;
	}
	
	public DataEventFilter(String fieldName, String fieldValue) {
		if (fieldName == null && fieldValue == null) throw new IllegalArgumentException("Cannot have both fieldName and fieldValue as null.");
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.eventType = null;
	}
	
	public DataEventFilter(DataEventType eventType, String fieldName, String fieldValue) {
		if (fieldName == null && fieldValue == null && eventType == null) 
			throw new IllegalArgumentException("Cannot have all of eventType, fieldName, and fieldValue as null. At least one must be non-null");
		this.eventType = eventType;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	@Override
	public boolean passes(DataEvent e) {
		boolean matches = true;  //odd, I know, but any failure turns it to false.
		/*
		 * This entire matching process is complicated by how to handle nulls.  The only
		 * 		situation in which a value may NOT be null, is if there is a field value, 
		 * 		although as I sit here and type, I am re-thinking that.  One might want to 
		 * 		trigger on all NI, UN, OT events from PCORnet CDM regardless of which field
		 * 		they apply to, for example.  So, I think any field can be null.  Previously,
		 * 		I thought that the field name should always accompany the field value.
		 * 
		 * The only requirement is that SOME field not be null. But we have ensured through 
		 * 	construction that filters and data events must have something in them.  So that 
		 * 	is accomplished already.
		 * 
		 * Then, the semantics are that all non-null fields must match.
		 */
		
		// any failure causes value to be false across all.
		if (eventType != null) matches = matches && eventType.equals(e.getDataEventType());
		if (fieldName != null) matches = matches && fieldName.equals(e.getFieldName());
		if (fieldValue != null) matches = matches && fieldValue.equals(e.getFieldValue());
		
		/*  OLD LOGIC
		 * 
		if (eventType != null && eventType.equals(e.getDataEventType())) {
			if (fieldName != null && fieldName.equals(e.getFieldName())) {
				matches = (fieldValue == null || fieldValue.equals(e.getFieldValue()));
			} else if (fieldName == null) {
				matches = true;
			}
		} else if (fieldName != null && fieldName.equals(e.getFieldName())) {
			matches = (fieldValue == null || fieldValue.equals(e.getFieldValue()));
		}
		*/
		
		return matches;
	}
	
	public DataEventType getDataEventType() {
		return eventType;
	}

}
