package edu.uams.dbmi.rts.tuple.component;

import edu.uams.dbmi.rts.cui.Cui;
import edu.uams.dbmi.rts.iui.Iui;

public class ConceptComponent {
	Iui conceptSystemIui;
	Cui conceptCode;
	
	/**
	 * 
	 * @return the Cui of the concept associated with this component
	 */
	public Cui getCui() {
		return conceptCode;
	}

	/**
	 * sets the Cui of the concept associated with this component
	 * @param conceptCode
	 */
	public void setCui(Cui conceptCode) {
		this.conceptCode = conceptCode;
	}

	/**
	 * gets the Iui of the concept system where the concept is defined
	 * @return
	 */
	public Iui getConceptSystemIui() {
		return conceptSystemIui;
	}

	/**
	 * sets the Iui of the concept system where the concept is defined
	 * @param conceptSystemIui
	 */
	public void setConceptSystemIui(Iui conceptSystemIui) {
		this.conceptSystemIui = conceptSystemIui;
	}

	public ConceptComponent(Cui conceptCode, Iui conceptSystemIui){
		this.conceptSystemIui = conceptSystemIui;
		this.conceptCode = conceptCode;
	}
	
	public ConceptComponent() {
		
	}
}
