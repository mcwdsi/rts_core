package edu.uams.dbmi.rts.tuple.component;

import java.net.URI;

import edu.uams.dbmi.rts.iui.Iui;

public class RelationshipComponent {
	
	private URI relationshipURI;
	private Iui ontologyIui;
	
	/**
	 * 
	 * @return the URI of the relationship associated with this component
	 */
	public URI getRelationshipURI() {
		return relationshipURI;
	}

	/**
	 * sets the URI of the relationship associated with this component
	 * @param universalUui
	 */
	public void setRelationshipURI(URI relationshipURI) {
		this.relationshipURI = relationshipURI;
	}

	/**
	 * gets the Iui of the ontology where the relationship is defined
	 * @return
	 */
	public Iui getOntologyIui() {
		return ontologyIui;
	}

	/**
	 * sets the Iui of the ontology where the relationship is defined
	 * @param ontologyIui
	 */
	public void setOntologyIui(Iui ontologyIui) {
		this.ontologyIui = ontologyIui;
	}

	public RelationshipComponent(URI relationshipURI, Iui ontologyIui){
		this.ontologyIui = ontologyIui;
		this.relationshipURI = relationshipURI;
	}
	
	public RelationshipComponent(){
		
	}

}
