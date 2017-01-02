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
import edu.uams.dbmi.rts.template.TeTemplate;
import edu.uams.dbmi.rts.template.TenTemplate;
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
			Iui referentIui, Iui assertedTimeIui, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalUri,
			Iui universalOntologyIui, Iui relevantTimeIui) {
		PtoLackUTemplate template = this.createPtoLackUTemplate();

		// setting the authoring time
		template.setAuthoringTimeIui(assertedTimeIui);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// setting the referent iui
		template.setReferentIui(referentIui);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the univeral identifier
		template.setUniversalUui(new Uui(universalUri.toString()));

		// setting the ontology the universal is defined in
		template.setUniversalOntologyIui(universalOntologyIui);

		// setting the temporal entity during which the relationship holds
		template.setTemporalEntityIui(relevantTimeIui);

		return template;
	}

	public PtoPTemplate createPtoPTemplate() {
		return new PtoPTemplate();
	}

	public PtoPTemplate createPtoPTemplate(Iui authorIui, Iui authoringTimeIui,
			List<Iui> particulars, URI relationshipURI, Iui ontologyIui,
			Iui relevantTimeIui) {
		PtoPTemplate template = this.createPtoPTemplate();

		// setting the authoring time
		template.setAuthoringTimeIui(authoringTimeIui);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// setting the particulars
		template.setParticulars(particulars);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(ontologyIui);

		// setting the temporal entity during which the relationship holds
		template.setTemporalEntityIui(relevantTimeIui);

		return template;
	}

	public PtoUTemplate createPtoUTemplate() {
		return new PtoUTemplate();
	}

	public PtoUTemplate createPtoUTemplate(Iui authorIui, Iui referentIui,
			Iui assertedTimeIui, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalUri,
			Iui universalOntologyIui, Iui relevantTimeIui) {
		PtoUTemplate template = this.createPtoUTemplate();

		// setting the authoring time
		template.setAuthoringTimeIui(assertedTimeIui);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// setting the referent iui
		template.setReferentIui(referentIui);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the univeral identifier
		template.setUniversalUui(new Uui(universalUri.toString()));

		// setting the ontology the universal is defined in
		template.setUniversalOntologyIui(universalOntologyIui);

		// setting the temporal entity during which the relationship holds
		template.setTemporalEntityIui(relevantTimeIui);

		return template;
	}

	public TenTemplate createTenTemplate() {
		return new TenTemplate();
	}

	public TenTemplate createTenTemplate(Iui authorIui, Iui authoringTimeIui,
			Iui referentIui, String name, Iui namingSystemIui,
			Iui relevantTimeIui) {
		TenTemplate template = this.createTenTemplate();

		template.setAuthorIui(authorIui);
		template.setAuthoringTimeIui(authoringTimeIui);
		template.setName(name);
		template.setNamingSystemIui(namingSystemIui);
		template.setReferentIui(referentIui);
		template.setTemporalEntityIui(relevantTimeIui);

		return template;
	}

	public TeTemplate createTeTemplate() {
		return new TeTemplate();
	}

	public TeTemplate createTeTemplate(Iui authorIui, Iui referentIui,
			URI universalUri, Iui ontologyIui) {
		TeTemplate template = this.createTeTemplate();

		// setting the authoring timestamp for now
		template.setAuthoringTimestamp(new Iso8601DateTime());

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// getting an available Iui for the referent
		template.setReferentIui(referentIui);

		// setting the univeral identifier
		template.setUniversalUui(new Uui(universalUri));

		// setting the ontology the universal is defined in
		template.setUniversalOntologyIui(ontologyIui);

		return template;
	}

	public TeTemplate createTeTemplate(Iui authorIui,
			Iso8601DateTime timestamp, Iui referentIui, URI universalUri,
			Iui ontologyIui) {
		TeTemplate template = this.createTeTemplate();

		template.setAuthoringTimestamp(timestamp);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// getting an available Iui for the referent
		template.setReferentIui(referentIui);

		// setting the univeral identifier
		template.setUniversalUui(new Uui(universalUri));

		// setting the ontology the universal is defined in
		template.setUniversalOntologyIui(ontologyIui);

		return template;
	}

	public PtoDETemplate createPtoDETemplate() {
		return new PtoDETemplate();
	}

	public PtoDETemplate createPtoDETemplate(Iui authorIui, Iui referentIui,
			Iui authoringTimeIui, byte[] data, URI relationshipURI,
			Iui relationshipOntologyIui, URI universalURI,
			Iui universalOntologyIui) {
		PtoDETemplate template = this.createPtoDETemplate();

		// setting the authoring timestamp for now
		template.setAuthoringTimeIui(authoringTimeIui);

		// setting the author Iui
		template.setAuthorIui(authorIui);

		// getting an available Iui for the referent
		template.setReferentIui(referentIui);

		// setting the relationship URI
		template.setRelationshipURI(relationshipURI);

		// setting the ontology where the relationship is defined
		template.setRelationshipOntologyIui(relationshipOntologyIui);

		// setting the univeral identifier
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
		template.setReferentIui(referentIui);
		template.setReplacementTemplateIuis(replacementTemplateIuis);

		return template;
	}

}
