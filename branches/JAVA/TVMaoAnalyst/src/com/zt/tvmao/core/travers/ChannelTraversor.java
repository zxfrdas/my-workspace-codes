package com.zt.tvmao.core.travers;

import java.util.List;

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
	 * 传入HTML解析规则，构造HTML解析工作类。
	 * @param visitor HTML解析规则
	 */
	public ChannelTraversor(TVMaoNodeVisitor visitor) {
		super(visitor);
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
