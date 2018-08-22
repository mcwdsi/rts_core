package edu.uams.dbmi.rts.tuple;

import edu.uams.dbmi.rts.cui.Cui;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.tuple.component.ConceptComponent;
import edu.uams.dbmi.rts.tuple.component.ParticularComponent;
import edu.uams.dbmi.rts.tuple.component.TemporalComponent;

public class PtoCTuple extends RtsTuple {
	
	private ConceptComponent conceptComponent;
	private TemporalComponent temporalComponent;
	private ParticularComponent<Iui> particularComponent;
	
	public PtoCTuple() {
		this.conceptComponent = new ConceptComponent();
		this.temporalComponent = new TemporalComponent();
		this.particularComponent = new ParticularComponent<Iui>();
	}
	
	public void setReferentIui(Iui iui) {
		if (particularComponent.isEmpty()) {
			particularComponent.addParticular(iui);
		} else
			throw new IllegalStateException("the referent iui of this Tuple has been set already.");
	}
	
	public Iui getReferentIui() {
		return particularComponent.getParticular();
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
	public boolean isPtoCTuple() {
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PtoC< ");
		
		builder.append(this.getTupleIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		//builder.append(this.getAuthoringTimeIui());
		//builder.append(", ");
		
		builder.append(this.getAuthoringTimeReference());
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
