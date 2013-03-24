package com.zt.tvmao.core.visitors;

import java.lang.ref.WeakReference;

import org.jsoup.select.NodeVisitor;

import com.zt.tvmao.util.Utility;
import com.zt.tvmao.vo.TVMaoObject;

/**
 * 电视猫HTML节点解析规则基类
 * @author zhaotong
 */
public abstract class TVMaoNodeVisitor implements NodeVisitor {
	/**
	 * 保存解析数据的电视猫数据类
	 */
	protected TVMaoObject mData;
	/**
	 * 解析电视猫数据时的回调接口
	 */
	protected WeakReference<IVisitorCallback> mListenerRef;
	
	/**
	 * 设置HTML解析电视猫数据时所需的回调接口
	 * @param listener {@link IVisitorCallback}
	 */
	public void setParserListener(IVisitorCallback listener)
	{
		mListenerRef = new WeakReference<IVisitorCallback>(listener);
	}
	
	/**
	 * 判断回调接口是否为空值
	 * @return 为空返回true，反之返回false
	 */
	public boolean isListenerNull()
	{
		return Utility.isRefNull(mListenerRef);
	}
	
	/**
	 * 判断电视猫数据类是否为空
	 * @return 为空返回true，反之返回false
	 */
	public boolean isDataNull()
	{
		return (null == mData);
	}

}
