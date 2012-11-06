package com.zt.lib.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;

public class ReaderImpl implements ConfigReaderWriter {
	
	private WeakReference<Context> mContextRef;
	private ConfigAdapter mWorker;
	private String filePath;

	@Override
	public void loadFile(String name, ConfigType type, Context context)
	{
		mContextRef = new WeakReference<Context>(context);
		switch (type)
		{
		case XML:
			filePath = "/data/data/" + mContextRef.get().getPackageName() + "/shared_prefs/" + name
					+ ConfigType.XML.value();
			mWorker = (ConfigAdapter) mContextRef.get().getSharedPreferences(name, Context.MODE_MULTI_PROCESS
					| Context.MODE_PRIVATE);
			break;
			
		case PROP:
			filePath = "/data/data/" + mContextRef.get().getPackageName() + "/file/" + name
					+ ConfigType.PROP.value();
			mWorker = (ConfigAdapter) new Properties();
			try {
				mWorker.load(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

}
