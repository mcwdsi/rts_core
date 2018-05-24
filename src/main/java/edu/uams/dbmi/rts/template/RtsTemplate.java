package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.AuthoringComponent;
import edu.uams.dbmi.rts.template.component.IuiComponent;

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
	
	public RtsTemplate() {
		this.iuiComponent = new IuiComponent();
		this.authoringComponent = new AuthoringComponent();
	}
	
	public Iui getTemplateIui() {
		return iuiComponent.getIui();
	}
	
	public void setTemplateIui(Iui newIui) {
		this.iuiComponent.setIui(newIui);
	}
	
	public Iui getAuthorIui() {
		return authoringComponent.getAuthorIui();
	}
	
	public void setAuthorIui(Iui newIui) {
		this.authoringComponent.setAuthorIui(newIui);
	}
	
	public boolean isATemplate() {
		return false; 
	}
	
	public boolean isMetadataTemplate() {
		return false;
	}
	
	@Deprecated
	public boolean isPtoDRTemplate() {
		return false;
	}
	
	public boolean isPtoDETemplate() {
		return false;
	}
	
	public boolean isPtoUTemplate() {
		return false;
	}
	
	public boolean isPtoPTemplate() {
		return false;
	}
	
	public boolean isPtoLackUTemplate() {
		return false;
	}
	
	@Deprecated
	public boolean isTenTemplate() {
		return false;
	}
	
	@Deprecated
	public boolean isTeTemplate() {
		return false;
	}
	
	public boolean isPtoCTemplate() {
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
		
		builder.append(">");
		
		return builder.toString();

	}
	
}
