package com.zt.lib.config;

import android.content.Context;

public class ConfigManager {

	private static volatile ConfigManager instance;

	public static ConfigManager getInstance(Context context)
	{
		if (null == instance) {
			synchronized (ConfigManager.class) {
				if (null == instance) {
					instance = new ConfigManager();
				}
			}
		}
		return instance;
	}

}
