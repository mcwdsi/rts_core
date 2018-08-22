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
	 * Add a template to the transaction
	 * 
	 * @param template
	 */
	public void addTemplate(RtsTuple template) {
		cache.add(template);
	}
	
	/**
	 * updates a template that already exists within the RTS with given metadata
	 * 
	 * @param template
	 * @param metadata
	 */
	public void updateTemplate(RtsTuple template, MetadataTuple metadata){
		cache.add(template);
		cache.add(metadata);
		
	}

	/**
	 * Attempt to commit the transaction to the RtsStore
	 * 
	 * @return true if the commit was successful, false otherwise
	 */
	public boolean commit() {
		boolean status = session.saveTemplates(cache);
		cache.clear();
		
		return status;
	}
}
