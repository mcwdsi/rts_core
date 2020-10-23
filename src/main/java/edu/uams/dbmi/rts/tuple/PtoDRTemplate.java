package edu.uams.dbmi.rts.tuple;

import java.net.URI;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.tuple.component.DataComponent;
import edu.uams.dbmi.rts.tuple.component.RelationshipComponent;
import edu.uams.dbmi.rts.tuple.component.UniversalComponent;
import edu.uams.dbmi.rts.uui.Uui;

/**
 * Template that assocates a particular (e.g. Josh Hanna's first name) with a 
 * digital representation (e.g. the string "Josh").
 * @author 1070675
 *
 * Deprecated.  Use PtoDETemplate instead.
 *
 */
@Deprecated
public class PtoDRTemplate extends RtsTuple {
	
	private DataComponent dataComponent;
	private RelationshipComponent relationshipComponent;
	private UniversalComponent datatypeComponent;
	
	public PtoDRTemplate(){
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
		builder.append("PtoDR <");
		
		builder.append(this.getTupleIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		builder.append(this.getAuthoringTimeIui());
		builder.append(", ");
		
		//builder.append(this.getReferent());
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
		
		builder.append(">");
		
		return builder.toString();
	}

	@Override
	public RtsTupleType getRtsTupleType() {
		return null;
	}

}
