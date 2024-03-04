package edu.uams.dbmi.rts.tuple.component;

/**
 * represents the data component used in Ten and PtoDR tuples to store non-relationship
 * or metadata information
 * 
 * @author Josh Hanna
 *
 */
public class DataComponent {
	private byte[] data;
	
	public DataComponent(byte[] bytes){
		this.data = bytes;
	}
	
	public DataComponent(){
	}
	
	/**
	 * 
	 * @return a byte array where the non-relationship and non-metadata information for 
	 * the associated tuple
	 */
	public byte[] getData(){
		return data;
	}
	
	/**
	 * saves a byte array representing some non-relationship and non-metadata information
	 * for the associated tuple
	 * @param bytes
	 */
	public void setData(byte[] bytes){
		this.data = bytes;
	}
	
}
