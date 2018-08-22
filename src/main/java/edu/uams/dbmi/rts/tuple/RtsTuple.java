package edu.uams.dbmi.rts.tuple;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.tuple.component.AuthoringComponent;
import edu.uams.dbmi.rts.tuple.component.IuiComponent;

/**
 * Class which is a superclass of all Tuples.  All Tuples 
 * 	have an IUI component (the IUI of the Tuple itself), 
 * 	an authoring component (IUI_a, t_a, and t_d), and 
 *  particular component (IUI_p).
 * 
 * @author Josh Hanna
 *
 */
public abstract class RtsTuple {
	
	/*
	 * IuiComponent contains IUI of Tuple itself
	 */
	IuiComponent iuiComponent;
	
	/*
	 * Author of Tuple (Iui), ta parameter (TemporalReference),
	 * 	td parameter of associated metadata Tuple (td)
	 *	 
	 **/
	AuthoringComponent authoringComponent;
	
	public RtsTuple() {
		this.iuiComponent = new IuiComponent();
		this.authoringComponent = new AuthoringComponent();
	}
	
	public Iui getTupleIui() {
		return iuiComponent.getIui();
	}
	
	public void setTupleIui(Iui newIui) {
		this.iuiComponent.setIui(newIui);
	}
	
	public Iui getAuthorIui() {
		return authoringComponent.getAuthorIui();
	}
	
	public void setAuthorIui(Iui newIui) {
		this.authoringComponent.setAuthorIui(newIui);
	}
	
	public boolean isATuple() {
		return false; 
	}
	
	public boolean isMetadataTuple() {
		return false;
	}
	
	@Deprecated
	public boolean isPtoDRTemplate() {
		return false;
	}
	
	public boolean isPtoDETuple() {
		return false;
	}
	
	public boolean isPtoUTuple() {
		return false;
	}
	
	public boolean isPtoPTuple() {
		return false;
	}
	
	public boolean isPtoLackUTuple() {
		return false;
	}
	
	@Deprecated
	public boolean isTenTemplate() {
		return false;
	}
	
	@Deprecated
	public boolean isTeTuple() {
		return false;
	}
	
	public boolean isPtoCTuple() {
		return false;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("T <");
		
		builder.append(this.getTupleIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		builder.append(">");
		
		return builder.toString();

	}
	
}
