package edu.uams.dbmi.rts.template.component;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;

/**
 * Represents the authoring metadata component that is a part of every referent tracking template
 * 
 * @author Josh Hanna
 *
 */
public class AuthoringComponent {
	
	private Iui authorIui;
	private Iso8601DateTime authoringTimestamp;
	private Iui authoringTimeIui;
	
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
	 * gets the Iui of the temporal entity when the associated template asserted
	 * @return
	 */
	public Iui getAuthoringTimeIui() {
		return authoringTimeIui;
	}

	/**
	 * sets the Iui of the temporal entity when the associated template asserted
	 * @param authoringTimeIui
	 */
	public void setAuthoringTimeIui(Iui authoringTimeIui) {
		this.authoringTimeIui = authoringTimeIui;
	}

	public AuthoringComponent(Iui authorIui, Iso8601DateTime dateTime){
		this.authoringTimestamp = dateTime;
		this.authorIui = authorIui;
	}
	
	public AuthoringComponent() {
	}

	
}
