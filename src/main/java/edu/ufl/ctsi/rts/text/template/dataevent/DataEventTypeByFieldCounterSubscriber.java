package edu.ufl.ctsi.rts.text.template.dataevent;

public class DataEventTypeByFieldCounterSubscriber implements DataEventSubscriber {

	int counter;
	DataEventType de;
	String fieldName;
	
	public DataEventTypeByFieldCounterSubscriber(DataEventType de, String fieldName) {
		this.de = de;
		this.fieldName = fieldName;
		this.counter = 0;
	}
	
	public DataEventType getDataEventType() {
		return this.de;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	@Override
	public void notify(DataEvent e) {
		if (e.getDataEventType().equals(de) && this.fieldName.equals(e.getFieldName()))
			counter++;
	}

	public int getCount() {
		return counter;
	}
}
