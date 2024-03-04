package edu.uams.dbmi.rts.session;

import java.util.HashSet;
import java.util.Set;

import edu.uams.dbmi.rts.tuple.MetadataTuple;
import edu.uams.dbmi.rts.tuple.RtsTuple;

public class RtsTransaction {

	private RtsSession session;
	
	private Set<RtsTuple> cache = new HashSet<RtsTuple>();

	public RtsTransaction(RtsSession session) {
		this.session = session;
	}

	/**
	 * Add a tuple to the transaction
	 * 
	 * @param tuple
	 */
	public void addTuple(RtsTuple tuple) {
		cache.add(tuple);
	}
	
	/**
	 * updates a tuple that already exists within the RTS with given metadata
	 * 
	 * @param tuple
	 * @param metadata
	 */
	public void updateTuple(RtsTuple tuple, MetadataTuple metadata){
		cache.add(tuple);
		cache.add(metadata);
		
	}

	/**
	 * Attempt to commit the transaction to the RtsStore
	 * 
	 * @return true if the commit was successful, false otherwise
	 */
	public boolean commit() {
		boolean status = session.saveTuples(cache);
		cache.clear();
		
		return status;
	}
}
