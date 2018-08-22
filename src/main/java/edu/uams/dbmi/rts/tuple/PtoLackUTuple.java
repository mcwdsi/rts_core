package edu.uams.dbmi.rts.tuple;

import java.net.URI;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.tuple.component.ParticularComponent;
import edu.uams.dbmi.rts.tuple.component.RelationshipComponent;
import edu.uams.dbmi.rts.tuple.component.TemporalComponent;
import edu.uams.dbmi.rts.tuple.component.UniversalComponent;
import edu.uams.dbmi.rts.uui.Uui;

/**
 * Tuple that asserts a particular (e.g. Josh Hanna) is not an instantiation of a 
 * universal (e.g. Chair).
 * @author Josh Hanna
 *
 */
public class PtoLackUTuple extends RtsTuple {
	
	private RelationshipComponent relationshipComponent;
	private UniversalComponent universalComponent;
	private TemporalComponent temporalComponent;
	private ParticularComponent<Iui> particularComponent;
	
	public PtoLackUTuple(){
		this.relationshipComponent = new RelationshipComponent();
		this.universalComponent = new UniversalComponent();
		this.temporalComponent = new TemporalComponent();
		this.particularComponent = new ParticularComponent<Iui>();
	}
	
	public void setReferentIui(Iui iui) {
		if (particularComponent.isEmpty()) {
			particularComponent.addParticular(iui);
		} else
			throw new IllegalStateException("the referent of this PtoLackU Tuple has been set already.");
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
	
	public URI getRelationshipURI(){
		return this.relationshipComponent.getRelationshipURI();
	}
	
	public void setRelationshipURI(URI newURI){
		this.relationshipComponent.setRelationshipURI(newURI);
	}
	
	public Iui getRelationshipOntologyIui(){
		return this.relationshipComponent.getOntologyIui();
	}
	
	public void setRelationshipOntologyIui(Iui newIui){
		this.relationshipComponent.setOntologyIui(newIui);
	}
	
	public Uui getUniversalUui(){
		return this.universalComponent.getUniversalUui();
	}
	
	public void setUniversalUui(Uui newUui){
		this.universalComponent.setUniversalUui(newUui);
	}
	
	public Iui getUniversalOntologyIui(){
		return this.universalComponent.getOntologyIui();
	}
	
	public void setUniversalOntologyIui(Iui newIui){
		this.universalComponent.setOntologyIui(newIui);
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
	public boolean isPtoLackUTuple(){
		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PtoLackU< ");
		
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

		builder.append(this.getRelationshipURI());
		builder.append(", ");
		
		builder.append(this.getRelationshipOntologyIui());
		builder.append(", ");
		
		builder.append(this.getUniversalUui());
		builder.append(", ");
		
		builder.append(this.getUniversalOntologyIui());
		
		builder.append(" >");
		
		return builder.toString();
	}
}
