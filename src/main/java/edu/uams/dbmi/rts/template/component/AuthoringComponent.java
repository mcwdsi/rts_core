package edu.uams.dbmi.rts.template.component;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;

/**
 * Represents the authoring metadata component that is a part of every referent tracking template
 * 
 * @author Josh Hanna
 *
 */
public class AuthoringComponent {
	
	/*
	 * Iui of entity that made assertion captured by template (Iui_a)
	 */
	private Iui authorIui;
	/*
	 * Timestamp of when template was inserted into RTS (t_d)
	 */
	private Iso8601DateTime authoringTimestamp;
	
	@Deprecated
	private Iui authoringTimeIui;
	/*
	 * Reference to temporal region at which author (Iui_a) made
	 * 	the assertion captured by template
	 */
	private TemporalReference authoringTimeReference;
	
	/**
	 * 
	 * @return the Iui of the author of the associated template
	 */
	public Iui getAuthorIui() {
		return authorIui;
	}

	/**
	 * sets the Iui of the author of the associated template
	 * @param authorIui
	 */
	public void setAuthorIui(Iui authorIui) {
		this.authorIui = authorIui;
	}

	/**
	 * 
	 * @return the DateTime of when the associated template was added to the system
	 */
	public Iso8601DateTime getAuthoringTimestamp() {
		return authoringTimestamp;
	}

	/**
	 * sets the DateTime of when the associated template was added to the system
	 * @param authoringTimestamp
	 */
	public void setAuthoringTimestamp(Iso8601DateTime authoringTimestamp) {
		this.authoringTimestamp = authoringTimestamp;
	}
	
	/**
	 * gets the Iui of the temporal entity where the associated template was asserted
	 * @return
	 */
	@Deprecated
	public Iui getAuthoringTimeIui() {
		return authoringTimeIui;
	}

	/**
	 * sets the Iui of the temporal entity where the associated template was asserted
	 * @param authoringTimeIui
	 */
	@Deprecated
	public void setAuthoringTimeIui(Iui authoringTimeIui) {
		this.authoringTimeIui = authoringTimeIui;
	}
	
	/**
	 * sets the TemporalReference to the time where the RT tuple was asserted
	 * 	by the entity to which the authorIui refers.
	 * 
	 * @param tr
	 */
	public void setAuthoringTimeReference(TemporalReference tr) {
		this.authoringTimeReference = tr;
	}
	
	/**
	 * gets the TemporalReference to the time where the RT tuple was asserted
	 * 	by the entity to which the authorIui refers.
	 * 
	 * @return
	 */
	public TemporalReference getAuthoringTimeReference() {
		return this.authoringTimeReference;
	}
	
	/**
	 * Creates an AuthoringComponent from (1) an IUI that denotes the entity
	 * 	who is making the assertion that corresponds to the tuple and 
	 *  (2) the dateTime that is td parameter of associated metadata (D) tuple 
	 * 
	 * @param authorIui
	 * @param dateTime
	 */
	public AuthoringComponent(Iui authorIui, Iso8601DateTime dateTime){
		this.authoringTimestamp = dateTime;
		this.authorIui = authorIui;
	}
	
	/**
	 * Default constructor that creates an empty AuthoringComponent, which
	 * 	must subsequently be configured using the set methods of this class
	 */
	public AuthoringComponent() {
	}

	
}
