package edu.ufl.ctsi.rts.text.template.dataevent;

public abstract class AllEventAbstractSubscriber implements DataEventSubscriber {

	NullDataEventFilter filter = new NullDataEventFilter();
	
	public AbstractDataEventFilter getFilter() {
		return filter;
	}

}
