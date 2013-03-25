package com.zt.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static final int DEEPTH = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();
		HDMIParser parser = new HDMIParser();
		HttpVisitor visitor = new HttpVisitor();
		String nextPage = null;
		String content = null;
		for (int i = 0; i < DEEPTH; i ++) {
			try {
				content = visitor.getHtml(String.format(Params.SEARCH,
						URLEncoder.encode("HDMI连接线", "UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
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
