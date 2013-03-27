package com.zt.taobao.parser;

import org.jsoup.select.Elements;

public class BattaryParser extends AbstractTaoBaoParser {

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
		String color = str.substring(str.lastIndexOf("颜色分类:") + 5, str.length());
		String length = "未知";
		try {
			length = str.substring(0, str.lastIndexOf("毫安"));
			final int len = length.length();
			for (int i = 0; i < len; i ++) {
				String temp = length.substring(i, len);
				try {
					Integer.valueOf(temp);
					length = temp;
					break;
				} catch (NumberFormatException e) {
					continue;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		mCache.put(KEY_COLOR, color);
		mCache.put(KEY_TYPE, length);
	}

}
