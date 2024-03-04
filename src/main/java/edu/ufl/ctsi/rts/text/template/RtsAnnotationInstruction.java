package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Map;

import edu.ufl.ctsi.rts.text.template.dataevent.DataEvent;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventMessageBoard;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventType;

public class RtsAnnotationInstruction extends RtsAbstractInstruction {
	DataEventType type;
	String fieldName;
	int fieldOrderInTable;
	
	public RtsAnnotationInstruction(DataEventType type, String fieldName, int fieldOrderInTable) {
		this.type = type;
		this.fieldName = fieldName;
		this.fieldOrderInTable = fieldOrderInTable;
	}
	
	@Override
	public boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables) {
		
		@SuppressWarnings("rawtypes")
		RtsTemplateVariable recNum = variables.get("RECORD_NUMBER");
		Object o = recNum.getValue();
		int recordNumber = ((Integer)o).intValue();
		
		DataEvent de = new DataEvent(fieldName, args.get(fieldOrderInTable), type, recordNumber); //TODO - need to send through the field number and record number somehow
		DataEventMessageBoard.publish(de);
		
		/*
		 * I think I had expected the publish method to return something
		 * 	that said "yes, the message has been published and 
		 * 		successfully sent to subscribers" but IDK.
		 * 
		 * Need to revisit this.
		 */
		return true;
	}
	
}
