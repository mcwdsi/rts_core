package edu.uams.dbmi.rts.session;

import java.util.HashSet;
import java.util.Set;

import edu.uams.dbmi.rts.template.MetadataTemplate;
import edu.uams.dbmi.rts.template.RtsTemplate;

public class RtsTransaction {

	private RtsSession session;
	
	private Set<RtsTemplate> cache = new HashSet<RtsTemplate>();

	public RtsTransaction(RtsSession session) {
		this.session = session;
	}

	/**
	 * Add a template to the transaction
	 * 
	 * @param template
	 */
	public void addTemplate(RtsTemplate template) {
		cache.add(template);
	}
	
	/**
	 * updates a template that already exists within the RTS with given metadata
	 * 
	 * @param template
	 * @param metadata
	 */
	public void updateTemplate(RtsTemplate template, MetadataTemplate metadata){
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
