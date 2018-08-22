package edu.uams.dbmi.rts.tuple.component;

import java.util.HashSet;
import java.util.Set;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.metadata.RtsChangeType;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;

public class MetadataComponent {

	private RtsChangeType type;
	private RtsChangeReason reason;
	private RtsErrorCode errorCode;
	private Set<Iui> replacementTemplateIuis = new HashSet<Iui>();

	/**
	 * 
	 * @return The type of the change (insertion, invalidation, revalidation)
	 */
	public RtsChangeType getType() {
		return type;
	}

	/**
	 * sets the type for the change (insertion, etc)
	 * 
	 * @param type
	 */
	public void setType(RtsChangeType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The reason for the change (An, Un, Pn)
	 */
	public RtsChangeReason getReason() {
		return reason;
	}

	/**
	 * sets the reason for the change (insertion, etc)
	 * 
	 * @param reason
	 */
	public void setReason(RtsChangeReason reason) {
		this.reason = reason;
	}

	/**
	 * 
	 * @return the error code that what was wrong with the original template
	 */
	public RtsErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * sets the error code describing what was wrong with the original template
	 * 
	 * @param errorCode
	 */
	public void setErrorCode(RtsErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @return the set of new templates that more accurately describe reality
	 */
	public Set<Iui> getReplacementTemplateIuis() {
		return replacementTemplateIuis;
	}

	/**
	 * adds a set of templates to the component that more accurately describes
	 * reality
	 * 
	 * @param replacementTemplates
	 */
	public void setReplacementTemplateIuis(Set<Iui> replacementTemplateIuis) {
		this.replacementTemplateIuis = replacementTemplateIuis;
	}

	/**
	 * creates a metadata component of a template where there are more than one
	 * replacement templates
	 * 
	 * @param reason
	 * @param errorCode
	 * @param replacementTemplates
	 */
	public MetadataComponent(RtsChangeReason reason, RtsErrorCode errorCode,
			Set<Iui> replacementTemplateIuis) {
		this.reason = reason;
		this.errorCode = errorCode;
		this.replacementTemplateIuis.addAll(replacementTemplateIuis);
	}

	/**
	 * creates a metadata component of a template where there is only one
	 * replacement template
	 * 
	 * @param reason
	 * @param errorCode
	 * @param replacementTemplate
	 */
	public MetadataComponent(RtsChangeReason reason, RtsErrorCode errorCode,
			Iui replacementTemplateIui) {
		this.reason = reason;
		this.errorCode = errorCode;
		this.replacementTemplateIuis.add(replacementTemplateIui);
	}

	public MetadataComponent() {

	}

}
