package edu.uams.dbmi.rts.tuple;


public enum RtsTupleType {
	ATUPLE(ATuple.class),
	METADATATUPLE(MetadataTuple.class),
	PTODETUPLE(PtoDETuple.class),
	PTOLACKUTUPLE(PtoLackUTuple.class),
	PTOPTUPLE(PtoPTuple.class),
	PTOUTUPLE(PtoUTuple.class),
	PTOCTUPLE(PtoCTuple.class),
	@Deprecated
	TENTUPLE(TenTemplate.class),
	@Deprecated
	TETUPLE(TenTemplate.class);
	
	Class<?> type;
	
	private RtsTupleType(Class<?> type){
		this.type = type;
	}
	
	public Class<?> getType(){
		return type;
	}

}
