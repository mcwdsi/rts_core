package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.rts.cui.Cui;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.ConceptComponent;
import edu.uams.dbmi.rts.template.component.TemporalComponent;

public class PtoCTemplate extends RtsTemplate {
	
	private ConceptComponent conceptComponent;
	private TemporalComponent temporalComponent;
	
	public PtoCTemplate() {
		this.conceptComponent = new ConceptComponent();
		this.temporalComponent = new TemporalComponent();
	}
	
	public Iui getAuthoringTimeIui() {
		return this.authoringComponent.getAuthoringTimeIui();
	}
	
	public void setAuthoringTimeIui(Iui temporalEntityIui) {
		this.authoringComponent.setAuthoringTimeIui(temporalEntityIui);
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
	
	public Iui getTemporalEntityIui() {
		return this.temporalComponent.getTemporalEntityIui();
	}
	
	public void setTemporalEntityIui(Iui temporalEntityIui) {
		this.temporalComponent.setTemporalEntityIui(temporalEntityIui);
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
