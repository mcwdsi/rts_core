package edu.uams.dbmi.rts;

import edu.uams.dbmi.rts.session.RtsSession;

public abstract class RtsManager {
	
	protected TemplateFactory templateFactory;
	
	public RtsManager(){
		this.templateFactory = createTemplateFactory();
	}
	
	public TemplateFactory getTemplateFactory(){
		return templateFactory;
	}
		
	public RtsSession getRtsSession(){
		return createRtsSession();
	}
	
	protected TemplateFactory createTemplateFactory(){
		return new TemplateFactory();
	}
	
	protected abstract RtsSession createRtsSession();
}
