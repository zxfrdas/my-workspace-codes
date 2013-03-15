package com.zt.tvmao.core.visitors;

import com.zt.tvmao.vo.TVMaoObject;

/**
 * 解析HTML过程中解析电视猫数据类时的回调接口
 * @author zhaotong
 * @param <T> 电视猫数据类
 */
public interface IVisitorCallback {
	/**
	 * HTML开始解析一个电视猫数据类时回调
	 * @return 提供来保存数据的电视猫数据类
	 */
	public TVMaoObject onStart();
	/**
	 * HTML解析完一个电视猫数据类后回调
	 * @param data 存有解析数据的电视猫数据类
	 */
	public void onEnd(TVMaoObject data);
}
