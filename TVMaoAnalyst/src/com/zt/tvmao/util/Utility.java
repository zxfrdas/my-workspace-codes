package com.zt.tvmao.util;

import java.lang.ref.Reference;

/**
 * 各种不好分类的公共工具类
 * @author zhaotong
 */
public class Utility {

	/**
	 * 判断Reference引用的类是否为空值
	 * @param ref 被判断的Reference类
	 * @return 为空返回true，反之false
	 */
	public static <E extends Reference<?>> boolean isRefNull(E ref)
	{
		return ((null == ref) || (null != ref && null == ref.get()));
	}
	
}
