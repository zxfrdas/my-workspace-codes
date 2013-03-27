package com.zt.taobao.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.zt.taobao.Item;
import com.zt.taobao.util.Count;

public abstract class AbstractTaoBaoParser implements ITaoBaoParser {
	protected static final String SHOP_URL = "h3>a[href]";
	protected static final String BUYER_LIST = "div#J_showBuyerList>button#J_listBuyerOnView";
	protected static final String NEXT_PAGE = "a[href].J_TAjaxTrigger:contains(下一页)";
	protected static final String PRICE_AND_NUMBER = "tr:has(td:not(:has(img[src]))>em)" +
			">td:not(.cell-align-l)";
	protected static final String COLOR_AND_TYPE = "td:not(:has(span.tb-anonymous)).cell-align-l";
	protected static final String CAPACITY = "td:has(img[src]).cell-align-l";
	protected static final String KEY_PRICE = "price";
	protected static final String KEY_NUMBER = "number";
	protected static final String KEY_COLOR = "color";
	protected static final String KEY_TYPE = "type";
	
	protected Map<String, String> mCache;
	protected Count mCount;
	
	public AbstractTaoBaoParser() {
		mCache = new HashMap<String, String>(4);
		mCount = new Count();
	}
	
	int parserCapacity(String content)
	{
		Elements elements = parser(content, CAPACITY);
		return elements.size();
	}
	
	@Override
	public Elements parser(String content, String selection)
	{
		Document document = Jsoup.parse(content);
		return document.select(selection);
	}

	@Override
	public String getShopUrlFromResult(String content, int index)
	{
		Elements elements = parser(content, SHOP_URL);
		if (index < elements.size()) {
			return elements.get(index).absUrl("href");
		}
		return "";
	}

	@Override
	public String getRecordUrlFromShop(String content)
	{
		Elements elements = parser(content, BUYER_LIST);
		String str = elements.get(0).absUrl("detail:params");
		return str.substring(0, str.lastIndexOf(","));
	}

	@Override
	public String getNextRecordPageUrl(String content, String firstUrl)
	{
		if (null == content || "".equals(content)) {
			return firstUrl;
		} else if (0 != parser(content, NEXT_PAGE).size()) {
			return parser(content, NEXT_PAGE).get(0).attr("href");
		}
		return null;
	}

	@Override
	public List<Item> parserRecordPage(String content)
	{
		final int capacity = parserCapacity(content); 
		List<Item> items = new ArrayList<Item>(capacity);
		for (int index = 0; index < capacity; index ++) {
			parserPriceAndNumber(content, index);
			parserColorAndType(content, index);
			Item item = new Item();
			item.price = getPrice();
			item.number = getNumber();
			item.color = getColor();
			item.length = getType();
			mCount.add(item);
			items.add(item);
		}
		return items;
	}

	private String getType()
	{
		return mCache.get(KEY_TYPE);
	}

	private String getColor()
	{
		return mCache.get(KEY_COLOR);
	}

	private String getNumber()
	{
		return mCache.get(KEY_NUMBER);
	}

	private String getPrice()
	{
		return mCache.get(KEY_PRICE);
	}

	abstract void parserPriceAndNumber(String content, int itemIndex);
	
	abstract void parserColorAndType(String content, int itemIndex);

	@Override
	public String getOutput()
	{
		return mCount.getCount();
	}
	
}
