package com.zt.tvmao.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zt.tvmao.core.cache.TVMaoClassMap;
import com.zt.tvmao.core.travers.TVMaoNodeTraversor;
import com.zt.tvmao.util.TVMaoHtmlVistor;

public class TVMaoParser implements IParser {
	private static final String BASE_URL = "http://www.tvmao.com";
	private TVMaoHtmlVistor mHtmlVistor;
	private Document mDocument;
	private TVMaoClassMap mClassMap;
	private TVMaoNodeTraversor mTraversor;
	private TVMaoPage mTvMaoPage;
	
	public TVMaoParser() {
		mHtmlVistor = new TVMaoHtmlVistor();
		mClassMap = TVMaoClassMap.getInstance();
		mTraversor = null;
		mTvMaoPage = null;
	}
	
	@Override
	public TVMaoPage parser(String url, ParserType type)
	{
		if (!mHtmlVistor.isSameUrl(url)) {
			mDocument = Jsoup.parseBodyFragment(mHtmlVistor.getHtml(url), BASE_URL);
		}
		mTraversor = mClassMap.getTraversor(type, mClassMap.getVisitor(type));
		mTraversor.setDocument(mDocument);
		mTvMaoPage = new TVMaoPage(mTraversor.traverse());
/*		switch (type)
		{
		case CHANNEL:
			mVisitor = new ChannelVisitor();
			mTraversor = new ChannelTraversor(mVisitor);
			mTraversor.setDocument(mDocument);
			mTvMaoPage = new TVMaoPage(mTraversor.traverse());
			break;
			
		case CHANNEL_DETAIL:
			break;
			
		case EVENT:
			//TODO-第一步解析搜索结果中的节目
			mVisitor = new EventVisitor();
			mTraversor = new EventTraversor(mVisitor);
			mTraversor.setDocument(mDocument);
			mTvMaoPage = new TVMaoPage(mTraversor.traverse(), 40);
			//TODO-第二步解析所有节目的详情页面，获取图片地址
			//TODO-第三步解析当前页标、总页数、上一页和下一页的地址
			break;
			
		case EVENT_DETAIL:
			break;

		default:
			break;
		}*/
		return mTvMaoPage;
	}
}
