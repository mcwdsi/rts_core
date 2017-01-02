package edu.uams.dbmi.rts.template;

import java.util.Iterator;
import java.util.Set;

import edu.uams.dbmi.rts.metadata.RtsChangeType;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.template.component.MetadataComponent;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;
import edu.uams.dbmi.rts.iui.Iui;

/**
 * The template that records changes to other templates, including all relevant
 *   provenance information.
 *   
 * The referent of a metadata template is always another template.  So the
 *   referent IUI should always be an IUI that denotes an RT template.
 * 
 * Contains a metadata component in addition to the other components all
 *   templates share.
 * 
 * @author Josh Hanna
 * 
 */
public class MetadataTemplate extends RtsTemplate {

	/*
	 * in addition to IuiComponent, AuthoringComponent,
	 *  and ParticularComponent.
	 *  
	 *  In the case of MetadataTemplate, the authorIui is 
	 *  	the IUI_d parameter, the timestamp is the t_d
	 *  	parameter and should exactly equal the timestampe
	 *  	of the associated template, and the particular
	 *  	component should be a template IUI of the a
	 *  	associated template.
	 */
	MetadataComponent metadataComponent;

	public MetadataTemplate() {
		this.metadataComponent = new MetadataComponent();
	}

	/**
	 * This timestamp is the td parameter of the template
	 * 
	 * @return
	 * An ISO8601 formatted date/time
	 */
	public Iso8601DateTime getAuthoringTimestamp() {
		return authoringComponent.getAuthoringTimestamp();
	}

	public void setAuthoringTimestamp(Iso8601DateTime newTimestamp) {
		this.authoringComponent.setAuthoringTimestamp(newTimestamp);
	}

	/**
	 * The change type is the CT parameter of the template
	 * @return
	 * One of three, mutually exclusive (and we think exhaustive) values for 
	 *   types of change.
	 */
	public RtsChangeType getChangeType() {
		return metadataComponent.getType();
	}

	/**
	 * The change reason is the C parameter of the template
	 * @return
	 * One of four, mutually exclusive (and we think exhaustive) reasons for
	 *   change.
	 */	
	public RtsChangeReason getChangeReason() {
		return metadataComponent.getReason();
	}

	public void setChangeType(RtsChangeType type) {
		this.metadataComponent.setType(type);
	}

	public void setChangeReason(RtsChangeReason reason) {
		this.metadataComponent.setReason(reason);
	}

	public RtsErrorCode getErrorCode() {
		return metadataComponent.getErrorCode();
	}

	public void setErrorCode(RtsErrorCode errorCode) {
		this.metadataComponent.setErrorCode(errorCode);
	}

	public Set<Iui> getReplacementTemplateIuis() {
		return metadataComponent.getReplacementTemplateIuis();
	}

	public void setReplacementTemplateIuis(Set<Iui> replacements) {
		this.metadataComponent.setReplacementTemplateIuis(replacements);
	}

	@Override
	public boolean isMetadataTemplate() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("D< ");

		builder.append(this.getTemplateIui());  // iui of template
		builder.append(", ");

		builder.append(this.getAuthorIui());  // iuid
		builder.append(", ");

		Iso8601DateTimeFormatter formatter = new Iso8601DateTimeFormatter();

		builder.append(formatter.format(this.getAuthoringTimestamp()));  // td
		builder.append(", ");

		builder.append(this.getReferentIui());  // iuit
		builder.append(", ");

		builder.append(this.getChangeType());  // CT
		builder.append(", ");

		builder.append(this.getChangeReason()); // C
		builder.append(", ");

		builder.append(this.getErrorCode());   // E
		builder.append(", ");

		builder.append("(");
		Iterator<Iui> s = this.getReplacementTemplateIuis().iterator();
		while (s.hasNext()) {
			builder.append(s.next().toString() + ", ");
		}
		builder.setLength(builder.length()-2); //hack off last comma
		builder.append(")");

		builder.append(" >");

		return builder.toString();

	}
}
