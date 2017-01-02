package edu.uams.dbmi.rts.template.component;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalReference;

/**
 * This class captures the tr parameter of PtoP, PtoU, 
 * 		PtoLackU, and PtoC templates.
 * 
 * @author hoganwr
 *
 */
public class TemporalComponent {
	
	@Deprecated
	private Iui temporalEntityIui;
	
	private TemporalReference tr;
	
	/**
	 * gets the temporal entity associated with this component
	 * @return
	 */
	@Deprecated
	public Iui getTemporalEntityIui() {
		return temporalEntityIui;
	}
	
	public TemporalReference getTemporalReference() {
		return tr;
	}

	/**
	 * sets the temporal entity associated with this component
	 * @param temporalEntityIui
	 */
	@Deprecated
	public void setTemporalEntityIui(Iui temporalEntityIui) {
		this.temporalEntityIui = temporalEntityIui;
	}
	
	public void setTemporalReference(TemporalReference tr) {
		if (tr == null) throw new NullPointerException("temporal reference may not be null");
		this.tr = tr;
	}

	@Deprecated
	public TemporalComponent(Iui temporalEntityIui){
		this.temporalEntityIui = temporalEntityIui;
	}
	
	public TemporalComponent(TemporalReference tr) {
		setTemporalReference(tr);
	}
	
	public TemporalComponent() {
		
	}

}
