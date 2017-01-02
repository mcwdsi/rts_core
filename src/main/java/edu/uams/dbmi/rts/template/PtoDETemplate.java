package edu.uams.dbmi.rts.template;

import java.net.URI;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.DataComponent;
import edu.uams.dbmi.rts.template.component.RelationshipComponent;
import edu.uams.dbmi.rts.template.component.UniversalComponent;
import edu.uams.dbmi.rts.uui.Uui;

/**
 * Template that associates a particular (e.g. Josh Hanna's first name) with its 
 * digital representation (e.g. the string "Josh").
 * @author 1070675
 *
 */
public class PtoDETemplate extends RtsTemplate {
	
	private DataComponent dataComponent;
	private RelationshipComponent relationshipComponent;
	private UniversalComponent datatypeComponent;
	
	public PtoDETemplate(){
		this.dataComponent = new DataComponent();
		this.relationshipComponent = new RelationshipComponent();
		this.datatypeComponent = new UniversalComponent();
	}
	
	public Iui getAuthoringTimeIui(){
		return this.authoringComponent.getAuthoringTimeIui();
	}
	
	public void setAuthoringTimeIui(Iui temporalEntityIui){
		this.authoringComponent.setAuthoringTimeIui(temporalEntityIui);
	}
	
	public byte[] getData(){
		return dataComponent.getData();
	}
	
	public void setData(byte[] newData){
		this.dataComponent.setData(newData);
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
	
	public Uui getDatatypeUui(){
		return this.datatypeComponent.getUniversalUui();
	}
	
	public void setDatatypeUui(Uui newUui){
		this.datatypeComponent.setUniversalUui(newUui);
	}
	
	public Iui getDatatypeOntologyIui(){
		return this.datatypeComponent.getOntologyIui();
	}
	
	public void setDatatypeOntologyIui(Iui newIui){
		this.datatypeComponent.setOntologyIui(newIui);
	}
	
	@Override
	public boolean isPtoDRTemplate(){
		return true;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PtoDE< ");
		
		builder.append(this.getTemplateIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		builder.append(this.getAuthoringTimeIui());
		builder.append(", ");
		
		builder.append(this.getReferentIui());
		builder.append(", ");

		builder.append(this.getData());
		builder.append(", ");
		
		builder.append(this.getRelationshipURI());
		builder.append(", ");
		
		builder.append(this.getRelationshipOntologyIui());
		builder.append(", ");
		
		builder.append(this.getDatatypeUui());
		builder.append(", ");
		
		builder.append(this.getDatatypeOntologyIui());
		
		builder.append(" >");
		
		return builder.toString();
	}
}
