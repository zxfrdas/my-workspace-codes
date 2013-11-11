package com.zt.lib.util;

public class Tools {

	public static boolean isStringNullOrEmpty(String str)
	{
		return (null == str) || (null != str && str.isEmpty());
	}
	
}
