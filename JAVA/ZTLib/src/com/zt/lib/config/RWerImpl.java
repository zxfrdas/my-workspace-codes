package com.zt.lib.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class RWerImpl implements ConfigRWer {
	
	private WeakReference<Context> mContextRef;
	private SharedPreferences mSharedPref;
	private Properties mProper;
	private Editor mSpEditor;
	private String mFileName;

	@Override
	public void loadFile(String name, ConfigType type, Context context) throws FileNotFoundException, IOException
	{
		mContextRef = new WeakReference<Context>(context);
		mSharedPref = null;
		mProper = null;
		mSpEditor = null;
		switch (type)
		{
		case XML:
			mFileName = name;
			mSharedPref = mContextRef.get().getSharedPreferences(mFileName, Context.MODE_MULTI_PROCESS
					| Context.MODE_PRIVATE);
			mSpEditor = mSharedPref.edit();
			break;
			
		case PROP:
			mFileName = name + ConfigType.PROP.value();
			mProper = new Properties();
			mContextRef.get().openFileOutput(mFileName, Context.MODE_PRIVATE).close();
			InputStreamReader isr = new InputStreamReader(mContextRef.get().openFileInput(mFileName));
			mProper.load(isr);
			isr.close();
			break;

		default:
			break;
		}
	}

	@Override
	public Object get(String name)
	{
		Object o = null;
		if (null != mSharedPref) {
			o = mSharedPref.getAll().get(name);
		} else if (null != mProper) {
			o = mProper.get(name);
		}
		return o;
	}

	@Override
	public int getInt(String name)
	{
		int i = 0;
		if (null != mSharedPref) {
			i = mSharedPref.getInt(name, 0);
		} else if (null != mProper) {
			i = Integer.valueOf(mProper.getProperty(name));
		}
		return i;
	}

	@Override
	public boolean getBoolean(String name)
	{
		boolean b = false;
		if (null != mSharedPref) {
			b = mSharedPref.getBoolean(name, false);
		} else if (null != mProper) {
			b = Boolean.valueOf(mProper.getProperty(name));
		}
		return b;
	}

	@Override
	public String getString(String name)
	{
		String s = "";
		if (null != mSharedPref) {
			s = mSharedPref.getString(name, "");
		} else if (null != mProper) {
			s = mProper.getProperty(name);
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ?> getAll()
	{
		Map<String, Object> m = null;
		if (null != mSharedPref) {
			m = (Map<String, Object>) mSharedPref.getAll();
		} else if (null != mProper) {
			m = new Hashtable<String, Object>();
			for (Map.Entry<?, ?> entry : mProper.entrySet()) {
				String key = (String) entry.getKey();
				Object o = entry.getValue();
				m.put(key, o);
			}
		}
		return m;
	}

	@Override
	public ConfigRWer set(String name, Object value)
	{
		if (null != mSharedPref) {
			mSpEditor.putString(name, value.toString());
		} else if (null != mProper) {
			mProper.setProperty(name, value.toString());
		}
		return this;
	}

	@Override
	public ConfigRWer setInt(String name, int value)
	{
		if (null != mSharedPref) {
			mSpEditor.putInt(name, value);
		} else if (null != mProper) {
			mProper.setProperty(name, String.valueOf(value));
		}
		return this;
	}

	@Override
	public ConfigRWer setBoolean(String name, boolean value)
	{
		if (null != mSharedPref) {
			mSpEditor.putBoolean(name, value);
		} else if (null != mProper) {
			mProper.setProperty(name, String.valueOf(value));
		}
		return this;
	}

	@Override
	public ConfigRWer setString(String name, String value)
	{
		if (null != mSharedPref) {
			mSpEditor.putString(name, value);
		} else if (null != mProper) {
			mProper.setProperty(name, value);
		}
		return this;
	}

	@Override
	public ConfigRWer setAll(Map<String, ?> value)
	{
		if (null != mSharedPref) {
			for (Map.Entry<String, ?> entry : value.entrySet()) {
				mSpEditor.putString(entry.getKey(), entry.getValue().toString());
			}
		} else if (null != mProper) {
			mProper.putAll(value);
		}
		return this;
	}

	@Override
	public void commit() throws IOException
	{
		if (null != mSharedPref) {
			mSpEditor.commit();
		} else if (null != mProper) {
			OutputStreamWriter osw = new OutputStreamWriter(mContextRef.get().openFileOutput(
					mFileName, Context.MODE_PRIVATE));
			mProper.store(osw, "");
			osw.close();
		}
	}
	
}
