package com.zt.lib.config;

import android.content.Context;

public interface ConfigReaderWriter {
	
	public void loadFile(String name, ConfigType type, Context context);
	
}
