package edu.ufl.ctsi.rts.text.template;

public class RtsTemplateCondition {
	int fieldNum;
	String fieldVal;
	
	public RtsTemplateCondition(int fieldNum, String fieldVal) {
		this.fieldNum = fieldNum;
		this.fieldVal = fieldVal;
	}
	
	public boolean isMet(String value) {
		return this.fieldVal.equals(value);
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public String getFieldValue() {
		return fieldVal;
	}
}
