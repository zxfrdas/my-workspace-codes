package com.zt.lib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectHelper {
	
	/**
	 * 对象浅层拷贝。用于仅包含基础类型的对象的拷贝。仅赋值同名属性。
	 * @param o
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object copyValue(Object o) throws InstantiationException, IllegalAccessException
	{
		Object copyTo = o.getClass().newInstance();
		Field[] leftFields = copyTo.getClass().getDeclaredFields();
		Field[] rightFields = o.getClass().getDeclaredFields();
		for(int i = 0; i < leftFields.length; i ++) {
			leftFields[i].setAccessible(true);
			rightFields[i].setAccessible(true);
			if (leftFields[i].getName().equals(rightFields[i].getName())) {
				leftFields[i].set(copyTo, rightFields[i].get(o));
			}
		}
		return copyTo;
	}
	
	public static Object invokeMethod(Object object, String methodName, Object... args)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Method[] methods = object.getClass().getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
			if (method.getName().toLowerCase().contains(methodName.toLowerCase())) {
				return method.invoke(object, args);
			}
		}
		return null;
	}
	
	public static String toString(Object object)
	{
		StringBuilder builder = new StringBuilder();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				builder.append("\n" + field.getName() + " = " + field.get(object));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}
	
}
