package com.zt.lib.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;

import android.content.Context;

import com.zt.lib.ObjectHelper;
import com.zt.lib.StreamHelper;

/**
 * 配置文件管理类。需要在assets目录存放默认的配置文件(filename.properties)，程序安装后第一次运行会将默认配置文件
 * 写入应用的私有目录下。后续配置从私有目录读取。
 * @author Administrator
 */
public class ConfigManager extends Observable {

	private static volatile ConfigManager instance;
	private WeakReference<Context> mContextRef;
	private ConfigRWer mRWer;
	private String filePath;
	private String fileName;
	private EnumConfigType eType;
	private ConfigData mConfigData;

	/**
	 * 获取ConfigManager的实例。
	 * @param context 当前上下文
	 * @param configData 需要ConfigManager管理的配置参数类
	 * @return instance of {@code ConfigManager}
	 */
	public static ConfigManager getInstance(Context context, ConfigData configData)
	{
		if (null == instance) {
			synchronized (ConfigManager.class) {
				if (null == instance) {
					instance = new ConfigManager(context, configData);
				}
			}
		}
		return instance;
	}
	
	private ConfigManager(Context context, ConfigData configData)
	{
		mRWer = new RWerImpl();
		mContextRef = new WeakReference<Context>(context);
		mConfigData = configData;
	}
	
	/**
	 * 载入配置文件，读取配置项。如果文件不存在，会先判断assets目录下是否存在默认配置文件，若存在，则创建文件并写入默认配置
	 * 若不存在，则创建空文件。
	 * @param name
	 * @param type
	 */
	public void loadFile(String name, EnumConfigType type)
	{
		eType = type;
		fileName = name;
		setFilePath(name);
		try {
			creatFileIfNotExist(filePath);
			mRWer.loadFile(name, eType, mContextRef.get());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 内存中数值和文件中数值都回复默认设置
	 * @throws IOException
	 */
	public void resetDefaultValue() throws IOException
	{
		InputStream is = null;
		try {
			is = mContextRef.get().getAssets().open(fileName + EnumConfigType.PROP.value());
		} catch (FileNotFoundException e) {
			is = null;
		}
		if (null != is) {
			StreamHelper.output(is, mContextRef.get().openFileOutput(fileName + EnumConfigType.PROP.value(), 
					Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE
					| Context.MODE_WORLD_WRITEABLE));
			mRWer.loadFile(fileName, EnumConfigType.PROP, mContextRef.get());
			try {
				getAllValue();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			if (EnumConfigType.XML == eType) {
				mRWer.loadFile(fileName, EnumConfigType.XML, mContextRef.get());
				setAllValue().commit();
				new File(mContextRef.get().getFilesDir() + "/" + fileName + EnumConfigType.PROP.value()).delete();
			}
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
	 * 判断文件是否存在。若不存在，则从assets目录读取默认配置然后创建文件，写入默认配置。
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	private void creatFileIfNotExist(String filePath) throws IOException
	{
		if (!new File(filePath).exists()) {
			resetDefaultValue();
		}
	}
	
	/**
	 * 将配置参数类里的配置数据写入内存
	 * @throws IOException 
	 */
	public ConfigManager setAllValue() throws IOException
	{
		String[] names = ObjectHelper.getFieldNames(mConfigData);
		Object[] values = ObjectHelper.getFieldValues(mConfigData);
		Map<String, Object> map = new Hashtable<String, Object>();
		for (int i = 0; i < names.length; i ++) {
			map.put(names[i], values[i]);
		}
		mRWer.setAll(map);
		return this;
	}
	
	/**
	 * 从文件中读取配置数据赋值给配置参数类
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 */
	public void getAllValue() throws IllegalArgumentException, NoSuchFieldException
	{
		Map<String, ?> map = mRWer.getAll();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			ObjectHelper.setFieldValue(mConfigData, entry.getKey(), entry.getValue());
		}
		notifyConfigChanged();
	}
	
	/**
	 * 将配置参数类的数据从内存写入文件
	 * @throws IOException
	 */
	public void commit() throws IOException
	{
		mRWer.commit();
		notifyConfigChanged();
	}
	
	private void notifyConfigChanged()
	{
		super.setChanged();
		super.notifyObservers(mConfigData);
	}

}
