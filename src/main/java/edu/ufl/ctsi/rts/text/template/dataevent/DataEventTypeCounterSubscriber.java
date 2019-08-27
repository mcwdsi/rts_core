package edu.ufl.ctsi.rts.text.template.dataevent;

public class DataEventTypeCounterSubscriber implements DataEventSubscriber {

	int count;
	DataEventType de;
	
	public DataEventTypeCounterSubscriber(DataEventType de) {
		count = 0;
		this.de = de;
	}
	
	@Override
	public void notify(DataEvent e) {
		if (e.getDataEventType().equals(de))
			count++;
	}
	
	public int getCount() {
		return count;
	}
	
	public DataEventType getDataEventType() {
		return de;
	}
}
