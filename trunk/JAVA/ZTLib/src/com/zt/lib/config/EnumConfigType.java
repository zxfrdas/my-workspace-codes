package com.zt.lib.config;

public enum EnumConfigType {
	XML(".xml"),
	PROP(".properties");
	
	private String value;
	
	private EnumConfigType(String value)
	{
		this.value = value;
	}
	
	public String value()
	{
		return this.value;
	}
}
