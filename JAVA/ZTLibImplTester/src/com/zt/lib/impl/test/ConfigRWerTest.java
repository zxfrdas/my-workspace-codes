package com.zt.lib.impl.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.test.AndroidTestCase;

import com.zt.lib.ObjectHelper;
import com.zt.lib.Print;
import com.zt.lib.StreamHelper;
import com.zt.lib.config.ConfigRWer;
import com.zt.lib.config.ConfigType;
import com.zt.lib.config.RWerImpl;
import com.zt.lib.impl.test.AllTests.TestObject;

public class ConfigRWerTest extends AndroidTestCase {

	private static final String FILE_NAME = "testLoadFile";
	ConfigRWer mRWer;
	TestObject mTestObject;
	String[] names;
	Object[] values;
	int index;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		Print.setTAG(AllTests.TAG);
		mRWer = new RWerImpl();
		mTestObject = new TestObject();
		InputStream is = getContext().getAssets().open(FILE_NAME + ConfigType.PROP.value());
		FileOutputStream fos = getContext().openFileOutput(FILE_NAME + ConfigType.PROP.value(),
				Context.MODE_PRIVATE);
		StreamHelper.output(is, fos);
		names = ObjectHelper.getFieldNames(mTestObject);
		values = ObjectHelper.getFieldValues(mTestObject);
		index = 0;
		try {
			mRWer.loadFile(FILE_NAME, ConfigType.PROP, getContext());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testGet()
	{
		assertEquals(1, mRWer.get("publicInt"));
		assertEquals(false, mRWer.get("publicBoolean"));
		assertEquals("privateString", mRWer.get("publicString"));
	}
	
	public void testGetInt()
	{
		assertEquals(1, mRWer.getInt("publicInt"));
	}
	
	public void testGetBoolean()
	{
		assertEquals(false, mRWer.getBoolean("publicBoolean"));
	}
	
	public void testGetString()
	{
		assertEquals("privateString", mRWer.getString("publicString"));
	}
	
	public void testGetAll()
	{
		fail("not test");
	}
	
	public void testSet() throws NoSuchFieldException
	{
		mRWer.set("publicInt", ObjectHelper.getFieldValue(mTestObject, "publicInt"));
		mRWer.set("publicBoolean", ObjectHelper.getFieldValue(mTestObject, "publicBoolean"));
		mRWer.set("publicString", ObjectHelper.getFieldValue(mTestObject, "publicString"));
		assertEquals(0, mRWer.get("publicInt"));
		assertEquals(true, mRWer.get("publicBoolean"));
		assertEquals("publicString", mRWer.get("publicString"));
	}
	
	public void testSetInt() throws NoSuchFieldException
	{
		mRWer.setInt("publicInt", (Integer) ObjectHelper.getFieldValue(mTestObject, "publicInt"));
		assertEquals(ObjectHelper.getFieldValue(mTestObject, "publicInt"), mRWer.getInt("publicInt"));
	}
	
	public void testSetBoolean() throws NoSuchFieldException
	{
		mRWer.setBoolean("publicBoolean", (Boolean) ObjectHelper.getFieldValue(mTestObject, "publicBoolean"));
		assertEquals(ObjectHelper.getFieldValue(mTestObject, "publicBoolean"), mRWer.getBoolean("publicBoolean"));
	}
	
	public void testSetString() throws NoSuchFieldException
	{
		mRWer.setString("publicString", (String) ObjectHelper.getFieldValue(mTestObject, "publicString"));
		assertEquals(ObjectHelper.getFieldValue(mTestObject, "publicString"), mRWer.getString("publicString"));
	}
	
	public void testSetAll()
	{
		fail("not test");
	}

}
