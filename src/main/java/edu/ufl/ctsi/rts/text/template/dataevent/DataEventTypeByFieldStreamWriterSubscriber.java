package edu.ufl.ctsi.rts.text.template.dataevent;

import java.io.IOException;
import java.io.Writer;

public class DataEventTypeByFieldStreamWriterSubscriber implements DataEventSubscriber {
	Writer w;
	DataEventType de;
	DataEventFilter def;
	String fieldName;
	String delim;

	
	public DataEventTypeByFieldStreamWriterSubscriber(DataEventType de, String fieldName, Writer w) {
		this(de,fieldName,w,"\t");
	}
	
	public DataEventTypeByFieldStreamWriterSubscriber(DataEventType de, String fieldName, Writer w, String delim) {
		this.w = w;
		this.de = de;
		this.def = new DataEventFilter(de, fieldName, null);
		this.delim = delim;
	}
	
	@Override
	public void notify(DataEvent e) {
		DataEventType eventType = e.getDataEventType();
		String eventFieldName = e.getFieldName();
		try {
			if (eventType != null && eventType.equals(de) && 
					eventFieldName !=null && this.fieldName.equals(e.getFieldName())) {
				w.write(e.getDataEventType().toString());
				w.write(delim);
				w.write(e.getFieldName());
				w.write(delim);
				if (e.getFieldValue() != null) w.write(e.getFieldValue());
				else w.write("null");
				w.write(delim);
				w.write(Integer.toString(e.getRecordNumber()));
				w.write("\n");
			}
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
