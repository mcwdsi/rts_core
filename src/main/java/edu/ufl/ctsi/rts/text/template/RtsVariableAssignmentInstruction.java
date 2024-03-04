package edu.ufl.ctsi.rts.text.template;


public abstract class RtsVariableAssignmentInstruction extends RtsTemplateInstruction {

	String varName;
	
	public RtsVariableAssignmentInstruction(String varName) {
		this.varName = varName;
	}
	
	public String getVariableName() {
		return varName;
	}
	
	@SuppressWarnings("rawtypes")
	public abstract RtsTemplateVariable getVariable();
}
