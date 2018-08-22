package edu.uams.dbmi.rts.tuple.component;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.uui.Uui;

public class UniversalComponent {
	
	private Uui universalUui;
	private Iui ontologyIui;
	
	/**
	 * 
	 * @return the Uui of the universal associated with this component
	 */
	public Uui getUniversalUui() {
		return universalUui;
	}

	/**
	 * sets the Uui of the universal associated with this component
	 * @param universalUui
	 */
	public void setUniversalUui(Uui universalUui) {
		this.universalUui = universalUui;
	}

	/**
	 * gets the Iui of the ontology where the universal is defined
	 * @return
	 */
	public Iui getOntologyIui() {
		return ontologyIui;
	}

	/**
	 * sets the Iui of the ontology where the universal is defined
	 * @param ontologyIui
	 */
	public void setOntologyIui(Iui ontologyIui) {
		this.ontologyIui = ontologyIui;
	}

	public UniversalComponent(Uui universalUui, Iui ontologyIui){
		this.ontologyIui = ontologyIui;
		this.universalUui = universalUui;
	}
	
	public UniversalComponent(){
		
	}

}
