package com.zt.libtester;

import java.lang.reflect.InvocationTargetException;

import com.zt.lib.ObjectHelper;
import com.zt.lib.exceptions.FieldNotFoundException;
import com.zt.lib.exceptions.MethodNotFoundException;

import junit.framework.TestCase;

public class ObjectHelperTest extends TestCase {

	TestObject o1;
	TestObject o2;
	String str;
	String[] strAry;
	boolean b;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		o1 = new TestObject();
		o2 = new TestObject();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testCopyValue()
	{
		try {
			ObjectHelper.setFieldValue(o1, "publicint", 1);
			ObjectHelper.setFieldValue(o1, "publicstring", "abc");
			ObjectHelper.setFieldValue(o1, "publicinteger", 1);
			ObjectHelper.setFieldValue(o1, "publicbool", true);
			ObjectHelper.setFieldValue(o1, "publicboolean", true);
			o2 = (TestObject) ObjectHelper.copyValue(o1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		assertEquals(o1.publicInt, o2.publicInt);
		assertEquals(o1.publicString, o2.publicString);
		assertEquals(o1.publicInteger, o2.publicInteger);
		assertEquals(o1.publicBool, o2.publicBool);
		assertEquals(o1.publicBoolean, o2.publicBoolean);
	}

	public void testInvokeMethod()
	{
		try {
			str = (String) ObjectHelper.invokeMethod(o1, "getString", "abc");
			b = (Boolean) ObjectHelper.invokeMethod(o1, "getbool", false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals("get String is abc\n", str);
		assertEquals(true, b);
	}

	public void testToStringObject()
	{
		System.out.println(ObjectHelper.toString(o1));
		assertTrue(true);
	}

	public void testGetFieldNames()
	{
		strAry = ObjectHelper.getFieldNames(o1);
		assertEquals("publicInt", strAry[0]);
		assertEquals("publicString", strAry[1]);
		assertEquals("publicInteger", strAry[2]);
		assertEquals("publicBool", strAry[3]);
		assertEquals("publicBoolean", strAry[4]);
	}

	public void testSetFieldValue()
	{
		try {
			ObjectHelper.setFieldValue(o1, "publicint", 1);
			ObjectHelper.setFieldValue(o1, "publicstring", "abc");
			ObjectHelper.setFieldValue(o1, "publicinteger", 1);
			ObjectHelper.setFieldValue(o1, "publicbool", true);
			ObjectHelper.setFieldValue(o1, "publicboolean", true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(1, o1.publicInt);
		assertEquals("abc", o1.publicString);
		assertEquals(Integer.valueOf(1), o1.publicInteger);
		assertEquals(true, o1.publicBool);
		assertEquals(Boolean.valueOf(true), o1.publicBoolean);
	}
	
	public static class TestObject {
		public int publicInt = 0;
		public String publicString = "";
		public Integer publicInteger = Integer.valueOf(0);
		public boolean publicBool = false;
		public Boolean publicBoolean = Boolean.valueOf(false);
		
		public boolean getBool(Boolean b)
		{
			return !b.booleanValue();
		}
		
		private String getString(String str)
		{
			return "get String is " + str + "\n";
		}
		
	}

}
