package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RtsTupleCompletionInstruction extends RtsTemplateInstruction {

	ArrayList<String> tupleBlockFields;
	ArrayList<String> contentBlockFields;
	
	public RtsTupleCompletionInstruction(List<String> tupleBlockFields, List<String> contentBlockFields) {
		this.tupleBlockFields = new ArrayList<String>();
		this.tupleBlockFields.addAll(tupleBlockFields);
		this.contentBlockFields = new ArrayList<String>();
		this.contentBlockFields.addAll(contentBlockFields);
	}
	
	@Override
	public boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
