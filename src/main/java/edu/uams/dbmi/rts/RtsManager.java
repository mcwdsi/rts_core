package edu.uams.dbmi.rts;

import edu.uams.dbmi.rts.session.RtsSession;

public abstract class RtsManager {
	
	protected RtsTupleFactory templateFactory;
	
	public RtsManager(){
		this.templateFactory = createTemplateFactory();
	}
	
	public RtsTupleFactory getTemplateFactory(){
		return templateFactory;
	}
		
	public RtsSession getRtsSession(){
		return createRtsSession();
	}
	
	protected RtsTupleFactory createTemplateFactory(){
		return new RtsTupleFactory();
	}
	
	protected abstract RtsSession createRtsSession();
}
