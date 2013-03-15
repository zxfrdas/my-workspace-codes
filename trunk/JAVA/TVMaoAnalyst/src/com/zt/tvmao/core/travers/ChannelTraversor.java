package com.zt.tvmao.core.travers;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zt.tvmao.core.visitors.TVMaoNodeVisitor;
import com.zt.tvmao.vo.TVMaoChannel;
import com.zt.tvmao.vo.TVMaoObject;

/**
 * 电视猫频道HTML解析类
 * @author zhaotong
 */
public class ChannelTraversor extends TVMaoNodeTraversor {
	private static final String NAME = "t_q_tab_channel";
	
	/**
	 * 传入HTML文档以及HTML解析规则，构造HTML解析工作类。
	 * @param document HTML文档
	 * @param visitor HTML解析规则
	 */
	public ChannelTraversor(Document document, TVMaoNodeVisitor visitor) {
		super(document, visitor);
	}

	@Override
	public List<TVMaoObject> traverse()
	{
		Element element = mDocument.getElementById(NAME);
		if (debug) {
			System.out.println(element.text());
		}
		traverse(element);
		return mDatas;
	}

	@Override
	public TVMaoObject onStart()
	{
		return new TVMaoChannel();
	}

}
