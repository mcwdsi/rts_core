package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Map;

public class RtsAssignTimestampInstruction extends RtsVariableAssignmentInstruction {
	
	RtsTemplateVariable<String> var;

	public RtsAssignTimestampInstruction(String varName) {
		super(varName);
	}

	@Override
	public RtsTemplateVariable<String> getVariable() {
		return var;
	}

	@Override
	public boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables) {
		var = new RtsTemplateVariable<String>(varName);
		var.setValue(args.get(0));
		
		variables.put(varName, var);
		
		return (var.getValue() != null);  // could check to make sure it's ISO8601 string
	}
}
