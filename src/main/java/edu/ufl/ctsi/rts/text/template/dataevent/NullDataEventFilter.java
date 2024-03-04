package edu.ufl.ctsi.rts.text.template.dataevent;

/**
 * The NullDataEventFilter passes ALL events through.  Use this "filter" when you want everything.
 * 
 * @author hoganwr
 *
 */
public class NullDataEventFilter extends AbstractDataEventFilter {

	/*
	 * The null filter passes all events through (non-Javadoc)
	 * @see edu.ufl.ctsi.rts.text.template.dataevent.DataEventFilter#passes(edu.ufl.ctsi.rts.text.template.dataevent.DataEvent)
	 */
	/**
	 * Returns true for all data events
	 * 
	 * Throws illegal argument exception for null.
	 */
	@Override
	public boolean passes(DataEvent e) {
		if (e==null) throw new IllegalArgumentException("Data event cannot be null.");
		return true;
	}

}
