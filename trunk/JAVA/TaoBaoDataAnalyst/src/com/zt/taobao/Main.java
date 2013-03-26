package com.zt.taobao;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final int DEEPTH = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();
//		HDMIParser parser = new HDMIParser();
		BattaryParser parser = new BattaryParser();
		HttpVisitor visitor = new HttpVisitor();
		String nextPage = null;
		String content = null;
		for (int i = 0; i < DEEPTH; i ++) {
			content = visitor.getHtml(Params.BATTARY);
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
