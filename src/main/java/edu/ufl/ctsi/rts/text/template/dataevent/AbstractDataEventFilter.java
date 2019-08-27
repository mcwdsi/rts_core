package edu.ufl.ctsi.rts.text.template.dataevent;

import java.util.Objects;

public abstract class AbstractDataEventFilter {
	
	DataEventType eventType;
	String fieldName;
	String fieldValue;
	
	public abstract boolean passes(DataEvent e);
	
	@Override
	public boolean equals(Object o) {
		boolean eq = false;
		
		if (o instanceof AbstractDataEventFilter) {
			AbstractDataEventFilter de = (AbstractDataEventFilter)o;
			/*
			 * We check == before .equals in case they're null.  If they're not null, then we need to use .equals.
			 * 
			 * If either one is true, then true for that part of the conjunct.
			 */
			eq = (eventType.equals(de.eventType) && (fieldName == de.fieldName || fieldName.equals(de.fieldName))
					&& (fieldValue == de.fieldValue || fieldValue.equals(de.fieldValue)));
		}
		return eq;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(eventType, fieldName, fieldValue);
	}
}
