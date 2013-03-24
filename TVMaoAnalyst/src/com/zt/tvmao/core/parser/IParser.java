package com.zt.tvmao.core.parser;

/**
 * 读取电视猫网页解析完毕后数据的接口
 * @author zhaotong
 */
public interface IParser {
	/**
	 * 目前支持解析的HTML页面种类
	 * @author zhaotong
	 */
	public enum ParserType {
		/**
		 * 电视猫搜索结果页面中的频道
		 */
		CHANNEL("Channel"),
		/**
		 * 电视猫频道详情页面
		 */
		CHANNEL_DETAIL("ChannelDetail"),
		/**
		 * 电视猫搜索结果页面中的节目
		 */
		EVENT("Event"),
		/**
		 * 电视猫节目详情页面
		 */
		EVENT_DETAIL("EventDetail");
		
		String value = "";
		
		ParserType(String value) {
			this.value = value;
		}
		
		public String getStringValue()
		{
			return this.value;
		}
		
	}
	
	/**
	 * 解析电视猫HTML内容，返回解析完毕后生成的当前页数据
	 * @param url 地址
	 * @param type 频道/频道详情/节目/节目详情
	 * @return {@link TVMaoPage}
	 */
	public TVMaoPage parser(String url, ParserType type);
}
