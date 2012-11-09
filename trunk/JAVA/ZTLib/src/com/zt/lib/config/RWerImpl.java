package com.zt.lib.config;

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
	public void loadFile(String name, EnumConfigType type, Context context)
			throws IOException
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
					| Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
			mSpEditor = mSharedPref.edit();
			break;
			
		case PROP:
			mFileName = name + EnumConfigType.PROP.value();
			mProper = new Properties();
			mProper.load(new InputStreamReader(mContextRef.get().openFileInput(mFileName)));
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
			setByType(name, value);
		} else if (null != mProper) {
			mProper.put(name, value.toString());
		}
		return this;
	}
	
	private void setByType(String name, Object value)
	{
		Class<?> c = value.getClass();
		if (int.class.equals(c) || Integer.class.equals(c)) {
			mSpEditor.putInt(name, Integer.valueOf(value.toString()));
		} else if (float.class.equals(c) || Float.class.equals(c)) {
			mSpEditor.putFloat(name, Float.valueOf(value.toString()));
		} else if (long.class.equals(c) || Long.class.equals(c)) {
			mSpEditor.putLong(name, Long.valueOf(value.toString()));
		} else if (boolean.class.equals(c) || Boolean.class.equals(c)) {
			mSpEditor.putBoolean(name, Boolean.valueOf(value.toString()));
		} else if (String.class.equals(c)) {
			mSpEditor.putString(name, value.toString());
		}
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
				setByType(entry.getKey(), entry.getValue());
			}
		} else if (null != mProper) {
			for (Map.Entry<String, ?> entry : value.entrySet()) {
				mProper.put(entry.getKey(), entry.getValue().toString());
			}
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
