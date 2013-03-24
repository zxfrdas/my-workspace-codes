package com.zt.taobao;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final String RESULT = "http://s.taobao.com/search?spm=a230r.1.8.7.aElfgy&q=" +
			"hdmi%C1%AC%BD%D3%CF%DF&initiative_id=staobaoz_20130323&sort=sale-desc&atype=b&" +
			"filterFineness=2&bcoffset=1&newpre=null&style=list&s=0#J_Filter";
	
	private static final int DEEPTH = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();
		HttpParser parser = new HttpParser();
		HttpVisitor visitor = new HttpVisitor();
		String nextPage = null;
		String content = null;
		for (int i = 0; i < DEEPTH; i ++) {
			content = visitor.getHtml(RESULT);
			parser.parserShopPage(visitor.getHtml(parser
					.parserResultPage(content, i)));
			content = null;
			while (null != (nextPage = parser.getNextPageUrl(content))) {
				content = visitor.getHtml(nextPage);
				items.addAll(parser.parserPage(content));
			}
		}
		System.out.println(parser.getCount());
	}

}
