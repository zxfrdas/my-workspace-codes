package com.zt.tvmao;

import com.zt.tvmao.core.cache.TVMaoCacher;
import com.zt.tvmao.core.parser.IParser;
import com.zt.tvmao.core.parser.IParser.ParserType;
import com.zt.tvmao.core.parser.TVMaoParser;
import com.zt.tvmao.util.TVMaoUrlBuilder;

public class TVMaoManager {
	private static class InstanceHolder {
		private static TVMaoManager instance = new TVMaoManager();
	}
	private static final int PAGE_SIZE = 20;
	private IParser mParser;
	private TVMaoCacher mEventCacher;
	private TVMaoCacher mChannelCacher;
	
	public static TVMaoManager getInstance()
	{
		return InstanceHolder.instance;
	}
	
	private TVMaoManager() {
		mParser = new TVMaoParser();
		mChannelCacher = new TVMaoCacher(PAGE_SIZE);
		mEventCacher = new TVMaoCacher(PAGE_SIZE);
	}
	
	public void query(String keyWord, ParserType type, int page)
	{
		switch (type)
		{
		case CHANNEL:
			if (1 == page) {
				mChannelCacher.put(mParser.parser(TVMaoUrlBuilder.query(keyWord), type));
			}
			mChannelCacher.get(page);
			break;
			
		case EVENT:
			if (1 == page) {
				mEventCacher.put(mParser.parser(TVMaoUrlBuilder.query(keyWord), type));
			}
			mEventCacher.get(page);
			break;

		default:
			break;
		}
	}
	
	/**
	 * 重新搜索关键字前清空缓存
	 */
	public void clearCache()
	{
		mChannelCacher.clear();
		mEventCacher.clear();
	}
	
}
