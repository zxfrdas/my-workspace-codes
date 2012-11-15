package com.zt.lib;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zt.lib.annotations.TargetName;

public class ObjectHelper {
	
	/**
	 * 对象浅层拷贝。用于仅包含基础类型的对象的拷贝。仅赋值同名属性。
	 * @param o 要拷贝的对象
	 * @return 拷贝之后的新对象
	 */
	public static Object copyValue(Object o)
	{
		Object copyTo = null;
		try {
			copyTo = o.getClass().newInstance();
			Field[] leftFields = copyTo.getClass().getDeclaredFields();
			Field[] rightFields = o.getClass().getDeclaredFields();
			for(int i = 0; i < leftFields.length; i ++) {
				leftFields[i].setAccessible(true);
				rightFields[i].setAccessible(true);
				leftFields[i].set(copyTo, rightFields[i].get(o));
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
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
	
	public static Field[] getFields(Object o)
	{
		return o.getClass().getDeclaredFields();
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
	 * 获取所有传入Field的名称
	 * @param fields 要获取名称的变量数组
	 * @return 包含所有变量名的字符串数组
	 */
	public static String[] getFieldNames(Field[] fields)
	{
		String[] names = new String[fields.length];
		int index = 0;
		for (Field f : fields) {
			names[index] = f.getName();
			index ++;
		}
		return names;
	}
	
	/**
	 * 获取对象中所有声明变量（包括私有变量）的值或引用。
	 * @param o
	 * @return 包含所有值或引用的对象数组
	 * @throws IllegalArgumentException
	 */
	public static Object[] getFieldValues(Object o) throws IllegalArgumentException
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
			field.set(o, formatFieldValue(field, value));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private static Object formatFieldValue(Field field, Object value)
	{
		return formatObjectType(field.getType(), value);
	}
	
	/**
	 * 尝试输入的对象转型为指定对象。目前支持转为int/Integer,float/Float,long/Long,boolean/Boolean,String 
	 * @param type 希望转为的型
	 * @param value 被转型对象
	 * @return 转型后的对象
	 */
	public static Object formatObjectType(Class<?> type, Object value)
	{
		if (int.class.equals(type)
				|| Integer.class.equals(type)) {
			value = Integer.valueOf(value.toString());
		} else if (float.class.equals(type)
				|| Float.class.equals(type)) {
			value = Float.valueOf(value.toString());
		} else if (long.class.equals(type)
				|| Long.class.equals(type)) {
			value = Long.valueOf(value.toString());
		} else if (boolean.class.equals(type)
				|| Boolean.class.equals(type)) {
			value = Boolean.valueOf(value.toString());
		} else if (String.class.equals(type)) {
			value = value.toString();
		}
		return value;
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
			field.getType();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 获取输入变量@TargetName这一Annotation的值
	 * @param field 输入变量
	 * @return 变量的@TargetName值，无则返回变量名
	 */
	public static String getFieldTargetNameValue(Field field)
	{
		String name = "";
		Annotation[] annotations = field.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof TargetName) {
				name = ((TargetName)annotation).value();
			} else {
				name = field.getName();
			}
		}
		return name;
	}

	/**
	 * 获取对象中所有变量中@TargetName这一Annotation属性的值。如果无此值，则返回变量本身名称。
	 * @param o
	 * @return 对象中所有变量的@TargetName值，无则返回变量名
	 */
	public static String[] getFieldTargetNameValues(Object o)
	{
		Field[] fields = o.getClass().getDeclaredFields();
		String[] values = new String[fields.length];
		int index = 0;
		for (Field field : fields) {
			values[index] = getFieldTargetNameValue(field);
			index ++;
		}
		return values;
	}
	
	/**
	 * 获取输入变量的所有Annotation
	 * @param field 输入变量
	 * @return 包括该变量所有Annotation的数组，可能为0。
	 */
	public static Annotation[] getFieldAnnotations(Field field)
	{
		return field.getDeclaredAnnotations();
	}
	
}