package com.zt.tvmao.core.parser;

import java.util.List;

import com.zt.tvmao.core.parser.IParser.ParserType;
import com.zt.tvmao.vo.TVMaoObject;

/**
 * 电视猫HTML一页数据
 * @author zhaotong
 */
public class TVMaoPage {
	public int mCurHtmlPage;
	public int mTotalHtmlPage;
	public int mHtmlPageSize;
	public String mPrevUrl;
	public String mNextUrl;
	public ParserType mType;
	public List<TVMaoObject> mDatas;
	
	public TVMaoPage(List<TVMaoObject> datas) {
		mDatas = datas;
		mCurHtmlPage = 1;
		mTotalHtmlPage = 1;
		mHtmlPageSize = datas.size();
		mPrevUrl = "";
		mNextUrl = "";
	}
	
	public TVMaoPage(List<TVMaoObject> datas, int htmlPageSize) {
		mDatas = datas;
		mCurHtmlPage = 1;
		mTotalHtmlPage = 1;
		mHtmlPageSize = htmlPageSize;
		mPrevUrl = "";
		mNextUrl = "";
	}
	
	public boolean isSinglePage()
	{
		return (mTotalHtmlPage == 1 && (mTotalHtmlPage == mCurHtmlPage));
	}

}
