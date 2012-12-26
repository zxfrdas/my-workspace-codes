package com.zt.lib.impl.test;

import java.lang.reflect.Field;

import android.test.AndroidTestCase;

import com.zt.lib.ObjectReflector;

public class ObjectHelperTest extends AndroidTestCase {

	TestObject o;
	
	@Override
	protected void setUp() throws Exception
	{
		o = new TestObject();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testGetFieldAnnotationValue()
	{
		Field[] fields = ObjectReflector.getFields(o);
		String[] names = ObjectReflector.getFieldNames(o);
		int index = 0;
		for (String string : names) {
			try {
				ObjectReflector.getFieldTargetNameValue(fields[index]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			index ++;
		}
		
	}
	
	public void testGetFieldAnnotationValues()
	{
		String[] names = ObjectReflector.getFieldTargetNameValues(new TestObject());
		assertEquals("pStringArray", names[0]);
		assertEquals("pString", names[1]);
		assertEquals("pInt", names[2]);
		assertEquals("pBoolean", names[3]);
	}

}
