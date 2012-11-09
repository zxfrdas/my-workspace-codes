package com.zt.lib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectHelper {
	
	/**
	 * 对象浅层拷贝。用于仅包含基础类型的对象的拷贝。仅赋值同名属性。
	 * @param o 要拷贝的对象
	 * @return 拷贝之后的新对象
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	public static Object copyValue(Object o) throws InstantiationException, IllegalAccessException
	{
		Object copyTo = o.getClass().newInstance();
		Field[] leftFields = copyTo.getClass().getDeclaredFields();
		Field[] rightFields = o.getClass().getDeclaredFields();
		for(int i = 0; i < leftFields.length; i ++) {
			leftFields[i].setAccessible(true);
			rightFields[i].setAccessible(true);
			leftFields[i].set(copyTo, rightFields[i].get(o));
		}
		return copyTo;
	}
	
	/**
	 * 调用对象中指定函数，包括私有函数。
	 * @param object
	 * @param methodName
	 * @param args
	 * @return 函数返回值
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException 
	 */
	public static Object invokeMethod(Object object, String methodName, Object... args)
			throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		Method[] methods = object.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().toLowerCase().equals(methodName.toLowerCase())) {
				method.setAccessible(true);
				try {
					return method.invoke(object, args);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		throw new NoSuchMethodException();
	}
	
	/**
	 * 将对象中变量（包括私有变量）及其值构造为字符串返回。
	 * @param object 试图打印的对象
	 * @return 字符串形如"键 = 值"
	 */
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
	
	/**
	 * 获取对象中所有声明的变量（包括私有变量）名。
	 * @param o
	 * @return 包含所有变量名的字符串数组
	 */
	public static String[] getFieldNames(Object o)
	{
		Field[] fields = o.getClass().getDeclaredFields();
		String[] names = new String[fields.length];
		int index = 0;
		for (Field f : fields) {
			names[index] = f.getName();
			index ++;
		}
		return names;
	}
	
	/**
	 * 获取对象中多有声明变量（包括私有变量）的值或引用。
	 * @param o
	 * @return 包含所有值或引用的对象数组
	 * @throws IllegalArgumentException
	 */
	public static Object[] getFieldValues(Object o ) throws IllegalArgumentException
	{
		Field[] fields = o.getClass().getDeclaredFields();
		Object[] values = new Object[fields.length];
		int index = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				values[index] = field.get(o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			index ++;
		}
		return values;
	}
	
	/**
	 * 设置对象中指定变量的值，变量名不能忽略大小写。
	 * @param o 需设置的对象
	 * @param fieldName 变量名
	 * @param value 设置的值
	 * @throws NoSuchFieldException 未找到指定名称的变量
	 * @throws IllegalArgumentException 试图设置的值不正确
	 */
	public static void setFieldValue(Object o, String fieldName, Object value) throws IllegalArgumentException,
			NoSuchFieldException
	{
		Field field = o.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		try {
			field.set(o, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取对象中指定变量的值，变量名不能忽略大小写
	 * @param o 视图读取变量的对象
	 * @param fieldName 变量名
	 * @return 变量的值
	 * @throws NoSuchFieldException 无此变量
	 */
	public static Object getFieldValue(Object o, String fieldName) throws NoSuchFieldException
	{
		Object object = null;
		Field field = o.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		try {
			object = field.get(o);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}
	
}
