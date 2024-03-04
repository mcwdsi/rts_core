package edu.uams.dbmi.rts.tuple.component;

public enum RelationshipPolarity {
	AFFIRMATIVE(""),
	NEGATED("NOT");

	private RelationshipPolarity(String polarity) {
		this.polarity = polarity;
	}

	String polarity;

	public String getPolarity() {
		return polarity;
	}

	@Override
	public String toString() {
		return polarity;
	}
}