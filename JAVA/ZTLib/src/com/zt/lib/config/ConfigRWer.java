package com.zt.lib.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import android.content.Context;

public interface ConfigRWer {
	
	public void loadFile(String name, ConfigType type, Context context) throws FileNotFoundException, IOException;
	
	public Object get(String name);
	
	public int getInt(String name);
	
	public boolean getBoolean(String name);
	
	public String getString(String name);
	
	public Map<String, ?> getAll();
	
	public ConfigRWer set(String name, Object value);
	
	public ConfigRWer setInt(String name, int value);
	
	public ConfigRWer setBoolean(String name, boolean value);
	
	public ConfigRWer setString(String name, String value);
	
	public ConfigRWer setAll(Map<String, ?> value);
	
	public void commit() throws IOException ;
	
}
