package com.zt.lib.impl.test;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import com.zt.lib.ObjectHelper;
import com.zt.lib.Print;
import com.zt.lib.config.ConfigManager;
import com.zt.lib.config.EnumConfigType;

public class ConfigManagerTest extends AndroidTestCase implements Observer {
	
	private static final String FILE_NAME = "testLoadFile";
	ConfigManager mConfigManager;
	TestObject mTestObject;
	TestObject mExpectObject;
	SharedPreferences mSp;
	String[] names;
	Object[] values;
	Object[] expects;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		Print.setTAG(AllTests.TAG);
		mTestObject = new TestObject();
		mTestObject.publicInt = 0;
		mTestObject.publicBoolean = true;
		mTestObject.publicString = "publicString";
		mExpectObject = new TestObject();
		mExpectObject.publicInt = 1;
		mExpectObject.publicBoolean = false;
		mExpectObject.publicString = "privateString";
		names = ObjectHelper.getFieldNames(mTestObject);
		values = ObjectHelper.getFieldValues(mTestObject);
		expects = ObjectHelper.getFieldValues(mExpectObject);
		mConfigManager = ConfigManager.getInstance(getContext(), mTestObject);
		mConfigManager.addObserver(this);
		mConfigManager.loadFile(FILE_NAME, FILE_NAME, EnumConfigType.PROP);
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		mConfigManager.deleteObserver(this);
	}
	
	public void testLoadFile()
	{
//		mConfigManager.loadFile(FILE_NAME, EnumConfigType.XML);
//		assertTrue(new File(mConfigManager.getCurFilePath()).exists());
//		mConfigManager.loadFile(FILE_NAME, EnumConfigType.PROP);
//		assertTrue(new File(mConfigManager.getCurFilePath()).exists());
	}
	
	public void testResetDefaultValue()
	{
		try {
			mConfigManager.reGetAllValue();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		mTestObject.publicInt = 2;
		mTestObject.publicBoolean = true;
		mTestObject.publicString = "asdasd";
		try {
			mConfigManager.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mConfigManager.resetDefaultValue(FILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < names.length; i ++) {
			try {
				assertEquals(expects[i], ObjectHelper.getFieldValue(mTestObject, names[i]));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void testCommit()
	{
		try {
			mConfigManager.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < names.length; i ++) {
			try {
				assertEquals(values[i], ObjectHelper.getFieldValue(mTestObject, names[i]));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void testReGetAllValue()
	{
		try {
			mConfigManager.reGetAllValue();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < names.length; i ++) {
			try {
				assertEquals(expects[i], ObjectHelper.getFieldValue(mTestObject, names[i]));
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(Observable observable, Object data)
	{
		mTestObject = (TestObject) data;
	}

}
