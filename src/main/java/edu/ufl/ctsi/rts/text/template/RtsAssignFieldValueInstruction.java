package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Map;



public class RtsAssignFieldValueInstruction extends RtsVariableAssignmentInstruction {

	RtsTemplateVariable<String> var;
	int fieldNum;
	
	public RtsAssignFieldValueInstruction(String varName, int fieldNum) {
		super(varName);
		this.fieldNum = fieldNum;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RtsTemplateVariable getVariable() {
		return var;
	}

	@Override
	public boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables) {
		var = new RtsTemplateVariable<String>(varName);
		var.setValue(args.get(fieldNum));
		
		variables.put(varName, var);
		
		return (var.getValue() != null);
	}

}
