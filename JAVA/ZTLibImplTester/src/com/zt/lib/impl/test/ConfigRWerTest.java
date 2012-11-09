package com.zt.lib.impl.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.test.AndroidTestCase;

import com.zt.lib.ObjectHelper;
import com.zt.lib.Print;
import com.zt.lib.StreamHelper;
import com.zt.lib.config.ConfigRWer;
import com.zt.lib.config.EnumConfigType;
import com.zt.lib.config.RWerImpl;

public class ConfigRWerTest extends AndroidTestCase {

	private static final String FILE_NAME = "testLoadFile";
	ConfigRWer mRWer;
	TestObject mTestObject;
	TestObject mExpectObject;
	SharedPreferences mSp;
	String[] names;
	Object[] values;
	Object[] expects;
	int index;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		Print.setTAG(AllTests.TAG);
		mRWer = new RWerImpl();
		mTestObject = new TestObject();
		InputStream is = getContext().getAssets().open(FILE_NAME + EnumConfigType.PROP.value());
		FileOutputStream fos = getContext().openFileOutput(FILE_NAME + EnumConfigType.PROP.value(),
				Context.MODE_PRIVATE);
		StreamHelper.output(is, fos);
		mSp = getContext().getSharedPreferences(FILE_NAME, Context.MODE_MULTI_PROCESS);
		Editor editor = mSp.edit();
		editor.putInt("publicInt", 1);
		editor.putBoolean("publicBoolean", false);
		editor.putString("publicString", "privateString");
		editor.commit();
		names = ObjectHelper.getFieldNames(mTestObject);
		values = ObjectHelper.getFieldValues(mTestObject);
		mExpectObject = new TestObject();
		mExpectObject.publicInt = 1;
		mExpectObject.publicBoolean = false;
		mExpectObject.publicString = "privateString";
		expects = ObjectHelper.getFieldValues(mExpectObject);
		index = 0;
		try {
			mRWer.loadFile(FILE_NAME, EnumConfigType.XML, getContext());
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

	public void testGet() throws IllegalArgumentException, NoSuchFieldException
	{
		for (index = 0; index < names.length; index ++) {
			ObjectHelper.setFieldValue(mTestObject, names[index], mRWer.get(names[index]));
			assertEquals(expects[index], ObjectHelper.getFieldValue(mTestObject, names[index]));
		}
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

	public void testGetAll() throws IllegalArgumentException, NoSuchFieldException
	{
		Map<String, ?> map = mRWer.getAll();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			ObjectHelper.setFieldValue(mTestObject, name, value);
		}
		assertEquals(1, ObjectHelper.getFieldValue(mTestObject, "publicInt"));
		assertEquals(false, ObjectHelper.getFieldValue(mTestObject, "publicBoolean"));
		assertEquals("privateString", ObjectHelper.getFieldValue(mTestObject, "publicString"));
	}

	public void testSet() throws NoSuchFieldException, IOException
	{
		for (index = 0; index < names.length; index ++) {
			mRWer.set(names[index], ObjectHelper.getFieldValue(mTestObject, names[index])).commit();
			ObjectHelper.setFieldValue(mTestObject, names[index], mRWer.get(names[index]));
			assertEquals(values[index], ObjectHelper.getFieldValue(mTestObject, names[index]));
		}
	}

	public void testSetInt() throws NoSuchFieldException, IOException
	{
		mRWer.setInt("publicInt", (Integer) ObjectHelper.getFieldValue(mTestObject, "publicInt"))
				.commit();
		assertEquals(ObjectHelper.getFieldValue(mTestObject, "publicInt"),
				mRWer.getInt("publicInt"));
	}

	public void testSetBoolean() throws NoSuchFieldException, IOException
	{
		mRWer.setBoolean("publicBoolean",
				(Boolean) ObjectHelper.getFieldValue(mTestObject, "publicBoolean")).commit();
		assertEquals(ObjectHelper.getFieldValue(mTestObject, "publicBoolean"),
				mRWer.getBoolean("publicBoolean"));
	}

	public void testSetString() throws NoSuchFieldException, IOException
	{
		mRWer.setString("publicString",
				(String) ObjectHelper.getFieldValue(mTestObject, "publicString")).commit();
		assertEquals(ObjectHelper.getFieldValue(mTestObject, "publicString"),
				mRWer.getString("publicString"));
	}

	public void testSetAll() throws NoSuchFieldException, IOException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		for (index = 0; index < names.length; index ++) {
			map.put(names[index], ObjectHelper.getFieldValue(mTestObject, names[index]));
		}
		mRWer.setAll(map).commit();
		for (index = 0; index < names.length; index ++) {
			ObjectHelper.setFieldValue(mTestObject, names[index], mRWer.get(names[index]));
			assertEquals(values[index], ObjectHelper.getFieldValue(mTestObject, names[index]));
		}
	}

}
