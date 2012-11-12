package com.zt.lib.impl.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import android.test.AndroidTestCase;

import com.zt.lib.ObjectHelper;
import com.zt.lib.Print;

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
		Field[] fields = ObjectHelper.getFields(o);
		String[] names = ObjectHelper.getFieldNames(o);
		int index = 0;
		for (String string : names) {
			Print.d(fields[index].getName());
			try {
				ObjectHelper.getFieldAnnotationValue(fields[index]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			index ++;
		}
		
	}
	
	public void testGetFieldAnnotationValues()
	{
		String[] names = ObjectHelper.getFieldAnnotationValues(new TestObject());
		assertEquals("pString", names[0]);
		assertEquals("pInt", names[1]);
		assertEquals("pBoolean", names[2]);
	}

}
