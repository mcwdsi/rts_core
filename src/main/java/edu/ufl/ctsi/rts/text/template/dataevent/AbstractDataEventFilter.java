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
			eq = (eventType.equals(de.eventType) && fieldName.equals(de.fieldName)
					&& fieldValue.equals(de.fieldValue));
		}
		return eq;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(eventType, fieldName, fieldValue);
	}
}
