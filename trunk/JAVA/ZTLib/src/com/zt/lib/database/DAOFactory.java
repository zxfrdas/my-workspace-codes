package com.zt.lib.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.zt.lib.database.impl.SQLite3DAO;

import android.content.Context;

/**
 * 用于获取数据库操作类实例的工厂方法。
 * <p>实际返回的是进行了动态代理的数据库操作方法。
 * @see SQLite3DAO
 * @see DAOProxy
 */
public class DAOFactory {

	/**
	 * 创建经过动态代理的数据库操作类实例
	 * @param clazz 试图创建的操作类类型
	 * @param context 构造实例所需的参数
	 * @param item 数据库数据类的Class
	 * @return 创建的实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> IDAO<T> getInstance(Class<?> clazz, Context context, Class<?> item)
	{
		if (IDAO.class.isAssignableFrom(clazz)) {
			Constructor<?> constructor = null;
			try {
				constructor = clazz.getConstructor(new Class<?>[] { Context.class,
						item.getClass() });
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				
			}
			if (null != constructor) {
				IDAO<?> recorder = null;
				try {
					recorder = (IDAO<?>) constructor.newInstance(context, item);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return (IDAO<T>) new DAOProxy(recorder).bind();
			}
		}
		return null;
	}
	
}
