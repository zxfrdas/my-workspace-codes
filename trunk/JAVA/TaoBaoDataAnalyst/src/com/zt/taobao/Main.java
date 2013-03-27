package com.zt.taobao;

import java.util.ArrayList;
import java.util.List;

import com.zt.taobao.parser.BattaryParser;
import com.zt.taobao.parser.ITaoBaoParser;
import com.zt.taobao.util.HttpVisitor;
import com.zt.taobao.util.Params;

public class Main {
	private static final int DEEPTH = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();
//		ITaoBaoParser parser = new HDMIParser();
		ITaoBaoParser parser = new BattaryParser();
		HttpVisitor visitor = new HttpVisitor();
		String nextPage = null;
		for (int i = 0; i < DEEPTH; i ++) {
			String firstUrl = parser.getRecordUrlFromShop(visitor.getHtml(parser
					.getShopUrlFromResult(visitor.getHtml(Params.BATTARY), i)));
			System.out.println(firstUrl);
			String content = null;
			while (null != (nextPage = parser.getNextRecordPageUrl(content, firstUrl))) {
				content = visitor.getHtml(nextPage);
				items.addAll(parser.parserRecordPage(content));
			}
		}
		System.out.println(parser.getOutput());
	}

}
