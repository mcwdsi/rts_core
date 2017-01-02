package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.DataComponent;
import edu.uams.dbmi.rts.template.component.IuiComponent;
import edu.uams.dbmi.rts.template.component.TemporalComponent;
import edu.uams.dbmi.rts.time.TemporalReference;

/**
 * Template that concretizes a name of a temporal entity
 * @author Josh Hanna
 *
 */
public class TenTemplate extends RtsTemplate {

	IuiComponent namingSystemIui;
	DataComponent name;
	TemporalComponent temporalEntityReference;
	
	@Deprecated
	IuiComponent temporalEntityIui;

	public TenTemplate(){
		this.namingSystemIui = new IuiComponent();
		this.name = new DataComponent();
		this.temporalEntityReference = new TemporalComponent();
		
		this.temporalEntityIui = new IuiComponent();
	}

	/**
	 * deprecated.  Use getAuthoringTimeReference() instead.
	 * @return
	 */
	@Deprecated
	public Iui getAuthoringTimeIui() {
		return this.authoringComponent.getAuthoringTimeIui();
	}
	
	public TemporalReference getAuthoringTimeReference() {
		return this.authoringComponent.getAuthoringTimeReference();
	}
	
	/**
	 * deprecated.  Use setAuthoringTimeReference() instead.
	 * @param temporalEntityIui
	 */
	@Deprecated
	public void setAuthoringTimeIui(Iui temporalEntityIui) {
		this.authoringComponent.setAuthoringTimeIui(temporalEntityIui);
	}
	
	public void setAuthoringTimeReference(TemporalReference tr) {
		this.authoringComponent.setAuthoringTimeReference(tr);
	}
	
	public Iui getNamingSystemIui(){
		return namingSystemIui.getIui();
	}

	public void setNamingSystemIui(Iui newIui){
		this.namingSystemIui.setIui(newIui);
	}

	public String getName(){
		byte[] bytes = name.getData();
		if(bytes != null){
			return new String(bytes);
		} else {
			return null;
		}
	}

	public void setName(String newName){
		if(newName != null){
			this.name.setData(newName.getBytes());
		} else {
			this.name.setData(null);
		}
	}

	@Deprecated
	public Iui getTemporalEntityIui(){
		return temporalEntityIui.getIui();
	}
	
	public TemporalReference getTemporalEntityReference() {
		return temporalEntityReference.getTemporalReference();
	}

	@Deprecated
	public void setTemporalEntityIui(Iui newIui){
		this.temporalEntityIui.setIui(newIui);
	}
	
	public void setTemporalEntityReference(TemporalReference tr) {
		temporalEntityReference.setTemporalReference(tr);
	}

	@Override
	public boolean isTenTemplate(){
		return true;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Ten< ");

		builder.append(this.getTemplateIui());
		builder.append(", ");

		builder.append(this.getAuthorIui());
		builder.append(", ");

		builder.append(this.getAuthoringTimeIui());
		builder.append(", ");

		builder.append(this.getReferentIui());
		builder.append(", ");

		builder.append(this.getName());
		builder.append(", ");

		builder.append(this.getNamingSystemIui());
		builder.append(", ");

		//builder.append(this.getTemporalEntityIui());
		
		builder.append(this.temporalEntityReference.getTemporalReference().getIdentifier());

		builder.append(" >");

		return builder.toString();

	}
}
