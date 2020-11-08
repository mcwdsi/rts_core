package edu.uams.dbmi.rts;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uams.dbmi.rts.cui.Cui;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.metadata.RtsChangeType;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.tuple.ATuple;
import edu.uams.dbmi.rts.tuple.MetadataTuple;
import edu.uams.dbmi.rts.tuple.PtoCTuple;
import edu.uams.dbmi.rts.tuple.PtoDETuple;
import edu.uams.dbmi.rts.tuple.PtoLackUTuple;
import edu.uams.dbmi.rts.tuple.PtoPTuple;
import edu.uams.dbmi.rts.tuple.PtoUTuple;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.component.RelationshipPolarity;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601DateParseException;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeParser;
import edu.uams.dbmi.util.iso8601.Iso8601TimeParseException;
import edu.ufl.ctsi.rts.text.RtsTupleTextWriter;

public class RtsTupleFactory {
	
	protected Iso8601DateTimeParser dt_parser;
	
	public RtsTupleFactory() {
		dt_parser = new Iso8601DateTimeParser();
	}

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
	
	public RtsDeclaration buildRtsTupleOrTemporalRegion(List<String> tupleBlockFields, List<String> contentBlockFields) {
		
		String tupleType = tupleBlockFields.get(0);
		RtsDeclaration Tuple = null;
		
		if (tupleType.equals("A")) {
			Tuple = new ATuple();		
		} else if (tupleType.equals("U")) {
			Tuple = new PtoUTuple();
		} else if (tupleType.equals("P")) {
			Tuple = new PtoPTuple();
		} else if (tupleType.equals("L")) {
			Tuple = new PtoLackUTuple();
		} else if (tupleType.equals("C")) {
			Tuple = new PtoCTuple();
		} else if (tupleType.equals("E")) {
			Tuple = new PtoDETuple();
		} else if (tupleType.equals("D")) {
			Tuple = new MetadataTuple();
		} else if (tupleType.equals("T")) {
			Tuple = createTemporalRegion(contentBlockFields);
		} else {
			throw new RuntimeException("Unrecognized tuple type: " + tupleType);
		}
		
		if (Tuple != null && Tuple instanceof RtsTuple) {
			RtsTuple tup = (RtsTuple)Tuple;
			tup.setTupleIui(Iui.createFromString(tupleBlockFields.get(1)));
			tup.setAuthorIui(Iui.createFromString(contentBlockFields.get(0)));
			populateTuple(tup, contentBlockFields);
		}
		
		return Tuple;
		
	}
		
	protected void populateTuple(RtsTuple t, List<String> contentFields) {
		if (t instanceof ATuple) {
			populateATuple((ATuple)t, contentFields);
		} else if (t instanceof PtoUTuple) {
			populatePtoUTuple((PtoUTuple)t, contentFields);
		} else if (t instanceof PtoPTuple) {
			populatePtoPTuple((PtoPTuple)t, contentFields);
		} else if (t instanceof PtoLackUTuple) {
			populatePtoLackUTuple((PtoLackUTuple)t, contentFields);
		} else if (t instanceof PtoCTuple) {
			populatePtoCTuple((PtoCTuple)t, contentFields);
		} else if (t instanceof PtoDETuple) {
			populatePtoDETuple((PtoDETuple)t, contentFields);
		} else if (t instanceof MetadataTuple) {
			populateMetadataTuple((MetadataTuple)t, contentFields);
		}
	}

	private void populateATuple(ATuple t, List<String> contentFields) {
		// set the referent IUI - IUIp
		t.setReferentIui(Iui.createFromString(contentFields.get(2)));
		try {
			//set the authoring timestamp - ta
			t.setAuthoringTimestamp(dt_parser.parse(contentFields.get(1)));
		} catch (Iso8601DateParseException e) {
			e.printStackTrace();
		} catch (Iso8601TimeParseException e) {
			e.printStackTrace();
		}
	}

	private void populatePtoUTuple(PtoUTuple t, List<String> contentFields) {
		
		//contentFields.get(1) is ta
		TemporalReference tr = new TemporalReference(contentFields.get(1), contentFields.get(1).contains("Z"));
		t.setAuthoringTimeReference(tr);
		
		//contentFields.get(2) is r
		if (contentFields.get(2).startsWith("-!-")) {
			t.setRelationshipURI(URI.create(contentFields.get(2).substring(contentFields.get(2).indexOf("-!-")+3)));
			t.setRelationshipPolarity(RelationshipPolarity.NEGATED);
		} else {
			t.setRelationshipURI(URI.create(contentFields.get(2)));
			t.setRelationshipPolarity(RelationshipPolarity.AFFIRMATIVE);
		}
		
		//contentFields.get(3) is IUIo for r
		t.setRelationshipOntologyIui(Iui.createFromString(contentFields.get(3)));
		
		//contentFields.get(4) is IUIp
		t.setReferentIui(Iui.createFromString(contentFields.get(4)));
		
		//contentFields.get(5) is UUI
		t.setUniversalUui(new Uui(contentFields.get(5)));
		
		//contentFields.get(6) is IUIo for UUI
		t.setUniversalOntologyIui(Iui.createFromString(contentFields.get(6)));
		
		//contentFields.get(7) is tr
		t.setTemporalReference(new TemporalReference(contentFields.get(7), contentFields.get(7).contains("Z")));

	}

	private void populatePtoPTuple(PtoPTuple t, List<String> contentFields) {
		
		//contentFields.get(1) is ta
		TemporalReference tr = new TemporalReference(contentFields.get(1), contentFields.get(1).contains("Z"));
		t.setAuthoringTimeReference(tr);
		
		//contentFields.get(2) is r
		if (contentFields.get(2).startsWith("-!-")) {
			t.setRelationshipURI(URI.create(contentFields.get(2).substring(contentFields.get(2).indexOf("-!-")+3)));
			t.setRelationshipPolarity(RelationshipPolarity.NEGATED);
		} else {
			t.setRelationshipURI(URI.create(contentFields.get(2)));
			t.setRelationshipPolarity(RelationshipPolarity.AFFIRMATIVE);
		}
		
		//contentFields.get(3) is IUIo for r
		t.setRelationshipOntologyIui(Iui.createFromString(contentFields.get(3)));
		
		//contentFields.get(4) is P
		String[] pRefs = contentFields.get(4).split(Pattern.quote(""+RtsTupleTextWriter.SUBFIELD_DELIM));
		for (String pRef : pRefs) {
			String[] refInfo = pRef.split(Pattern.quote("="));
			if (refInfo[0].equals("iui")) {
				Iui iui = Iui.createFromString(refInfo[1]);
				t.addParticular(iui);
			} else if (refInfo[0].equals("tref")) {
				TemporalReference tref = new TemporalReference(refInfo[1], refInfo[1].contains("Z"));
				t.addParticular(tref);
			} else {
				throw new IllegalArgumentException("particular reference type must be 'iui' or 'tref'");
			}
		}
					
		//contentFields.get(5) is tr
		t.setTemporalReference(new TemporalReference(contentFields.get(5), contentFields.get(5).contains("Z")));

	}

	private void populatePtoLackUTuple(PtoLackUTuple t, List<String> contentFields) {
		
		//contentFields.get(1) is ta
		TemporalReference tr = new TemporalReference(contentFields.get(1), contentFields.get(1).contains("Z"));
		t.setAuthoringTimeReference(tr);
		
		//contentFields.get(2) is r
		t.setRelationshipURI(URI.create(contentFields.get(2)));
		
		//contentFields.get(3) is IUIo for r
		t.setRelationshipOntologyIui(Iui.createFromString(contentFields.get(3)));
		
		//contentFields.get(4) is IUIp
		t.setReferentIui(Iui.createFromString(contentFields.get(4)));
		
		//contentFields.get(5) is UUI 
		t.setUniversalUui(new Uui(contentFields.get(5)));
		
		//contentFields.get(6) is IUIo for UUI
		t.setUniversalOntologyIui(Iui.createFromString(contentFields.get(6)));
		
		//contentFields.get(7) is tr
		t.setTemporalReference(new TemporalReference(contentFields.get(7), contentFields.get(7).contains("Z")));
		
	}

	private void populatePtoCTuple(PtoCTuple t, List<String> contentFields) {
		
		//contentFields.get(1) is ta
		TemporalReference tr = new TemporalReference(contentFields.get(1), contentFields.get(1).contains("Z"));
		t.setAuthoringTimeReference(tr);
		
		//contentFields.get(2) is IUIc
		t.setConceptSystemIui(Iui.createFromString(contentFields.get(2)));
		
		//contentFields.get(3) is IUIp
		t.setReferentIui(Iui.createFromString(contentFields.get(3)));
		
		//contentFields.get(4) is UUI 
		t.setConceptCui(new Cui(contentFields.get(4)));
		
		//contentFields.get(5) is tr
		t.setTemporalReference(new TemporalReference(contentFields.get(5), contentFields.get(5).contains("Z")));
	}

	private void populatePtoDETuple(PtoDETuple t, List<String> contentFields) {
		
		//contentFields.get(1) is ta
		TemporalReference tr = new TemporalReference(contentFields.get(1), contentFields.get(1).contains("Z"));
		t.setAuthoringTimeReference(tr);
		
		//contentFields.get(2) is r
		t.setRelationshipURI(URI.create(contentFields.get(2)));
		
		//contentFields.get(3) is IUIo for r
		t.setRelationshipOntologyIui(Iui.createFromString(contentFields.get(3)));
		
		//contentFields.get(4) is IUIp
		String[] refInfo = contentFields.get(4).split(Pattern.quote("="));
		if (refInfo[0].equals("iui")) {
			Iui iui = Iui.createFromString(refInfo[1]);
			t.setReferent(iui);
		} else if (refInfo[0].equals("tref")) {
			TemporalReference tref = new TemporalReference(refInfo[1], refInfo[1].contains("Z"));
			t.setReferent(tref);
		} else {
			throw new IllegalArgumentException("particular reference type must be 'iui' or 'tref'");
		}
		
		//contentFields.get(5) is datatype UUI 
		t.setDatatypeUui(new Uui(contentFields.get(5)));
		
		//contentFields.get(6) is IUIo for datatype UUI
		t.setDatatypeOntologyIui(Iui.createFromString(contentFields.get(6)));
		
		//contentFields.get(7) is naming system IUI
		t.setNamingSystem(Iui.createFromString(contentFields.get(7)));
		
		//contentFields.get(8) is data
		t.setData(contentFields.get(8).getBytes());
		
	}

	private void populateMetadataTuple(MetadataTuple t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
		//contentFields.get(1) is td
		try {
			t.setAuthoringTimestamp(dt_parser.parse(contentFields.get(1)));
		} catch (Iso8601DateParseException | Iso8601TimeParseException e) {
			e.printStackTrace();
		}
		
		//contentFields.get(2) is Tuple IUI
		t.setReferent(Iui.createFromString(contentFields.get(2)));
		
		//contentFields.get(3) is change type
		RtsChangeType ct = RtsChangeType.valueOf(contentFields.get(3));
		t.setChangeType(ct);
		
		//contentFields.get(4) is change reason
		RtsChangeReason cr = RtsChangeReason.valueOf(contentFields.get(4));
		t.setChangeReason(cr);
		
		//contentFields.get(5) is error code
		RtsErrorCode ec = RtsErrorCode.valueOf(contentFields.get(5));
		t.setErrorCode(ec);
		
		//contentFields.get(6) is list of replacement Tuples
		String replTupleField = contentFields.get(6);
		if (replTupleField.length() > 0) {
			String[] replTupleIuis = replTupleField.split(Pattern.quote(""+RtsTupleTextWriter.SUBFIELD_DELIM));
			HashSet<Iui> replIuis = new HashSet<Iui>();
			for (String replTupleIui : replTupleIuis) {
				Iui replIui = Iui.createFromString(replTupleIui);
				replIuis.add(replIui);
			}
			if (replIuis.size() > 0) t.setReplacementTupleIuis(replIuis);
		}
	}
	
	private TemporalRegion createTemporalRegion(List<String> contentFields) {
		/* 2 - last thing in content fields is IUI of naming system
		 * 	This must be: 
		 *     1. D4AF5C9A-47BA-4BF4-9BAE-F13A8ED6455E if it's an ISO8601 date/time, and possibly even 
		 *     		any Gregorian Calendar system.
		 *     2. DB2282A4-631F-4D2C-940F-A220C496F6BE if it's a generic temporal reference that refers to 
		 *     	    some time interval either whose boundaries are not known or that spans but does not 
		 *          equate to intervals named in ISO8601 or the Gregorian more generally.
		 *     3. Some other IUI that refers to some calendaring or other naming system for temporal
		 *          regions.  Could be Hebrew calendar, Julian calendar, for example.
		 */
		if (contentFields.size()<3) {
			System.err.println("Too few content fields: " + contentFields.size());
			for (String s:contentFields) {
				System.err.println("\t" + s);
			}
		}
		String nsIuiTxt = contentFields.get(2);
		Iui nsIui = Iui.createFromString(nsIuiTxt);	
		
		// 0 - first thing in content fields is the temporal reference for the region
		String tRefTxt = contentFields.get(0);
		TemporalReference tref = new TemporalReference(tRefTxt, nsIui.equals(TemporalRegion.ISO_IUI));
		
		// 1 - next thing in content fields is UUI -- almost always will BFO IRI -- for type of region
		String typeTxt = contentFields.get(1);
		Uui typeUui = new Uui(typeTxt);
				
		return new TemporalRegion(tref, typeUui, nsIui);
	}

}