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
import com.zt.lib.collect.SingletonValueMap;

/**
 * 配置文件管理类。需要在assets目录存放默认的配置文件(filename.properties)，程序安装后第一次运行会将默认配置文件
 * 写入应用的私有目录下。后续配置从私有目录读取。
 * <p>从文件中读取配置到配置参数类、将配置参数类写入文件时，均会通知所有注册的观察者。
 * @author zhaotong
 */
public class ConfigManager extends Observable {

	private static volatile ConfigManager instance;
	private WeakReference<Context> mContextRef;
	private ConfigRWer mRWer;
	private String filePath;
	private String fileName;
	private EnumConfigType eType;
	private BaseConfigData mConfigData;
	private SingletonValueMap<String, String> mNameMap;

	/**
	 * 获取ConfigManager的实例。
	 * @param context 当前上下文
	 * @param configData 需要ConfigManager管理的配置参数类
	 * @return instance of {@code ConfigManager}
	 */
	public static ConfigManager getInstance(Context context, BaseConfigData configData)
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
	
	private ConfigManager(Context context, BaseConfigData configData)
	{
		mRWer = new RWerImpl();
		mContextRef = new WeakReference<Context>(context);
		mConfigData = configData;
		mNameMap = new SingletonValueMap<String, String>();
	}
	
	/**
	 * 载入配置文件，读取配置项。如果文件不存在，会先判断assets目录下是否存在默认配置文件，若存在，则创建文件并写入默认配置
	 * 若不存在，则创建空文件。
	 * @param name 配置文件名
	 * @param defaultName assets目录下指定名称的默认配置文件
	 * @param type 保存配置文件类型
	 * @throws IllegalArgumentException 
	 */
	public void loadFile(String name, String defaultName, EnumConfigType type)
			throws IllegalArgumentException
	{
		eType = type;
		fileName = name;
		setFilePath(name);
		try {
			creatFileIfNotExist(filePath, defaultName);
			mRWer.loadFile(fileName, type, mContextRef.get());
			reGetAllValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取配置参数类的唯一实例，供UI根据用户选择修改配置数据。
	 * @return
	 */
	public BaseConfigData getConfigData()
	{
		return mConfigData;
	}
	
	/**
	 * 从assets目录下读取指定名称的默认配置文件，恢复内存中数值和文件中数值。
	 * @param name 默认配置文件名
	 */
	public void resetDefaultValue(String name) throws IOException
	{
		InputStream is = null;
		try {
			is = mContextRef.get().getAssets().open(name + EnumConfigType.PROP.value());
		} catch (FileNotFoundException e) {
			is = null;
		}
		if (null != is) {
			StreamHelper.output(is, mContextRef.get().openFileOutput(fileName + EnumConfigType.PROP.value(), 
					Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE
					| Context.MODE_WORLD_WRITEABLE));
			mRWer.loadFile(fileName, EnumConfigType.PROP, mContextRef.get());
			try {
				reGetAllValue();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			if (EnumConfigType.XML == eType) {
				mRWer.loadFile(fileName, EnumConfigType.XML, mContextRef.get());
				commit();
				new File(mContextRef.get().getFilesDir() + "/" + fileName + EnumConfigType.PROP.value()).delete();
			}
		}
	}
	
	/**
	 * 获取当前配置文件所在的绝对路径
	 * @return filePath
	 */
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
	 * @param defaultName assets目录下指定名称的默认配置文件
	 * @throws IOException
	 */
	private void creatFileIfNotExist(String filePath, String defaultName) throws IOException
	{
		if (!new File(filePath).exists()) {
			resetDefaultValue(defaultName);
		}
	}
	
	/**
	 * 更新内存中配置参数类中的值。
	 * @throws IOException 
	 */
	private ConfigManager setAllValue() throws IOException
	{
		String[] names = ObjectHelper.getFieldNames(mConfigData);
		updateNameMap(names, ObjectHelper.getFieldAnnotationValues(mConfigData));
		Object[] values = ObjectHelper.getFieldValues(mConfigData);
		Map<String, Object> map = new Hashtable<String, Object>();
		for (int i = 0; i < names.length; i ++) {
//			map.put(names[i], values[i]);
			map.put(mNameMap.get(names[i]), values[i]);
		}
		mRWer.setAll(map);
		return this;
	}
	
	/**
	 * 重新从文件中读取配置数据赋值给配置参数类，放弃了所有未提交的更改。
	 * @throws IllegalArgumentException 
	 */
	public void reGetAllValue() throws IllegalArgumentException
	{
		Map<String, ?> map = mRWer.getAll();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			try {
//				ObjectHelper.setFieldValue(mConfigData, entry.getKey(), entry.getValue());
				ObjectHelper.setFieldValue(mConfigData, mNameMap.getKeyByValue(entry.getKey()), entry.getValue());
			} catch (NoSuchFieldException e) {
				continue;
			}
		}
		notifyConfigChanged();
	}
	
	private void updateNameMap(String[] names, String[] annotationNames)
	{
		int index = 0;
		for (String name : names) {
			mNameMap.put(name, annotationNames[index]);
			index ++;
		}
	}
	
	/**
	 * 提交更改，将所有数据写入文件
	 * @throws IOException
	 */
	public void commit() throws IOException
	{
		setAllValue();
		mRWer.commit();
		notifyConfigChanged();
	}
	
	private void notifyConfigChanged()
	{
		super.setChanged();
		super.notifyObservers();
	}

}
