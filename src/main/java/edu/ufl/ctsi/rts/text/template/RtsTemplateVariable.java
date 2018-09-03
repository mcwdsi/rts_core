package edu.ufl.ctsi.rts.text.template;

public class RtsTemplateVariable<T extends Object> {
	String varName;
	T value;
	
	public RtsTemplateVariable(String varName) {
		this.varName = varName;
	}
	
	public String getName() {
		return varName;
	}
	
	public void setValue(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
