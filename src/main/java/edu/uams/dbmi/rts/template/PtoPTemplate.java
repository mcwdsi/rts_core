package edu.uams.dbmi.rts.template;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.ParticularComponent;
import edu.uams.dbmi.rts.template.component.RelationshipComponent;
import edu.uams.dbmi.rts.template.component.TemporalComponent;
import edu.uams.dbmi.rts.time.TemporalReference;

/**
 * Template that relates to particulars together using a specified relationship
 * @author Josh Hanna
 *
 */
public class PtoPTemplate extends RtsTemplate {
	
	private RelationshipComponent relationshipComponent;
	private TemporalComponent temporalComponent;
	private ParticularComponent<ParticularReference> particularComponent; 
	
	public PtoPTemplate(){
		this.relationshipComponent = new RelationshipComponent();
		this.temporalComponent = new TemporalComponent();
		this.particularComponent = new ParticularComponent<ParticularReference>();
	}
	
	public void setReferent(ParticularReference pr) {
		particularComponent.addParticular(pr);
	}
	
	public ParticularReference getReferent() {
		return particularComponent.getParticular();
	}
	
	public List<ParticularReference> getAllParticulars() {
		return particularComponent.getParticulars();
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
	
	public void addParticular(ParticularReference particular){
		this.particularComponent.addParticular(particular);
	}
	
	public void setParticular(int index, ParticularReference particular){
		this.particularComponent.setParticular(index, particular);
	}
	
	public void setParticulars(List<ParticularReference> particulars){
		this.particularComponent.addAllParticulars(particulars);
	}
	
	public void clearParticulars(){
		this.particularComponent.clearParticulars();
	}
	
	@Override
	public boolean isPtoPTemplate(){
		return true;
	}
	

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PtoP< ");
		
		builder.append(this.getTemplateIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		//builder.append(this.getAuthoringTimeIui());
		//builder.append(", ");
		
		builder.append(this.getAuthoringTimeReference());
		builder.append(", ");
		
		builder.append(this.getReferent());
		builder.append(", ");

		builder.append(this.getRelationshipURI());
		builder.append(", ");
		
		builder.append(this.getRelationshipOntologyIui());
		builder.append(", ");
		
		builder.append("(");
		Iterator<ParticularReference> p = this.getAllParticulars().iterator();
		while(p.hasNext()) {
			builder.append(p.next().toString());
			builder.append(", ");
		}
		builder.setLength(builder.length()-2);
		builder.append(")");
		
		builder.append(" >");
		
		return builder.toString();
	}
}
