package edu.uams.dbmi.rts.template;

import java.net.URI;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.RelationshipComponent;
import edu.uams.dbmi.rts.template.component.TemporalComponent;
import edu.uams.dbmi.rts.template.component.UniversalComponent;
import edu.uams.dbmi.rts.uui.Uui;

/**
 * Template that asserts a particular (e.g. Josh Hanna) is not an instantiation of a 
 * universal (e.g. Chair).
 * @author Josh Hanna
 *
 */
public class PtoLackUTemplate extends RtsTemplate {
	
	private RelationshipComponent relationshipComponent;
	private UniversalComponent universalComponent;
	private TemporalComponent temporalComponent;
	
	public PtoLackUTemplate(){
		this.relationshipComponent = new RelationshipComponent();
		this.universalComponent = new UniversalComponent();
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
	
	public Iui getTemporalEntityIui(){
		return this.temporalComponent.getTemporalEntityIui();
	}
	
	public void setTemporalEntityIui(Iui newIui){
		this.temporalComponent.setTemporalEntityIui(newIui);
	}
	
	@Override
	public boolean isPtoLackUTemplate(){
		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PtoLackU< ");
		
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
		
		builder.append(this.getUniversalUui());
		builder.append(", ");
		
		builder.append(this.getUniversalOntologyIui());
		
		builder.append(" >");
		
		return builder.toString();
	}
}
