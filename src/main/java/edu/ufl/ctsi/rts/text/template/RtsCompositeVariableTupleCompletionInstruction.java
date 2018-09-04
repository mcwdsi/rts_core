package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RtsCompositeVariableTupleCompletionInstruction extends RtsTemplateInstruction {

	String varName;
	
	RtsVariableAssignmentInstruction varInstruction;
	RtsTupleCompletionInstruction tupleInstruction;
	
	public RtsCompositeVariableTupleCompletionInstruction(String varName, 
			List<String> tupleFields, List<String> contentFields) {
	
		tupleInstruction = new RtsTupleCompletionInstruction(tupleFields, contentFields);
		/*
		 * It's an Iui assignment in every case except T tuples when there is something
		 * 	other than [new-iui].
		 * 
		 *  
		 */
		if (!tupleFields.get(0).equals("T") || contentFields.get(0).equals("[new-iui]")) {
			varInstruction = new RtsAssignIuiInstruction(varName);
		} else {
			varInstruction = new RtsAssignTimestampInstruction(varName);
		}
	}
	
	@Override
	public boolean execute(ArrayList<String> args, Map<String, RtsTemplateVariable> variables) {
		// TODO Auto-generated method stub
		return false;
	}

}
