package edu.uams.dbmi.rts.template;


public enum RtsTemplateType {
	ATEMPLATE(ATemplate.class),
	METADATATEMPLATE(MetadataTemplate.class),
	PTODETEMPLATE(PtoDETemplate.class),
	PTOLACKUTEMPLATE(PtoDETemplate.class),
	PTOPTEMPLATE(PtoPTemplate.class),
	PTOUTEMPLATE(PtoDETemplate.class),
	TENTEMPLATE(PtoDETemplate.class),
	TETEMPLATE(PtoDETemplate.class);
	
	Class<?> type;
	
	private RtsTemplateType(Class<?> type){
		this.type = type;
	}
	
	public Class<?> getType(){
		return type;
	}

}
