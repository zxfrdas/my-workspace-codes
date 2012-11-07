package com.zt.lib.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.content.Context;

public class ConfigManager {

	private static volatile ConfigManager instance;
	private WeakReference<Context> mContextRef;
	private ConfigRWer mRWer;
	private String filePath;
	private ConfigType eType;

	public static ConfigManager getInstance(Context context)
	{
		if (null == instance) {
			synchronized (ConfigManager.class) {
				if (null == instance) {
					instance = new ConfigManager(context);
				}
			}
		}
		return instance;
	}
	
	private ConfigManager(Context context)
	{
		mRWer = new RWerImpl();
		mContextRef = new WeakReference<Context>(context);
	}
	
	public void loadFile(String name, ConfigType type)
	{
		eType = type;
		setFilePath(name);
		try {
			mRWer.loadFile(name, eType, mContextRef.get());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCurFilePath()
	{
		return filePath;
	}
	
	private void setFilePath(String name)
	{
		switch (eType)
		{
		case XML:
			filePath = "/data/data/" + mContextRef.get().getPackageName() + "/shared_prefs/" + name + eType.value();
			break;
			
		case PROP:
			filePath = mContextRef.get().getFilesDir().getAbsolutePath() + "/" + name + eType.value();
			break;
		}
	}

}
