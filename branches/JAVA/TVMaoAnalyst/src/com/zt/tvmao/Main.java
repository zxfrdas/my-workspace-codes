package com.zt.tvmao;

import com.zt.tvmao.core.parser.IParser.ParserType;
import com.zt.tvmao.core.parser.TVMaoPage;
import com.zt.tvmao.core.parser.TVMaoParser;
import com.zt.tvmao.util.TVMaoHtmlVistor;
import com.zt.tvmao.util.TVMaoUrlBuilder;
import com.zt.tvmao.vo.TVMaoObject;

public class Main {
	static boolean inTVTable = false;
	static boolean inTVNodes = false;
	static boolean inTVNode = false;
	

	public static void main(String[] args)
	{
		TVMaoParser tvMaoParser = new TVMaoParser();
		TVMaoHtmlVistor htmlVistor = new TVMaoHtmlVistor();
		TVMaoPage page = tvMaoParser.parser(TVMaoUrlBuilder.query("深圳"), ParserType.CHANNEL);
		for (TVMaoObject tvMaoObject : page.mDatas) {
			System.out.println(tvMaoObject.toString());
		}
	}
	
}
