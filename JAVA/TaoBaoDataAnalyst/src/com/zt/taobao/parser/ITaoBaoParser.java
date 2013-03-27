package com.zt.taobao.parser;

import java.util.List;

import org.jsoup.select.Elements;

import com.zt.taobao.Item;

public interface ITaoBaoParser {

	/**
	 * 用指定的规则解析输入的网页文本，得到符合规则的节点集合
	 * @param content 网页文本
	 * @param selection 解析规则
	 * @return 节点集合
	 */
	public Elements parser(String content, String selection);
	
	/**
	 * 从搜索结果页面获取指定索引的店铺网址
	 * @param content 搜索结果页面文本
	 * @param index 在结果中的索引
	 * @return 店铺网址
	 */
	public String getShopUrlFromResult(String content, int index);
	
	/**
	 * 从店铺页面获取历史交易记录网址
	 * @param content 店铺页面文本
	 * @return 历史交易记录网址
	 */
	public String getRecordUrlFromShop(String content);
	
	/**
	 * 从历史交易记录页面获取下一页网址
	 * @param content 历史交易页面文本
	 * @param firstUrl 历史交易记录页面的第一页网址
	 * @return 下一页网址
	 */
	public String getNextRecordPageUrl(String content, String firstUrl);
	
	/**
	 * 解析历史交易记录页面获取商品集合
	 * @param content 历史交易记录页面
	 * @return 商品集合
	 */
	public List<Item> parserRecordPage(String content);
	
	/**
	 * 获取解析完毕后的输出
	 * @return 解析完毕后结果集合文字输出
	 */
	public String getOutput();
	
}
