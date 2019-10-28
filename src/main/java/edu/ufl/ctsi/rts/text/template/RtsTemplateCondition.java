package edu.ufl.ctsi.rts.text.template;

public class RtsTemplateCondition {
	int fieldNum;
	String fieldVal;
	String comparator;
	
	public RtsTemplateCondition(int fieldNum, String fieldVal, String comparator) {
		this.fieldNum = fieldNum;
		this.fieldVal = fieldVal;
		if (!(comparator.equals("==") || comparator.equals("!="))) {
			throw new IllegalArgumentException("Comparator of " + comparator + " is not allowed.");
		}
		this.comparator = comparator;
	}
	
	public boolean isMet(String value) {
		if (comparator.equals("=="))
			return this.fieldVal.equals(value);
		else if (comparator.equals("!="))
			return !this.fieldVal.equals(value);
		else 
			return false;
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public String getFieldValue() {
		return fieldVal;
	}
}
