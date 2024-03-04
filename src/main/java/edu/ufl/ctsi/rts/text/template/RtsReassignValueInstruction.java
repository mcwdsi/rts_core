package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Map;

public class RtsReassignValueInstruction extends RtsVariableAssignmentInstruction {
	
	String globalArgName;
	@SuppressWarnings("rawtypes")
	RtsTemplateVariable var;
	
	public RtsReassignValueInstruction(String varName, String globalArgName) {
		super(varName);
		this.globalArgName = globalArgName.trim();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RtsTemplateVariable getVariable() {
		return var;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean execute(ArrayList<String> fieldsAndSysVariables, Map<String, RtsTemplateVariable> variables) {
		System.out.println("Reassigning value of " + varName + " to value of " + globalArgName);
		RtsTemplateVariable rtv = variables.get(this.globalArgName);
		Object val = rtv.getValue();
		var = new RtsTemplateVariable(varName);
		var.setValue(val);
		variables.put(varName, var);
		return true;
	}

}
