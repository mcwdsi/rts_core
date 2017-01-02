package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.rts.cui.Cui;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.ConceptComponent;
import edu.uams.dbmi.rts.template.component.TemporalComponent;
import edu.uams.dbmi.rts.time.TemporalReference;

public class PtoCTemplate extends RtsTemplate {
	
	private ConceptComponent conceptComponent;
	private TemporalComponent temporalComponent;
	
	public PtoCTemplate() {
		this.conceptComponent = new ConceptComponent();
		this.temporalComponent = new TemporalComponent();
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
	
	public Cui getConceptCui() {
		return conceptComponent.getCui();
	}
	
	public void setConceptCui(Cui cui) {
		this.conceptComponent.setCui(cui);
	}
	
	public Iui getConceptSystemIui() {
		return conceptComponent.getConceptSystemIui();
	}
	
	public void setConceptSystemIui(Iui conceptSystemIui) {
		this.conceptComponent.setConceptSystemIui(conceptSystemIui);
	}
	
	/**
	 * deprecated.  Use getTemporalReference() instead.
	 * @return
	 */
	@Deprecated
	public Iui getTemporalEntityIui() {
		return this.temporalComponent.getTemporalEntityIui();
	}
	
	public TemporalReference getTemporalReference() {
		return this.temporalComponent.getTemporalReference();
	}
	
	/**
	 * deprecated.  Use setTemporalReference() instead.
	 * @param temporalEntityIui
	 */
	@Deprecated
	public void setTemporalEntityIui(Iui temporalEntityIui) {
		this.temporalComponent.setTemporalEntityIui(temporalEntityIui);
	}
	
	public void setTemporalReference(TemporalReference tr) {
		this.temporalComponent.setTemporalReference(tr);
	}
	
	@Override
	public boolean isPtoCTemplate() {
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PtoC< ");
		
		builder.append(this.getTemplateIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		builder.append(this.getAuthoringTimeIui());
		builder.append(", ");
		
		builder.append(this.getReferentIui());
		builder.append(", ");
		
		builder.append(this.getConceptCui());
		builder.append(", ");
		
		builder.append(this.getConceptSystemIui());
		builder.append(" >");
		
		return builder.toString();
	}
}
