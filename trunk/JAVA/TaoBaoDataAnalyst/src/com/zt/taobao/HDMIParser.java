package com.zt.taobao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HDMIParser {
	private static final String ITEM_URL = "h3>a[href]";
	private static final String BUYER_LIST = "div#J_showBuyerList>button#J_listBuyerOnView";
	private static final String NEXT_PAGE = "a[href].J_TAjaxTrigger:contains(下一页)";
	private static final String PRICE_AND_NUMBER = "tr:has(td:not(:has(img[src]))>em)" +
			">td:not(.cell-align-l)";
	private static final String COLOR_AND_LENGTH = "td:not(:has(span.tb-anonymous)).cell-align-l";
	private static final String CAPACITY = "td:has(img[src]).cell-align-l";
	public static final String KEY_PRICE = "price";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_COLOR = "color";
	public static final String KEY_TYPE = "length";
	
	private String mStartUrl;
	private Map<String, String> mCache;
	private Count mCount;
	
	public HDMIParser() {
		mCache = new HashMap<String, String>(4);
		mCount = new Count();
	}
	
	public HDMIParser(String startUrl) {
		this();
		mStartUrl = startUrl;
	}
	
	public Elements parser(String content, String selection)
	{
		Document document = Jsoup.parse(content);
		return document.select(selection);
	}
	
	/**
	 * 解析搜索结果页面，获取指定索引的店铺的地址
	 * @param content
	 * @param 试图获取地址的店铺在结果中的位置
	 */
	public String parserResultPage(String content, int resultIndex)
	{
		Elements elements = parser(content, ITEM_URL);
		if (resultIndex < elements.size()) {
			return elements.get(resultIndex).absUrl("href");
		}
		return "";
	}
	
	/**
	 * 解析店铺页面，获取交易记录页面的地址
	 * @param content
	 */
	public void parserShopPage(String content)
	{
		Elements elements = parser(content, BUYER_LIST);
		mStartUrl = elements.get(0).absUrl("detail:params");
		mStartUrl = mStartUrl.substring(0, mStartUrl.lastIndexOf(","));
		System.out.println(mStartUrl);
	}
	
	/**
	 * 解析交易记录一页的数据
	 * @param content
	 * @return
	 */
	public List<Item> parserPage(String content)
	{
		final int capacity = parserCapacity(content); 
		List<Item> items = new ArrayList<Item>(capacity);
		for (int index = 0; index < capacity; index ++) {
			parserPriceAndNumber(content, index);
			parserColorAndLength(content, index);
			Item item = new Item();
			item.price = getPrice();
			item.number = getNumber();
			item.color = getColor();
			item.length = getLength();
			mCount.add(item);
			items.add(item);
		}
		return items;
	}
	
	/**
	 * 获取交易记录下一页的地址
	 * @param content
	 * @return
	 */
	public String getNextPageUrl(String content)
	{
		if (null == content || "".equals(content)) {
			return mStartUrl;
		} else if (0 != parser(content, NEXT_PAGE).size()) {
			return parser(content, NEXT_PAGE).get(0).attr("href");
		}
		return null;
	}
	
	private int parserCapacity(String content)
	{
		Elements elements = parser(content, CAPACITY);
		return elements.size();
	}
	
	private String getPrice()
	{
		return mCache.get(KEY_PRICE);
	}
	
	private String getNumber()
	{
		return mCache.get(KEY_NUMBER);
	}
	
	private void parserPriceAndNumber(String content, int itemIndex)
	{
		Elements elements = parser(content, PRICE_AND_NUMBER);
		mCache.put(KEY_PRICE, elements.get(4 * itemIndex).text());
		mCache.put(KEY_NUMBER, elements.get(4 * itemIndex + 1).text());
	}
	
	private void parserColorAndLength(String content, int itemIndex)
	{
		Elements elements = parser(content, COLOR_AND_LENGTH);
		String str = elements.get(itemIndex).text();
		System.out.println(str);
		String color = str.substring(str.lastIndexOf("颜色分类:") + 5, str.lastIndexOf(";"));
		String type = str.substring(str.lastIndexOf(":") + 1, str.length() - 1);
		mCache.put(KEY_COLOR, color);
		mCache.put(KEY_TYPE, type);
	}
	
	private String getLength()
	{
		return mCache.get(KEY_TYPE);
	}
	
	private String getColor()
	{
		return mCache.get(KEY_COLOR).toString();
	}
	
	public String getCount()
	{
		return mCount.getCount();
	}
	
}
