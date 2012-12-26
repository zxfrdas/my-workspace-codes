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

import com.zt.lib.ObjectReflector;
import com.zt.lib.Print;
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
	private ReaderWriter mRWer;
	private String filePath;
	private String fileName;
	private EnumConfigType eType;
	private IConfigData mConfigData;
	private SingletonValueMap<String, String> mNameMap;

	/**
	 * 获取ConfigManager的实例。
	 * @param context 当前上下文
	 * @param configData 需要ConfigManager管理的配置参数类
	 * @return instance of {@code ConfigManager}
	 */
	public static ConfigManager getInstance(Context context, IConfigData configData)
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
	
	private ConfigManager(Context context, IConfigData configData)
	{
		mContextRef = new WeakReference<Context>(context);
		mConfigData = configData;
		mNameMap = new SingletonValueMap<String, String>();
		if (null != mConfigData) {
			updateNameMap(ObjectReflector.getFieldNames(mConfigData),
					ObjectReflector.getFieldTargetNameValues(mConfigData));
		}
	}
	
	private void updateNameMap(String[] names, String[] annotationNames)
	{
		int index = 0;
		for (String name : names) {
			Print.d("key = " + name + " value = " + annotationNames[index]);
			mNameMap.put(name, annotationNames[index]);
			index ++;
		}
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
		mRWer = ReaderWriterFactory.getInstance().getReaderWriterImpl(eType);
		try {
			if (null == defaultName || "".equals(defaultName)) {
				defaultName = fileName;
			}
			creatFileIfNotExist(filePath, defaultName);
			mRWer.loadFile(fileName, mContextRef.get());
			reLoadAllValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setFilePath(String name)
	{
		switch (eType)
		{
		case XML:
			filePath = "/data/data/" + mContextRef.get().getPackageName() + "/shared_prefs/" + name
					+ eType.value();
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
			reLoadDefaultValue(defaultName);
		}
	}

	/**
	 * 获取配置参数类的唯一实例，供UI根据用户选择修改配置数据。
	 * @return
	 */
	public IConfigData getConfigData()
	{
		return mConfigData;
	}
	
	/**
	 * 从assets目录下读取指定名称的默认配置文件，恢复内存中数值和文件中数值。
	 * @param name 默认配置文件名
	 */
	public void reLoadDefaultValue(String name) throws IOException
	{
		InputStream is = null;
		try {
			is = mContextRef.get().getAssets().open(name + EnumConfigType.PROP.value());
		} catch (FileNotFoundException e) {
			is = null;
		}
		if (null != is) {
			StreamHelper.output(is, mContextRef.get().openFileOutput(fileName + EnumConfigType.PROP.value(), 
					Context.MODE_MULTI_PROCESS));
			ReaderWriterFactory.getInstance().getReaderWriterImpl(EnumConfigType.PROP)
					.loadFile(fileName, mContextRef.get());
//			mRWer.loadFile(fileName, mContextRef.get());
			try {
				reLoadAllValue();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			if (EnumConfigType.XML == eType) {
				ReaderWriterFactory.getInstance().getReaderWriterImpl(EnumConfigType.XML)
						.loadFile(fileName, mContextRef.get());
//				mRWer.loadFile(fileName, EnumConfigType.XML, mContextRef.get());
				commit();
				new File(mContextRef.get().getFilesDir() + "/" + fileName
						+ EnumConfigType.PROP.value()).delete();
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
	
	/**
	 * 重新从文件中读取配置数据赋值给配置参数类，放弃了所有未提交的更改。
	 * @throws IllegalArgumentException 
	 */
	public void reLoadAllValue() throws IllegalArgumentException
	{
		if (null == mConfigData) {
			return;
		}
		Map<String, ?> map = mRWer.getAll();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			try {
				ObjectReflector.setFieldValue(mConfigData, mNameMap.getKeyByValue(entry.getKey()),
						entry.getValue());
			} catch (NoSuchFieldException e) {
				continue;
			}
		}
		notifyConfigChanged();
	}
	
	/**
	 * 获取指定名称的值
	 * @param key
	 * @return value to get
	 */
	public Object getValue(String key)
	{
		return mRWer.get(key);
	}
	
	/**
	 * 获取包括所有值的数组
	 * @return 长度可能为0
	 */
	public Object[] getValues()
	{
		Map<String, ?> map = mRWer.getAll();
		Object[] values = new Object[map.size()];
		int index = 0;
		for (Object o : map.values()) {
			values[index] = o;
			index ++;
		}
		return values;
	}
	
	/**
	 * 获取包括所有键的字符串数组
	 * @return 长度可能为0
	 */
	public String[] getKeys()
	{
		Map<String, ?> map = mRWer.getAll();
		String[] str = new String[map.size()];
		int index = 0;
		for (String s : map.keySet()) {
			str[index] = s;
			index ++;
		}
		return str;
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
	
	/**
	 * 将配置参数类中的值写入内存中MAP里对应的键值对。
	 * @throws IOException 
	 */
	private ConfigManager setAllValue() throws IOException
	{
		if (null == mConfigData) {
			return this;
		}
		String[] names = ObjectReflector.getFieldNames(mConfigData);
		Object[] values = ObjectReflector.getFieldValues(mConfigData);
		Map<String, Object> map = new Hashtable<String, Object>();
		for (int i = 0; i < names.length; i ++) {
			map.put(mNameMap.get(names[i]), values[i]);
		}
		mRWer.setAll(map);
		return this;
	}

	private void notifyConfigChanged()
	{
		super.setChanged();
		super.notifyObservers(mConfigData);
	}

}
