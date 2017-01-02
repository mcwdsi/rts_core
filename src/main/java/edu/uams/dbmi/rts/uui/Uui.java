package edu.uams.dbmi.rts.uui;

import java.net.URI;

public class Uui {
	private URI _URI;

	public Uui(String uri){
		this._URI = URI.create(uri);
	}
	
	public Uui(URI uri){
		this._URI = uri;
	}
	
	public byte[] getByteRepresentation() {
		return _URI.toString().getBytes();
	}
	
	public String toString(){
		return _URI.toString();
	}
	
	public static Uui createFromString(String uri){
		return new Uui(uri);
	}
	
	public boolean equals(Object o){
		if(o instanceof Uui){
			Uui uui = (Uui) o;
			return _URI.equals(uui._URI);
		} else {
			return false;
		}
		
	}

}
