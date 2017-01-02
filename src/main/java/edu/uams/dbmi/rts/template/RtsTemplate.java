package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.AuthoringComponent;
import edu.uams.dbmi.rts.template.component.IuiComponent;
import edu.uams.dbmi.rts.template.component.ParticularComponent;

/**
 * Class which is a superclass of all templates.  All templates 
 * 	have an IUI component (the IUI of the template itself), 
 * 	an authoring component (IUI_a, t_a, and t_d), and 
 *  particular component (IUI_p).
 * 
 * @author Josh Hanna
 *
 */
public abstract class RtsTemplate {
	
	/*
	 * IuiComponent contains IUI of template itself
	 */
	IuiComponent iuiComponent;
	
	/*
	 * Author of template (Iui), ta parameter (TemporalReference),
	 * 	td parameter of associated metadata template (td)
	 *	 
	 **/
	AuthoringComponent authoringComponent;
	
	/*
	 * All templates are primarily about one particular, where we define
	 * 	the primary particular in the case of PtoP templates as the 
	 * 	first particular in the tuple of particulars that are related.
	 * 
	 * This component captures that primary particular.
	 */
	ParticularComponent particularComponent;
	
	public RtsTemplate(){
		this.iuiComponent = new IuiComponent();
		this.authoringComponent = new AuthoringComponent();
		this.particularComponent = new ParticularComponent();
	}
	
	public Iui getTemplateIui(){
		return iuiComponent.getIui();
	}
	
	public void setTemplateIui(Iui newIui){
		this.iuiComponent.setIui(newIui);
	}
	
	public Iui getAuthorIui(){
		return authoringComponent.getAuthorIui();
	}
	
	public void setAuthorIui(Iui newIui){
		this.authoringComponent.setAuthorIui(newIui);
	}
	
	public Iui getReferentIui(){
		return particularComponent.getParticular();
	}
	
	public void setReferentIui(Iui newIui){
		this.particularComponent.setParticular(newIui);
	}
	
	public boolean isATemplate(){
		return false;
	}
	
	public boolean isMetadataTemplate(){
		return false;
	}
	
	public boolean isPtoDRTemplate(){
		return false;
	}
	
	public boolean isPtoUTemplate(){
		return false;
	}
	
	public boolean isPtoPTemplate(){
		return false;
	}
	
	public boolean isPtoLackUTemplate(){
		return false;
	}
	
	public boolean isTenTemplate(){
		return false;
	}
	
	public boolean isTeTemplate(){
		return false;
	}
	
	public boolean isPtoCTemplate(){
		return false;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("T <");
		
		builder.append(this.getTemplateIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		builder.append(this.getReferentIui());
		
		builder.append(">");
		
		return builder.toString();

	}
	
}
