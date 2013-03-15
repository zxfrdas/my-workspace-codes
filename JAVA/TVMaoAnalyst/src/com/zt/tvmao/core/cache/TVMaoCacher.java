package com.zt.tvmao.core.cache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.zt.tvmao.core.parser.IParser;
import com.zt.tvmao.core.parser.TVMaoPage;
import com.zt.tvmao.vo.TVMaoObject;

/**
 * 电视猫数据缓存基类。用于缓存、重新分割和组织结果集合，以使其符合UI中每一页的数据量
 * @author zhaotong
 */
public class TVMaoCacher {
	private TVMaoPage mPrevPage;
	private TVMaoPage mCurPage;
	private TVMaoPage mNextPage;
	private List<TVMaoObject> mDatas;
	private int mCurItemIndex;
	private int mTotalItem;
	private int mCurHTMLPage;
	private int mTotalHTMLPage;
	private int mPageSize;
	private WeakReference<IParser> mCallbackRef;
	
	public TVMaoCacher(int pageSize) {
		clear();
		mPageSize = pageSize;
	}
	
	/**
	 * 清空缓存
	 */
	public void clear()
	{
		mDatas = new ArrayList<TVMaoObject>();
		mPrevPage = null;
		mCurPage = null;
		mNextPage = null;
		mCurItemIndex = 0;
		mTotalItem = 0;
		mCurHTMLPage = 1;
		mTotalHTMLPage = 1;
	}
	
	public void setCallback(IParser callback)
	{
		mCallbackRef = new WeakReference<IParser>(callback);
	}
	
	private boolean isCallbackNull()
	{
		return ((null == mCallbackRef) || (null != mCallbackRef && null == mCallbackRef
				.get()));
	}
	
	public void put(TVMaoPage page)
	{
		if (null == mCurPage) {
			mCurPage = page;
			firstTimeCache();
		} else if (!mCurPage.isSinglePage() && (mCurHTMLPage != page.mCurHtmlPage)) {
			if (mCurHTMLPage == (page.mCurHtmlPage - 1)) {
				//TODO-往后翻了一页
				mPrevPage = mCurPage;
				mCurPage = mNextPage;
				if (!isCallbackNull()) {
					mNextPage = mCallbackRef.get().parser(mCurPage.mNextUrl, mCurPage.mType);
				}
			} else if (mCurHTMLPage == (page.mCurHtmlPage + 1)) {
				//TODO-往前翻了一页
			} else {
				//TODO-往前往后超过一页
			}
		}
	}
	
	/**
	 * 清空缓存之后首次缓存
	 */
	private void firstTimeCache()
	{
		mCurHTMLPage = mCurPage.mCurHtmlPage;
		mTotalHTMLPage = mCurPage.mTotalHtmlPage;
		mCurItemIndex = 0;
		mTotalItem = mCurPage.mHtmlPageSize;
		if (!mCurPage.isSinglePage() && !isCallbackNull()) {
			mNextPage = mCallbackRef.get().parser(mCurPage.mNextUrl,
					mCurPage.mType);
			mTotalItem += mNextPage.mHtmlPageSize;
		}
	}
	
	public List<TVMaoObject> get(int pageIndex)
	{
		return mDatas;
	}
	
}
