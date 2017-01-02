package edu.uams.dbmi.rts.template;

import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.RelationshipComponent;
import edu.uams.dbmi.rts.template.component.TemporalComponent;

/**
 * Template that relates to particulars together using a specified relationship
 * @author Josh Hanna
 *
 */
public class PtoPTemplate extends RtsTemplate {
	
	private RelationshipComponent relationshipComponent;
	private TemporalComponent temporalComponent;
	
	public PtoPTemplate(){
		this.relationshipComponent = new RelationshipComponent();
		this.temporalComponent = new TemporalComponent();
	}

	public Iui getAuthoringTimeIui(){
		return this.authoringComponent.getAuthoringTimeIui();
	}
	
	public void setAuthoringTimeIui(Iui temporalEntityIui){
		this.authoringComponent.setAuthoringTimeIui(temporalEntityIui);
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

	public Iui getTemporalEntityIui(){
		return this.temporalComponent.getTemporalEntityIui();
	}
	
	public void setTemporalEntityIui(Iui newIui){
		this.temporalComponent.setTemporalEntityIui(newIui);
	}
	
	public void addParticular(Iui particular){
		this.particularComponent.addParticular(particular);
	}
	
	public void setParticular(int index, Iui particular){
		this.particularComponent.setParticular(index, particular);
	}
	
	public List<Iui> getParticulars(){
		return this.particularComponent.getParticulars();
	}
	
	public void setParticulars(List<Iui> particulars){
		this.particularComponent.setParticulars(particulars);
	}
	
	public void clearParticulars(){
		this.particularComponent.clearParticulars();
	}
	
	public void addAllParticulars(Collection<Iui> particulars){
		this.particularComponent.addAllParticulars(particulars);
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
		
		builder.append(this.getAuthoringTimeIui());
		builder.append(", ");
		
		builder.append(this.getReferentIui());
		builder.append(", ");

		builder.append(this.getRelationshipURI());
		builder.append(", ");
		
		builder.append(this.getRelationshipOntologyIui());
		builder.append(", ");
		
		builder.append("(");
		Iterator<Iui> p = this.getParticulars().iterator();
		while(p.hasNext()) {
			builder.append(p.next());
			builder.append(", ");
		}
		builder.setLength(builder.length()-2);
		builder.append(")");
		
		builder.append(" >");
		
		return builder.toString();
	}
}
