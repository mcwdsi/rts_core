package edu.ufl.ctsi.rts.text.template.dataevent;

import java.io.IOException;
import java.io.Writer;

public class AllDataEventStreamWriterSubscriber extends AllEventAbstractSubscriber {

	Writer w;
	String delim;
	
	
	public AllDataEventStreamWriterSubscriber(Writer w) {
		this.w = w;
		delim = "\t";
	}
	
	public AllDataEventStreamWriterSubscriber(Writer w, char delim) {
		this.w = w;
		this.delim = Character.toString(delim);
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
			System.err.println("RECORD NUMBER IS: " + e.getRecordNumber());
			w.write(Integer.toString(e.getRecordNumber()));
			w.write("\n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
