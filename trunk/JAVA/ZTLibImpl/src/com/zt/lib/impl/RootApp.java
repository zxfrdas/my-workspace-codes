package com.zt.lib.impl;

import com.zt.lib.config.ConfigManager;

import android.app.Application;

public class RootApp extends Application {
	
	public static ConfigManager mConfigManager;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mConfigManager = ConfigManager.getInstance(getApplicationContext());
	}
	
}
