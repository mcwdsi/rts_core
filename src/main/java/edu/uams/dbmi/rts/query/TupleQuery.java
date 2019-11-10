package edu.uams.dbmi.rts.query;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.metadata.RtsChangeReason;
import edu.uams.dbmi.rts.metadata.RtsErrorCode;
import edu.uams.dbmi.rts.persist.RtsStore;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;

public class TupleQuery {

	private List<RtsTupleType> types = new ArrayList<RtsTupleType>();;
	private Iui referentIui = null;
	private URI relationshipURI = null;
	private Uui universalUui = null;
	private RtsStore store;
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

	public TupleQuery(RtsStore rtsStore) {
		this.store = rtsStore;
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

	public Set<RtsTuple> runQuery() throws Exception{
		Set<RtsTuple> results = new HashSet<RtsTuple>();
		
		boolean parametersMatched = false;
		if(this.parametersMatchATuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.ATUPLE));
		}

		if(this.parametersMatchMetadataTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.METADATATUPLE));
		}

		if(this.parametersMatchPtoDRTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.PTODETUPLE));
		}

		if(this.parametersMatchPtoLackUTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.PTOLACKUTUPLE));
		}

		if(this.parametersMatchPtoPTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.PTOPTUPLE));
		}

		if(this.parametersMatchPtoUTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.PTOUTUPLE));
		}

		if(this.parametersMatchTenTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.TENTUPLE));
		}

		if(this.parametersMatchTeTuple()){
			parametersMatched = true;
			results.addAll(this.store.runQuery(this, RtsTupleType.TETUPLE));
		}

		if(!parametersMatched){
			throw new Exception("Malformed query");
		} else {
			return results;
		}
	}

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
