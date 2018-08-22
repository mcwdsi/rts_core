package edu.uams.dbmi.rts.tuple;


public enum RtsTupleType {
	ATEMPLATE(ATuple.class),
	METADATATEMPLATE(MetadataTuple.class),
	PTODETEMPLATE(PtoDETuple.class),
	PTOLACKUTEMPLATE(PtoLackUTuple.class),
	PTOPTEMPLATE(PtoPTuple.class),
	PTOUTEMPLATE(PtoUTuple.class),
	PTOCTEMPLATE(PtoCTuple.class),
	@Deprecated
	TENTEMPLATE(TenTemplate.class),
	@Deprecated
	TETEMPLATE(TenTemplate.class);
	
	Class<?> type;
	
	private RtsTupleType(Class<?> type){
		this.type = type;
	}
	
	public Class<?> getType(){
		return type;
	}

}
