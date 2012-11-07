package com.zt.lib.impl.test;

import java.io.File;

import com.zt.lib.Print;
import com.zt.lib.config.ConfigManager;
import com.zt.lib.config.ConfigType;
import com.zt.lib.impl.MainActivity;
import com.zt.lib.impl.RootApp;

import android.test.AndroidTestCase;

public class ConfigManagerTest extends AndroidTestCase {
	
	ConfigManager mConfigManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		mConfigManager = ConfigManager.getInstance(getContext());
		Print.enable();
		Print.setTAG(AllTests.TAG);
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testLoadFile()
	{
		mConfigManager.loadFile("test", ConfigType.XML);
		assertEquals("/data/data/com.zt.lib.impl/shared_prefs/test.xml", mConfigManager.getCurFilePath());
		assertTrue(new File(mConfigManager.getCurFilePath()).exists());
		mConfigManager.loadFile("test", ConfigType.PROP);
		assertEquals("/data/data/com.zt.lib.impl/files/test.properties", mConfigManager.getCurFilePath());
		assertTrue(new File(mConfigManager.getCurFilePath()).exists());
	}

}
