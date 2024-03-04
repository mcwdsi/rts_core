package edu.uams.dbmi.rts.tuple;

import java.util.Iterator;
import java.util.Set;

import edu.uams.dbmi.rts.metadata.RtsChangeType;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.tuple.component.MetadataComponent;
import edu.uams.dbmi.rts.tuple.component.ParticularComponent;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;
import edu.uams.dbmi.rts.iui.Iui;

/**
 * The Tuple that records changes to other Tuples, including all relevant
 *   provenance information.
 *   
 * The referent of a metadata Tuple is always another Tuple.  So the
 *   referent IUI should always be an IUI that denotes an RT Tuple.
 * 
 * Contains a metadata component in addition to the other components all
 *   Tuples share.
 * 
 * @author Josh Hanna
 * 
 */
public class MetadataTuple extends RtsTuple {

	/*
	 * in addition to IuiComponent, AuthoringComponent,
	 *  and ParticularComponent.
	 *  
	 *  In the case of MetadataTuple, the authorIui is 
	 *  	the IUI_d parameter, the timestamp is the t_d
	 *  	parameter and should exactly equal the timestampe
	 *  	of the associated Tuple, and the particular
	 *  	component should be a Tuple IUI of the a
	 *  	associated Tuple.
	 */
	MetadataComponent metadataComponent;
	
	ParticularComponent<Iui> particularComponent;

	public MetadataTuple() {
		this.metadataComponent = new MetadataComponent();
		this.particularComponent = new ParticularComponent<Iui>();
	}
	
	public void setReferent(Iui iui) {
		if (particularComponent.isEmpty()) {
			particularComponent.addParticular(iui);
		} else
			throw new IllegalStateException("the iui of the Tuple to which this metadata Tuple refers is already set.");
	}
	
	public Iui getReferent() {
		return particularComponent.getParticular();
	}

	/**
	 * This timestamp is the td parameter of the Tuple
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
	 * The change type is the CT parameter of the Tuple
	 * @return
	 * One of three, mutually exclusive (and we think exhaustive) values for 
	 *   types of change.
	 */
	public RtsChangeType getChangeType() {
		return metadataComponent.getType();
	}

	/**
	 * The change reason is the C parameter of the Tuple
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

	public Set<Iui> getReplacementTupleIuis() {
		return metadataComponent.getReplacementTupleIuis();
	}

	public void setReplacementTupleIuis(Set<Iui> replacements) {
		this.metadataComponent.setReplacementTupleIuis(replacements);
	}

	@Override
	public boolean isMetadataTuple() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("D< ");

		builder.append(this.getTupleIui());  // iui of Tuple
		builder.append(", ");

		builder.append(this.getAuthorIui());  // iuid
		builder.append(", ");

		Iso8601DateTimeFormatter formatter = new Iso8601DateTimeFormatter();

		builder.append(formatter.format(this.getAuthoringTimestamp()));  // td
		builder.append(", ");

		builder.append(this.getReferent());  // iuit
		builder.append(", ");

		builder.append(this.getChangeType());  // CT
		builder.append(", ");

		builder.append(this.getChangeReason()); // C
		builder.append(", ");

		builder.append(this.getErrorCode());   // E
		builder.append(", ");

		builder.append("(");
		Iterator<Iui> s = this.getReplacementTupleIuis().iterator();
		while (s.hasNext()) {
			builder.append(s.next().toString() + ", ");
		}
		builder.setLength(builder.length()-2); //hack off last comma
		builder.append(")");

		builder.append(" >");

		return builder.toString();

	}

	@Override
	public RtsTupleType getRtsTupleType() {
		return RtsTupleType.METADATATUPLE;
	}
}
