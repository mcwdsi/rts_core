package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Map;

import edu.uams.dbmi.rts.iui.Iui;

public class RtsAssignIuiInstruction extends RtsVariableAssignmentInstruction {
	
	RtsTemplateVariable<Iui> var;

	public RtsAssignIuiInstruction(String varName) {
		super(varName);
	}
	
	@Override
	public RtsTemplateVariable<Iui> getVariable() {
		return var;
	}

	@Override
	public boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables) {
		var = new RtsTemplateVariable<Iui>(varName);
		var.setValue(Iui.createRandomIui());
		
		variables.put(varName, var);
		
		return (var.getValue() != null);
	}
}
