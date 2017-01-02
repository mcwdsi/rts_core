package edu.uams.dbmi.rts.template.component;

import edu.uams.dbmi.rts.iui.Iui;

public class TemporalComponent {
	private Iui temporalEntityIui;
	
	/**
	 * gets the temporal entity associated with this component
	 * @return
	 */
	public Iui getTemporalEntityIui() {
		return temporalEntityIui;
	}

	/**
	 * sets the temportal entity associated with this component
	 * @param temporalEntityIui
	 */
	public void setTemporalEntityIui(Iui temporalEntityIui) {
		this.temporalEntityIui = temporalEntityIui;
	}

	public TemporalComponent(Iui temporalEntityIui){
		this.temporalEntityIui = temporalEntityIui;
	}
	
	public TemporalComponent(){
		
	}

}
