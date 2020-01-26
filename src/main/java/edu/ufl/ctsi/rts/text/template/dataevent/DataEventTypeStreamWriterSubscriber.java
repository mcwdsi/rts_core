package edu.ufl.ctsi.rts.text.template.dataevent;

import java.io.IOException;
import java.io.Writer;

public class DataEventTypeStreamWriterSubscriber implements DataEventSubscriber {

	Writer w;
	DataEventType de;
	DataEventFilter def;
	String delim;

	
	public DataEventTypeStreamWriterSubscriber(DataEventType de, Writer w) {
		this.w = w;
		this.de = de;
		this.def = new DataEventFilter(de);
		delim = "\t";
	}
	
	public DataEventTypeStreamWriterSubscriber(DataEventType de, Writer w, String delim) {
		this.w = w;
		this.de = de;
		this.def = new DataEventFilter(de);
		this.delim = delim;
	}
	
	@Override
	public void notify(DataEvent e) {
		try {
			if (e.getDataEventType() != null) w.write(e.getDataEventType().toString());
			else w.write("null");
			w.write(delim);
			if (e.getFieldName() != null) w.write(e.getFieldName());
			else w.write("null");
			w.write(delim);
			if (e.getFieldValue() != null) w.write(e.getFieldValue());
			else w.write("null");
			w.write(delim);
			w.write(Integer.toString(e.getRecordNumber()));
			w.write("\n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public DataEventType getDataEventType() {
		return de;
	}

	public DataEventFilter getFilter() {
		return def;
	}
}
