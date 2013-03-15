package com.zt.tvmao.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zt.tvmao.core.travers.ChannelTraversor;
import com.zt.tvmao.core.travers.EventTraversor;
import com.zt.tvmao.core.travers.TVMaoNodeTraversor;
import com.zt.tvmao.core.visitors.ChannelVisitor;
import com.zt.tvmao.core.visitors.EventVisitor;
import com.zt.tvmao.core.visitors.TVMaoNodeVisitor;
import com.zt.tvmao.util.TVMaoHtmlVistor;

public class TVMaoParser implements IParser {
	private static final String BASE_URL = "http://www.tvmao.com";
	private TVMaoHtmlVistor htmlVistor;
	private Document mDocument;
	private TVMaoNodeVisitor mVisitor;
	private TVMaoNodeTraversor mTraversor;
	private TVMaoPage mTvMaoPage;
	
	public TVMaoParser() {
		htmlVistor = new TVMaoHtmlVistor();
		mVisitor = null;
		mTraversor = null;
		mTvMaoPage = null;
	}
	
	@Override
	public TVMaoPage parser(String url, ParserType type)
	{
		if (!htmlVistor.isSameUrl(url)) {
			mDocument = Jsoup.parseBodyFragment(htmlVistor.getHtml(url), BASE_URL);
		}
		switch (type)
		{
		case channel:
			mVisitor = new ChannelVisitor();
			mTraversor = new ChannelTraversor(mDocument, mVisitor);
			mTvMaoPage = new TVMaoPage(mTraversor.traverse());
			break;
			
		case channelDetail:
			break;
			
		case event:
			//TODO-第一步解析搜索结果中的节目
			mVisitor = new EventVisitor();
			mTraversor = new EventTraversor(mDocument, mVisitor);
			mTvMaoPage = new TVMaoPage(mTraversor.traverse(), 40);
			//TODO-第二步解析所有节目的详情页面，获取图片地址
			//TODO-第三步解析当前页标、总页数、上一页和下一页的地址
			break;
			
		case eventDetail:
			break;

		default:
			break;
		}
		return mTvMaoPage;
	}
}
