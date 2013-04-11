package com.zt.taobao.parser;

import org.jsoup.select.Elements;


public class HDMIParser extends AbstractTaoBaoParser {

	@Override
	void parserPriceAndNumber(String content, int itemIndex)
	{
		Elements elements = parser(content, PRICE_AND_NUMBER);
		mCache.put(KEY_PRICE, elements.get(4 * itemIndex).text());
		mCache.put(KEY_NUMBER, elements.get(4 * itemIndex + 1).text());
	}

	@Override
	void parserColorAndType(String content, int itemIndex)
	{
		Elements elements = parser(content, COLOR_AND_TYPE);
		String str = elements.get(itemIndex).text();
		String color = str.substring(str.lastIndexOf("颜色分类:") + 5, str.lastIndexOf(";"));
		String type = str.substring(str.lastIndexOf(":") + 1, str.length() - 1);
		mCache.put(KEY_COLOR, color);
		mCache.put(KEY_TYPE, type);
	}

}
