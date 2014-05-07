package com.zt.tvmao.vo;

import java.io.Serializable;

import com.zt.lib.util.Reflector;

/**
 * 电视猫数据类基类
 * @author zhaotong
 */
public abstract class TVMaoObject implements Serializable {
	private static final long serialVersionUID = 3387198856670893347L;

	/**
	 * 判断电视猫数据类是否为空
	 * @return 为空返回true，反之返回false
	 */
	public abstract boolean isEmpty();
	
	/**
	 * 判断传入的字符串参数是否为空值
	 * @param string 被判断的字符串
	 * @return 为空返回true，反之返回false
	 */
	public boolean isStringNull(String string)
	{
		return ((null == string) || (null != string && string.isEmpty()));
	}

	@Override
	public String toString()
	{
		return Reflector.toString(this);
	}
}
