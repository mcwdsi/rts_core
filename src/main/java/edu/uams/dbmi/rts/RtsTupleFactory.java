package edu.uams.dbmi.rts;



import java.net.URI;
import java.util.List;
import java.util.Set;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.metadata.RtsChangeType;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.tuple.ATuple;
import edu.uams.dbmi.rts.tuple.MetadataTuple;
import edu.uams.dbmi.rts.tuple.PtoDETuple;
import edu.uams.dbmi.rts.tuple.PtoLackUTuple;
import edu.uams.dbmi.rts.tuple.PtoPTuple;
import edu.uams.dbmi.rts.tuple.PtoUTuple;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;

public class RtsTupleFactory {

	public ATuple createAtuple() {
		return new ATuple();
	}

	public ATuple createATuple(Iui authorIui, Iso8601DateTime timestamp,
			Iui referentIui) {
		ATuple tuple = this.createAtuple();
		tuple.setAuthoringTimestamp(timestamp);
		tuple.setAuthorIui(authorIui);
		tuple.setReferentIui(referentIui);
		return tuple;
	}

	public PtoLackUTuple createPtoLackUTuple() {
		return new PtoLackUTuple();
	}

	public PtoLackUTuple createPtoLackUTuple(Iui authorIui,
			Iui referentIui, TemporalReference assertedTimeReference, 
			URI relationshipURI,
			Iui relationshipOntologyIui, URI universalUri,
			Iui universalOntologyIui, 
			TemporalReference relevantTimeReference) {
		PtoLackUTuple tuple = this.createPtoLackUTuple();

		// setting the authoring time
		//tuple.setAuthoringTimeIui(assertedTimeIui);
		tuple.setAuthoringTimeReference(assertedTimeReference);

		// setting the author Iui
		tuple.setAuthorIui(authorIui);

		// setting the referent iui
		tuple.setReferentIui(referentIui);

		// setting the relationship URI
		tuple.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		tuple.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the universal identifier
		tuple.setUniversalUui(new Uui(universalUri.toString()));

		// setting the ontology the universal is defined in
		tuple.setUniversalOntologyIui(universalOntologyIui);

		// setting the temporal entity during which the relationship holds
		//tuple.setTemporalEntityIui(relevantTimeIui);
		tuple.setTemporalReference(relevantTimeReference);

		return tuple;
	}

	public PtoPTuple createPtoPTuple() {
		return new PtoPTuple();
	}

	public PtoPTuple createPtoPtuple(Iui authorIui, 
			TemporalReference authoringTimeReference,
			List<ParticularReference> particulars, URI relationshipURI, Iui ontologyIui,
			TemporalReference relevantTimeReference) {
		PtoPTuple tuple = this.createPtoPTuple();

		// setting the authoring time
		//tuple.setAuthoringTimeIui(authoringTimeIui);
		tuple.setAuthoringTimeReference(authoringTimeReference);

		// setting the author Iui
		tuple.setAuthorIui(authorIui);

		// setting the particulars
		tuple.setParticulars(particulars);

		// setting the relationship URI
		tuple.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		tuple.setRelationshipOntologyIui(ontologyIui);

		// setting the temporal entity during which the relationship holds
		//tuple.setTemporalEntityIui(relevantTimeIui);
		tuple.setAuthoringTimeReference(relevantTimeReference);

		return tuple;
	}

	public PtoUTuple createPtoUTuple() {
		return new PtoUTuple();
	}

	public PtoUTuple createPtoUtuple(Iui authorIui, Iui referentIui,
			TemporalReference assertedTimeReference, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalUri,
			Iui universalOntologyIui,
			TemporalReference relevantTimeReference) {
		PtoUTuple tuple = this.createPtoUTuple();

		// setting the authoring time
		//tuple.setAuthoringTimeIui(assertedTimeIui);
		tuple.setAuthoringTimeReference(assertedTimeReference);

		// setting the author Iui
		tuple.setAuthorIui(authorIui);

		// setting the referent iui
		tuple.setReferentIui(referentIui);

		// setting the relationship URI
		tuple.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		tuple.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the universal identifier
		tuple.setUniversalUui(new Uui(universalUri.toString()));

		// setting the ontology the universal is defined in
		tuple.setUniversalOntologyIui(universalOntologyIui);

		// setting the temporal entity during which the relationship holds
		//tuple.setTemporalEntityIui(relevantTimeIui);
		tuple.setAuthoringTimeReference(relevantTimeReference);

		return tuple;
	}

	public PtoDETuple createPtoDEtuple() {
		return new PtoDETuple();
	}

	public PtoDETuple createPtoDEtuple(Iui authorIui, Iui referentIui,
			TemporalReference authoringTimeReference, byte[] data, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalURI,
			Iui universalOntologyIui) {
		PtoDETuple tuple = this.createPtoDEtuple();

		// setting the authoring timestamp for now
		//tuple.setAuthoringTimeIui(authoringTimeIui);
		
		tuple.setAuthoringTimeReference(authoringTimeReference);

		// setting the author Iui
		tuple.setAuthorIui(authorIui);

		// getting an available Iui for the referent
		tuple.setReferent(referentIui);

		// setting the relationship URI
		tuple.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		tuple.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the universal identifier
		tuple.setDatatypeUui(new Uui(universalURI));

		// setting the ontology the universal is defined in
		tuple.setDatatypeOntologyIui(universalOntologyIui);

		// setting the data in the tuple
		tuple.setData(data);

		return tuple;

	}

	public MetadataTuple createMetadataTuple() {
		return new MetadataTuple();
	}

	public MetadataTuple createMetadataTuple(Iui authorIui,
			Iso8601DateTime timestamp, Iui referentIui, RtsChangeType type,
			RtsChangeReason reason, RtsErrorCode errorCode,
			Set<Iui> replacementtupleIuis) {
		MetadataTuple tuple = this.createMetadataTuple();

		tuple.setAuthorIui(authorIui);
		tuple.setAuthoringTimestamp(timestamp);
		tuple.setChangeType(type);
		tuple.setChangeReason(reason);
		tuple.setErrorCode(errorCode);
		tuple.setReferent(referentIui);
		tuple.setReplacementTupleIuis(replacementtupleIuis);

		return tuple;
	}

}