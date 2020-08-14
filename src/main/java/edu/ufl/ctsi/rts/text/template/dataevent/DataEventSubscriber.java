package edu.ufl.ctsi.rts.text.template.dataevent;

import java.io.Writer;

public interface DataEventSubscriber {
	public void notify(DataEvent e);

	default DataEventType getDataEventType(){
		return null;
	}

	default String getFieldName(){
		return "";
	}

	default AbstractDataEventFilter getFilter(){
		return new NullDataEventFilter();
	}

	default int getCount() {
		return 0;
	}

	default Writer getWriter(){
		return null;
	}
}
