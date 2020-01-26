package edu.ufl.ctsi.rts.text.template.dataevent;

public class DataEventTypeCounterSubscriber implements DataEventSubscriber {

	int count;
	DataEventType de;
	DataEventFilter def;
	
	public DataEventTypeCounterSubscriber(DataEventType de) {
		count = 0;
		this.de = de;
		this.def = new DataEventFilter(de);
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

	public DataEventFilter getFilter() {
		return def;
	}
}
