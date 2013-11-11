package com.zt.lib.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;

/**
 * 用于获取数据库操作类实例的工厂方法。
 * <p>所获取的类应该均为{@code AbsRecorder}的子类。
 * <p>实际返回的是进行了动态代理的数据库操作方法。
 * @see AbsDAO
 * @see DAOProxy
 */
public class DAOFactory {

	/**
	 * 创建经过动态代理的数据库操作类实例
	 * @param clazz 试图创建的操作类类型
	 * @param context {@code AbsDAO}实例所需的参数
	 * @return 创建的实例
	 */
	public static IDAO<?, ?> getInstance(Class<?> clazz, Context context)
	{
		if (IDAO.class.isAssignableFrom(clazz)) {
			Constructor<?> constructor = null;
			try {
				constructor = clazz.getConstructor(Context.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				
			}
			if (null != constructor) {
				IDAO<?, ?> recorder = null;
				try {
					recorder = (IDAO<?, ?>) constructor.newInstance(context);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return new DAOProxy(recorder).bind();
			}
		}
		return null;
	}
	
}
