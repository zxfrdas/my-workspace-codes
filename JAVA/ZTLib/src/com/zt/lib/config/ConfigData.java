package com.zt.lib.config;

public abstract class ConfigData implements Cloneable {

	@Override
	protected ConfigData clone()
	{
		ConfigData o = null;
		try {
			o = (ConfigData) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
}
