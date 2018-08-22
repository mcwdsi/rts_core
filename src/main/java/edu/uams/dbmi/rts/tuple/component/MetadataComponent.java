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
	private Set<Iui> replacementTupleIuis = new HashSet<Iui>();

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
	 * @return the error code that what was wrong with the original Tuple
	 */
	public RtsErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * sets the error code describing what was wrong with the original Tuple
	 * 
	 * @param errorCode
	 */
	public void setErrorCode(RtsErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * @return the set of new Tuples that more accurately describe reality
	 */
	public Set<Iui> getReplacementTupleIuis() {
		return replacementTupleIuis;
	}

	/**
	 * adds a set of Tuples to the component that more accurately describes
	 * reality
	 * 
	 * @param replacementTuples
	 */
	public void setReplacementTupleIuis(Set<Iui> replacementTupleIuis) {
		this.replacementTupleIuis = replacementTupleIuis;
	}

	/**
	 * creates a metadata component of a Tuple where there are more than one
	 * replacement Tuples
	 * 
	 * @param reason
	 * @param errorCode
	 * @param replacementTuples
	 */
	public MetadataComponent(RtsChangeReason reason, RtsErrorCode errorCode,
			Set<Iui> replacementTupleIuis) {
		this.reason = reason;
		this.errorCode = errorCode;
		this.replacementTupleIuis.addAll(replacementTupleIuis);
	}

	/**
	 * creates a metadata component of a Tuple where there is only one
	 * replacement Tuple
	 * 
	 * @param reason
	 * @param errorCode
	 * @param replacementTuple
	 */
	public MetadataComponent(RtsChangeReason reason, RtsErrorCode errorCode,
			Iui replacementTupleIui) {
		this.reason = reason;
		this.errorCode = errorCode;
		this.replacementTupleIuis.add(replacementTupleIui);
	}

	public MetadataComponent() {

	}

}
