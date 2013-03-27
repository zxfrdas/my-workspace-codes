package com.zt.taobao.util;

import java.util.HashMap;
import java.util.Map;

import com.zt.taobao.Item;

public class Count {
	
	private Map<String, Integer> mPriceCount;
	private Map<String, Integer> mTypeCount;
	private Map<String, Integer> mNumberCount;
	private Map<String, Integer> mColorCount;
	
	public Count() {
		mPriceCount = new HashMap<String, Integer>();
		mTypeCount = new HashMap<String, Integer>();
		mNumberCount = new HashMap<String, Integer>();
		mColorCount = new HashMap<String, Integer>();
	}
	
	public void addPrice(String key)
	{
		int before = 0;
		if (null != mPriceCount.get(key)) {
			before = mPriceCount.get(key);
		}
		mPriceCount.put(key, before + 1);
	}
	
	public void addLength(String key)
	{
		int before = 0;
		if (null != mTypeCount.get(key)) {
			before = mTypeCount.get(key);
		}
		mTypeCount.put(key, before + 1);
	}
	
	public void addNumber(String key)
	{
		int before = 0;
		if (null != mNumberCount.get(key)) {
			before = mNumberCount.get(key);
		}
		mNumberCount.put(key, before + 1);
	}
	
	public void addColor(String key)
	{
		int before = 0;
		if (null != mColorCount.get(key)) {
			before = mColorCount.get(key);
		}
		mColorCount.put(key, before + 1);
	}
	
	public void add(Item item)
	{
		addPrice(item.price);
		addLength(item.length);
		addNumber(item.number);
		addColor(item.color);
	}
	
	public String getCount()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("商品价格：").append("\n");
		for (Map.Entry<String, Integer> entry : mPriceCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		sb.append("商品种类：").append("\n");
		for (Map.Entry<String, Integer> entry : mTypeCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
//		sb.append("数量统计：").append("\n");
//		for (Map.Entry<String, Integer> entry : mNumberCount.entrySet()) {
//			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
//		}
		sb.append("颜色分类：").append("\n");
		for (Map.Entry<String, Integer> entry : mColorCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		return sb.toString();
	}
	
	public void clear()
	{
		mPriceCount.clear();
		mTypeCount.clear();
		mNumberCount.clear();
		mColorCount.clear();
	}
	
}
