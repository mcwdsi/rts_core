package edu.uams.dbmi.rts;



import java.net.URI;
import java.util.List;
import java.util.Set;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.metadata.RtsChangeType;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.template.ATemplate;
import edu.uams.dbmi.rts.template.MetadataTemplate;
import edu.uams.dbmi.rts.template.PtoDETemplate;
import edu.uams.dbmi.rts.template.PtoLackUTemplate;
import edu.uams.dbmi.rts.template.PtoPTemplate;
import edu.uams.dbmi.rts.template.PtoUTemplate;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;

public class TemplateFactory {

	public ATemplate createATemplate() {
		return new ATemplate();
	}

	public ATemplate createATemplate(Iui authorIui, Iso8601DateTime timestamp,
			Iui referentIui) {
		ATemplate template = this.createATemplate();
		template.setAuthoringTimestamp(timestamp);
		template.setAuthorIui(authorIui);
		template.setReferentIui(referentIui);
		return template;
	}

	public PtoLackUTemplate createPtoLackUTemplate() {
		return new PtoLackUTemplate();
	}

	public PtoLackUTemplate createPtoLackUTemplate(Iui authorIui,
			Iui referentIui, TemporalReference assertedTimeReference, 
			URI relationshipURI,
			Iui relationshipOntologyIui, URI universalUri,
			Iui universalOntologyIui, 
			TemporalReference relevantTimeReference) {
		PtoLackUTemplate template = this.createPtoLackUTemplate();

		// setting the authoring time
		//template.setAuthoringTimeIui(assertedTimeIui);
		template.setAuthoringTimeReference(assertedTimeReference);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// setting the referent iui
		template.setReferentIui(referentIui);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the universal identifier
		template.setUniversalUui(new Uui(universalUri.toString()));

		// setting the ontology the universal is defined in
		template.setUniversalOntologyIui(universalOntologyIui);

		// setting the temporal entity during which the relationship holds
		//template.setTemporalEntityIui(relevantTimeIui);
		template.setTemporalReference(relevantTimeReference);

		return template;
	}

	public PtoPTemplate createPtoPTemplate() {
		return new PtoPTemplate();
	}

	public PtoPTemplate createPtoPTemplate(Iui authorIui, 
			TemporalReference authoringTimeReference,
			List<ParticularReference> particulars, URI relationshipURI, Iui ontologyIui,
			TemporalReference relevantTimeReference) {
		PtoPTemplate template = this.createPtoPTemplate();

		// setting the authoring time
		//template.setAuthoringTimeIui(authoringTimeIui);
		template.setAuthoringTimeReference(authoringTimeReference);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// setting the particulars
		template.setParticulars(particulars);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(ontologyIui);

		// setting the temporal entity during which the relationship holds
		//template.setTemporalEntityIui(relevantTimeIui);
		template.setAuthoringTimeReference(relevantTimeReference);

		return template;
	}

	public PtoUTemplate createPtoUTemplate() {
		return new PtoUTemplate();
	}

	public PtoUTemplate createPtoUTemplate(Iui authorIui, Iui referentIui,
			TemporalReference assertedTimeReference, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalUri,
			Iui universalOntologyIui,
			TemporalReference relevantTimeReference) {
		PtoUTemplate template = this.createPtoUTemplate();

		// setting the authoring time
		//template.setAuthoringTimeIui(assertedTimeIui);
		template.setAuthoringTimeReference(assertedTimeReference);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// setting the referent iui
		template.setReferentIui(referentIui);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the universal identifier
		template.setUniversalUui(new Uui(universalUri.toString()));

		// setting the ontology the universal is defined in
		template.setUniversalOntologyIui(universalOntologyIui);

		// setting the temporal entity during which the relationship holds
		//template.setTemporalEntityIui(relevantTimeIui);
		template.setAuthoringTimeReference(relevantTimeReference);

		return template;
	}

	public PtoDETemplate createPtoDETemplate() {
		return new PtoDETemplate();
	}

	public PtoDETemplate createPtoDETemplate(Iui authorIui, Iui referentIui,
			TemporalReference authoringTimeReference, byte[] data, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalURI,
			Iui universalOntologyIui) {
		PtoDETemplate template = this.createPtoDETemplate();

		// setting the authoring timestamp for now
		//template.setAuthoringTimeIui(authoringTimeIui);
		
		template.setAuthoringTimeReference(authoringTimeReference);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// getting an available Iui for the referent
		template.setReferent(referentIui);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the universal identifier
		template.setDatatypeUui(new Uui(universalURI));

		// setting the ontology the universal is defined in
		template.setDatatypeOntologyIui(universalOntologyIui);

		// setting the data in the template
		template.setData(data);

		return template;

	}

	public MetadataTemplate createMetadataTemplate() {
		return new MetadataTemplate();
	}

	public MetadataTemplate createMetadataTemplate(Iui authorIui,
			Iso8601DateTime timestamp, Iui referentIui, RtsChangeType type,
			RtsChangeReason reason, RtsErrorCode errorCode,
			Set<Iui> replacementTemplateIuis) {
		MetadataTemplate template = this.createMetadataTemplate();

		template.setAuthorIui(authorIui);
		template.setAuthoringTimestamp(timestamp);
		template.setChangeType(type);
		template.setChangeReason(reason);
		template.setErrorCode(errorCode);
		template.setReferent(referentIui);
		template.setReplacementTemplateIuis(replacementTemplateIuis);

		return template;
	}

}
