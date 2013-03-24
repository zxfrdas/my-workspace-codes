package com.zt.taobao;

import java.util.HashMap;
import java.util.Map;

public class Count {
	
	private Map<Float, Integer> mPriceCount;
	private Map<Float, Integer> mLengthCount;
	private Map<Integer, Integer> mNumberCount;
	private Map<String, Integer> mColorCount;
	
	public Count() {
		mPriceCount = new HashMap<Float, Integer>();
		mLengthCount = new HashMap<Float, Integer>();
		mNumberCount = new HashMap<Integer, Integer>();
		mColorCount = new HashMap<String, Integer>();
	}
	
	public void addPrice(Float key)
	{
		int before = 0;
		if (null != mPriceCount.get(key)) {
			before = mPriceCount.get(key);
		}
		mPriceCount.put(key, before + 1);
	}
	
	public void addLength(Float key)
	{
		int before = 0;
		if (null != mLengthCount.get(key)) {
			before = mLengthCount.get(key);
		}
		mLengthCount.put(key, before + 1);
	}
	
	public void addNumber(Integer key)
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
		sb.append("价格统计：").append("\n");
		for (Map.Entry<Float, Integer> entry : mPriceCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		sb.append("长度统计：").append("\n");
		for (Map.Entry<Float, Integer> entry : mLengthCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		sb.append("数量统计：").append("\n");
		for (Map.Entry<Integer, Integer> entry : mNumberCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		sb.append("颜色统计：").append("\n");
		for (Map.Entry<String, Integer> entry : mColorCount.entrySet()) {
			sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
		}
		return sb.toString();
	}
	
	public void clear()
	{
		mPriceCount.clear();
		mLengthCount.clear();
		mNumberCount.clear();
		mColorCount.clear();
	}
	
}
