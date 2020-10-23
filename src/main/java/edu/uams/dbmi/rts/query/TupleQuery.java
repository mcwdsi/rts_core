package edu.uams.dbmi.rts.query;

import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.tuple.ATuple;
import edu.uams.dbmi.rts.tuple.MetadataTuple;
import edu.uams.dbmi.rts.tuple.PtoCTuple;
import edu.uams.dbmi.rts.tuple.PtoDETuple;
import edu.uams.dbmi.rts.tuple.PtoLackUTuple;
import edu.uams.dbmi.rts.tuple.PtoPTuple;
import edu.uams.dbmi.rts.tuple.PtoUTuple;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;

public class TupleQuery {

	private HashSet<RtsTupleType> types = new HashSet<RtsTupleType>();
	private Iui referentIui = null;
	private URI relationshipURI = null;
	private Uui universalUui = null;
	private Iui authorIui = null;
	private Iui authoringTimeIui = null;
	private Iso8601DateTime beginTimestamp = null;
	private Iso8601DateTime endTimestamp = null;
	private byte[] data = null;
	private Uui datatype = null;
	private RtsChangeReason changeReason = null;
	private RtsErrorCode errorCode = null;
	private Iui namingSystem = null;
	private String temporalEntityName = null;
	private TemporalReference tr;
	private List<ParticularReference> p;

	public TupleQuery() {

	}
	
	public void setP(List<ParticularReference> p) {
		this.p = p;
	}
	
	/*
	 * Later, we need to let the user specify the position at which each
	 * 	particular reference appears in the list.  For now, we're just 
	 * 	going to return true if each particular reference provided here
	 *  is in p parameter of the PtoP tuple.
	 */
	public Iterator<ParticularReference> iteratorP() {
		if (p!=null) {
			return p.iterator();
		} else {
			return null;
		}
	}
	
	public void setTemporalReference(TemporalReference tr) {
		this.tr = tr;
	}
	
	public TemporalReference getTemporalReference() {
		return tr;
	}
	
	public boolean matches(RtsTuple t) {
		boolean matches = true;
		matches = matches && (types.isEmpty() || types.contains(t.getRtsTupleType()));
		matches = matches && (authorIui == null || t.getAuthorIui().equals(authorIui));
		//System.out.println("types and author meet criteria");
		if (matches) 
			matches = matches && matchTupleTypeSpecificParameters(t);
		
		return matches;
	}
	
	private boolean matchTupleTypeSpecificParameters(RtsTuple t) {
		if (t instanceof ATuple) {
			ATuple at = (ATuple)t;
			return matchATuple(at);
		} else if (t instanceof PtoCTuple) {
			PtoCTuple ptc = (PtoCTuple)t;
			//TODO
		} else if (t instanceof PtoDETuple) {
			PtoDETuple ptd = (PtoDETuple)t;
			return matchPtoDETuple(ptd);
		} else if (t instanceof PtoLackUTuple) {
			PtoLackUTuple ptlu = (PtoLackUTuple)t;
			//TODO
		} else if (t instanceof PtoPTuple) {
			PtoPTuple ptp = (PtoPTuple)t;
			return matchPtoPTuple(ptp);
		} else if (t instanceof PtoUTuple) {
			PtoUTuple ptu = (PtoUTuple)t;
			return matchPtoUTuple(ptu);
		} else if (t instanceof MetadataTuple) {
			MetadataTuple mt = (MetadataTuple)t;
			//TODO
		}
		return false;
	}

	private boolean matchATuple(ATuple at) {
		//If we get here we already matched author IUI
		//So we only need to check timestamp and referent IUI
		boolean matches = (referentIui == null || at.getReferentIui().equals(referentIui));
		matches = matches && (beginTimestamp == null || at.getAuthoringTimestamp().equals(beginTimestamp));
		return matches;
	}
	
	private boolean matchPtoDETuple(PtoDETuple ptd) {
		//System.out.println("Comparing PtoDE tuple to query");
		ParticularReference pr = ptd.getReferent();
		boolean matches;
		if (pr instanceof TemporalReference) {
			TemporalReference tr = (TemporalReference)pr;
			matches = (tr == null || tr.equals(this.tr));
		} else {
			Iui iui = (Iui)pr;
			matches = (referentIui == null || iui.equals(referentIui));
		}
	
		if (matches && this.data != null) {
			//System.out.println("Comparing PtoDE tuple data.");
			byte[] ptdData = ptd.getData();
			matches = matches && (ptdData.length == data.length);
			if (matches) {
				for (int i=0; i<ptdData.length; i++) {
					matches = matches && (ptdData[i] == data[i]);
				}
			}
		}
		
		if (matches && this.datatype != null) {
			matches = matches && this.datatype.equals(ptd.getDatatypeUui());
		}
		
		if (matches && this.relationshipURI != null) {
			matches = matches && this.relationshipURI.equals(ptd.getRelationshipURI());
		}
		
		//TODO can also match on ontology IUIs
		//System.out.println("tuple is a match? " + matches);
		return matches;
	}
	
	private boolean matchPtoUTuple(PtoUTuple ptu) {
		boolean matches = true;
		if (this.referentIui != null) {
			matches = matches && this.referentIui.equals(ptu.getReferentIui());
		}
		if (this.universalUui != null) {
			matches = matches && this.universalUui.equals(ptu.getUniversalUui());
		}
		if (this.relationshipURI != null) {
			matches = matches && this.relationshipURI.equals(ptu.getRelationshipURI());
		}
		
		//TODO can also match on universal ontology IUI, relationship ontology IUI
		//TODO can also match on ta
		//TODO can also match on tr
		//TODO can also match on relationship polarity
		
		return matches;
	}
	
	private boolean matchPtoPTuple(PtoPTuple ptp) {
		boolean matches = true;
		if (this.relationshipURI != null) {
			matches = matches && this.relationshipURI.equals(ptp.getRelationshipURI());
		}
		if (this.p != null) {
			Iterator<ParticularReference> j = ptp.getAllParticulars().iterator();
			while (j.hasNext()) {
				matches = matches && this.p.contains(j.next());
			}
		}
		//TODO can also match on relationship ontology IUI
		//TODO can also match on ta
		//TODO can also match on tr
		//TODO can also match on relationship polarity	
	
		return matches;
	}

	public Iterator<RtsTupleType> getTypes() {
		return types.iterator();
	}

	public void addType(RtsTupleType tupleType){
		this.types.add(tupleType);
	}

	public void removeType(RtsTupleType tupleType){
		this.types.remove(tupleType);
	}

	public Iui getReferentIui() {
		return referentIui;
	}

	public void setReferentIui(Iui referentIui) {
		this.referentIui = referentIui;
	}

	public URI getRelationshipURI() {
		return relationshipURI;
	}

	public void setRelationshipURI(URI relationshipURI) {
		this.relationshipURI = relationshipURI;
	}

	public Uui getUniversalUui() {
		return universalUui;
	}

	public void setUniversalUui(Uui universalUui) {
		this.universalUui = universalUui;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Uui getDatatypeUui() {
		return datatype;
	}

	public void setDatatypeUui(Uui datatype) {
		this.datatype = datatype;
	}

	public RtsChangeReason getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(RtsChangeReason changeReason) {
		this.changeReason = changeReason;
	}

	public RtsErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(RtsErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public Iui getNamingSystemIui() {
		return namingSystem;
	}

	public void setNamingSystemIui(Iui namingSystem) {
		this.namingSystem = namingSystem;
	}

	public String getTemporalEntityName() {
		return temporalEntityName;
	}

	public void setTemporalEntityName(String temporalEntityName) {
		this.temporalEntityName = temporalEntityName;
	}

	public Iui getAuthorIui() {
		return authorIui;
	}

	/**
	 * For ATuple use beginTimestamp to match authoring timestamp
	 * For PtoDETuple use beginTimestamp to match particulars that are temporal regions and have a timestamp as identifier
	 * For PtoPTuple...
	 * @return
	 */
	public Iso8601DateTime getBeginTimestamp() {
		return beginTimestamp;
	}

	public Iso8601DateTime getEndTimestamp() {
		return endTimestamp;
	}

	public void setAuthorIui(Iui iui){
		this.authorIui = iui;
	}

	public void setBeginTimestamp(Iso8601DateTime timestamp){
		this.beginTimestamp = timestamp;
	}

	public void setEndTimestamp(Iso8601DateTime timestamp){
		this.endTimestamp = timestamp;
	}

	public Iui getAuthoringTimeIui() {
		return authoringTimeIui;
	}

	public void setAuthoringTimeIui(Iui authoringTimeIui) {
		this.authoringTimeIui = authoringTimeIui;
	}

	@Deprecated
	private boolean parametersMatchTeTuple() {
		if(this.types.contains(RtsTupleType.TETUPLE) || this.types.isEmpty()){
			if(this.relationshipURI != null){
				return false;
			} else if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(universalUui != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}

	@Deprecated
	private boolean parametersMatchTenTuple() {
		if(this.types.contains(RtsTupleType.TENTUPLE) || this.types.isEmpty()){
			if(this.relationshipURI != null){
				return false;
			} else if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(universalUui != null){
				return false;
			} else if(namingSystem != null){
				return true;
			} else if(temporalEntityName != null){
				return true;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean parametersMatchPtoUTuple() {
		if(this.types.contains(RtsTupleType.PTOUTUPLE) || this.types.isEmpty()){
			if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(this.relationshipURI != null){
				return true;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(universalUui != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean parametersMatchPtoPTuple() {
		if(this.types.contains(RtsTupleType.PTOPTUPLE) || this.types.isEmpty()){
			if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(universalUui != null){
				return false;
			} else if(this.relationshipURI != null){
				return true;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean parametersMatchPtoLackUTuple() {
		if(this.types.contains(RtsTupleType.PTOLACKUTUPLE) || this.types.isEmpty()){
			if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(this.relationshipURI != null){
				return true;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(universalUui != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean parametersMatchPtoDRTuple() {
		if(this.types.contains(RtsTupleType.PTODETUPLE) || this.types.isEmpty()){
			if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(universalUui != null){
				return false;
			} else if(this.relationshipURI != null){
				return true;
			} else if(data != null){
				return true;
			} else if(datatype != null){
				return true;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}

	private boolean parametersMatchMetadataTuple() {
		if(this.types.contains(RtsTupleType.METADATATUPLE) || this.types.isEmpty()){
			if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(this.relationshipURI != null){
				return false;
			} else if(universalUui != null){
				return false;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(authorIui != null){
				return true;
			} else if(changeReason != null){
				return true;
			} else if(errorCode != null){
				return true;
			} 
			return true;
		}
		return false;
	}

	private boolean parametersMatchATuple() {
		if(this.types.contains(RtsTupleType.ATUPLE) || this.types.isEmpty()){
			if(this.relationshipURI != null){
				return false;
			} else if(data != null){
				return false;
			} else if(datatype != null){
				return false;
			} else if(changeReason != null){
				return false;
			} else if(errorCode != null){
				return false;
			} else if(namingSystem != null){
				return false;
			} else if(temporalEntityName != null){
				return false;
			} else if(universalUui != null){
				return false;
			} else if(referentIui != null){
				return true;
			} else if(beginTimestamp != null){
				return true;
			} else if(endTimestamp != null){
				return true;
			} else if(authorIui != null){
				return true;
			}
			return true;
		}
		return false;
	}
}
