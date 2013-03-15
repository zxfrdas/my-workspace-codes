package com.zt.tvmao.util;

public class TVMaoUrlBuilder {
	private static final String SEARCH_URL = "http://www.tvmao.com/query.jsp?";
	private static final String KEY_WORDS = "keys";
	private static final String TIME = "time";
	private static final String SORT = "sort";
	private static final String MATCH = "match";
	private static final String START = "start";
	private static final String PAGEEND = "#pg";
	private static final String EQUAL = "=";
	private static final String AND = "&";

	/**
	 * 构造默认搜索链接。即网页搜索结果的第一页，模糊匹配。
	 * @param keyWord 关键字
	 * @return 搜索结果的HTML链接
	 */
	public static String query(CharSequence keyWord)
	{
		return query(keyWord, 1, false);
	}
	
	/**
	 * 构造指定结果页的搜索链接。即网页搜索结果的指定页，模糊匹配。
	 * @param keyWord 关键字
	 * @param page 页数，>=1
	 * @return 搜索结果的HTML链接
	 */
	public static String query(CharSequence keyWord, int page)
	{
		return query(keyWord, page, false);
	}
	
	/**
	 * 构造指定结果页和匹配方式的搜索链接。即网页搜索结果的指定页，指定匹配方式。
	 * @param keyWord 关键字
	 * @param page 页数，>=1
	 * @param isExact true为精确匹配，false为模糊匹配
	 * @return 搜索结果的HTML链接
	 */
	public static String query(CharSequence keyWord, int page, boolean isExact)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(SEARCH_URL).append(KEY_WORDS).append(EQUAL).append(keyWord)
				.append(AND).append(TIME).append(EQUAL).append(100).append(AND)
				.append(SORT).append(EQUAL).append(1)
				.append(AND).append(START).append(EQUAL);
		if (1 == page) {
			sb.append(0);
		} else {
			sb.append((page - 1) * 30).append(PAGEEND);
		}
		if (isExact) {
			sb.append(AND).append(MATCH).append(EQUAL).append(isExact);
		}
		return sb.toString();
	}
	
	
}
