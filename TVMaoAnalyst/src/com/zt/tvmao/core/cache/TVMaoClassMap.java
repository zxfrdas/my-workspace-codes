package com.zt.tvmao.core.cache;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.zt.tvmao.core.parser.IParser;
import com.zt.tvmao.core.parser.IParser.ParserType;
import com.zt.tvmao.core.travers.TVMaoNodeTraversor;
import com.zt.tvmao.core.visitors.TVMaoNodeVisitor;
import com.zt.tvmao.util.Utility;

public class TVMaoClassMap {
	private static final class InstanceHodler {
		private static final TVMaoClassMap instance = new TVMaoClassMap();
	}
	private static final String TRAVERSOR_END = "Traversor";
	private static final String TRAVERSOR_PATH = TVMaoNodeTraversor.class.getPackage().getName();
	private static final String VISITORS_END = "Visitor";
	private static final String VISITORS_PATH = TVMaoNodeVisitor.class.getPackage().getName();
	private Map<ParserType, WeakReference<TVMaoNodeTraversor>> mTraversMap;
	private Map<ParserType, WeakReference<TVMaoNodeVisitor>> mVisitorMap;
	
	public static TVMaoClassMap getInstance() {
		return InstanceHodler.instance;
	}
	
	private TVMaoClassMap() {
		mTraversMap = new HashMap<IParser.ParserType, WeakReference<TVMaoNodeTraversor>>();
		mVisitorMap = new HashMap<IParser.ParserType, WeakReference<TVMaoNodeVisitor>>();
	}
	
	/**
	 * 根据需要解析的类型获取对应的解析类
	 * @param type 需解析的类型
	 * @return 对应的解析类
	 * @see TVMaoNodeTraversor
	 */
	public TVMaoNodeTraversor getTraversor(ParserType type, TVMaoNodeVisitor visitor)
	{
		if (Utility.isRefNull(mTraversMap.get(type))) {
			mTraversMap.put(type, createTraversor(type, visitor));
		}
		return mTraversMap.get(type).get();
	}
	
	/**
	 * 根据需要解析的类型获取对应的解析规则类
	 * @param type 需解析的类型
	 * @return 对应的解析规则类
	 * @see TVMaoNodeVisitor
	 */
	public TVMaoNodeVisitor getVisitor(ParserType type)
	{
		if (Utility.isRefNull(mVisitorMap.get(type))) {
			mVisitorMap.put(type, createVisitor(type));
		}
		return mVisitorMap.get(type).get();
	}
	
	private WeakReference<TVMaoNodeTraversor> createTraversor(ParserType type,
			TVMaoNodeVisitor visitor)
	{
		return create(TRAVERSOR_PATH + "." + type.getStringValue()
				+ TRAVERSOR_END, new Object[] { visitor });
	}
	
	private WeakReference<TVMaoNodeVisitor> createVisitor(ParserType type)
	{
		return create(VISITORS_PATH + "." + type.getStringValue()
				+ VISITORS_END, new Object[] {});
	}
	
	@SuppressWarnings("unchecked")
	private <T> WeakReference<T> create(String name, Object...args)
	{
		Class<T> clasz = null;
		try {
			clasz = (Class<T>) Class.forName(name);
			System.out.println(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Constructor<T> constructor = null;
		T t = null;
		try {
			Class<?>[] classes = new Class[]{};
			if (null != args && args.length > 0) {
				classes = new Class[args.length];
				for (int index = 0; index < args.length; index ++) {
					classes[index] = args[index].getClass().getSuperclass();
				}
			}
			constructor = clasz
					.getConstructor(classes);
			t = constructor.newInstance(args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new WeakReference<T>(t);
	}
	
}
