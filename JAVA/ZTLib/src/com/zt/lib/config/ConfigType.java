package com.zt.lib.config;

public enum ConfigType {
	XML(".xml"),
	PROP(".properties");
	
	private String value;
	
	private ConfigType(String value)
	{
		this.value = value;
	}
	
	public String value()
	{
		return this.value;
	}
}
