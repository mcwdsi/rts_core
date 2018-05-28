package edu.uams.dbmi.rts.template;


public enum RtsTemplateType {
	ATEMPLATE(ATemplate.class),
	METADATATEMPLATE(MetadataTemplate.class),
	PTODETEMPLATE(PtoDETemplate.class),
	PTOLACKUTEMPLATE(PtoLackUTemplate.class),
	PTOPTEMPLATE(PtoPTemplate.class),
	PTOUTEMPLATE(PtoUTemplate.class),
	PTOCTEMPLATE(PtoCTemplate.class),
	@Deprecated
	TENTEMPLATE(TenTemplate.class),
	@Deprecated
	TETEMPLATE(TenTemplate.class);
	
	Class<?> type;
	
	private RtsTemplateType(Class<?> type){
		this.type = type;
	}
	
	public Class<?> getType(){
		return type;
	}

}
