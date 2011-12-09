package com.axiomalaska.hibernatetoolsdto.translator;

public class DTOType {
    private String dtoTypeName;
    private boolean exclude;
    private String getConversion;
    private String setConversion;
    
	public DTOType(String dtoTypeName, boolean exclude ) {
		super();
		this.dtoTypeName = dtoTypeName;
		this.exclude = exclude;
	}
    
	public DTOType(String dtoTypeName, boolean exclude, String getConversion, String setConversion) {
		super();
		this.dtoTypeName = dtoTypeName;
		this.exclude = exclude;
		this.getConversion = getConversion;
		this.setConversion = setConversion;
	}

	
	public String getDtoTypeName() {
		return dtoTypeName;
	}
	public void setDtoTypeName(String dtoTypeName) {
		this.dtoTypeName = dtoTypeName;
	}
	public boolean isExclude() {
		return exclude;
	}
	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}
	public String getGetConversion() {
		return getConversion;
	}
	public void setGetConversion(String getConversion) {
		this.getConversion = getConversion;
	}
	public String getSetConversion() {
		return setConversion;
	}
	public void setSetConversion(String setConversion) {
		this.setConversion = setConversion;
	}

    
}
