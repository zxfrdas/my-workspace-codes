package com.zt.lib.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import com.zt.lib.StreamHelper;

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
	
	/**
	 * 载入配置文件，读取配置项。如果文件不存在，会先判断assets目录下是否存在同名目录，存在则先复制到本地再读取，
	 * 不存在则创建空文件后读取。
	 * @param name
	 * @param type
	 */
	public void loadFile(String name, ConfigType type)
	{
		eType = type;
		setFilePath(name);
		try {
			creatFileIfNotExist(name, filePath);
			mRWer.loadFile(name, eType, mContextRef.get());
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
	
	/**
	 * 如果是SharedPreferences则不作处理。如果是properties，则先判断assets文件夹是否有指定名称文件，
	 * 若有，则将文件内容写入filePath下，若没有，则在filePath下直接创建空文件。
	 * @param name 文件名
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	private void creatFileIfNotExist(String name, String filePath) throws IOException
	{
		switch (eType)
		{
		case XML:
			// need do nothing
			break;
			
		case PROP:
			if (!new File(filePath).exists()) {
				InputStream is = null;
				try {
					is = mContextRef.get().getAssets().open(name + eType.value());
				} catch (FileNotFoundException e) {
					is = null;
				}
				if (null == is) {
					mContextRef.get().openFileOutput(name + eType.value(), Context.MODE_MULTI_PROCESS
							| Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE).close();
				} else {
					StreamHelper.output(is, mContextRef.get().openFileOutput(name + eType.value(), 
							Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE
							| Context.MODE_WORLD_WRITEABLE));
				}
			}
			break;

		default:
			break;
		}
	}

}
