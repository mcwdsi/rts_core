package edu.uams.dbmi.rts.cui;

public class Cui {
	private String cui;

	public Cui(String cui){
		this.cui = cui;
	}
	
	public byte[] getByteRepresentation() {
		return cui.getBytes();
	}
	
	public String toString(){
		return cui;
	}
	
	public boolean equals(Object o){
		if(o instanceof Cui){
			Cui cui = (Cui) o;
			return this.cui.equals(cui.toString());
		} else {
			return false;
		}
	}

}

