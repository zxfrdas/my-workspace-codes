package com.zt.tvmao.core.travers;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zt.tvmao.core.visitors.TVMaoNodeVisitor;
import com.zt.tvmao.vo.TVMaoEvent;
import com.zt.tvmao.vo.TVMaoObject;

/**
 * 电视猫节目HTML解析类
 * @author zhaotong
 */
public class EventTraversor extends TVMaoNodeTraversor {
	private static final String CLASS = "class";
	private static final String NAME = "epgcontent mt10";
	
	/**
	 * 传入HTML文档，从中解析电视猫电视节目数据类
	 * @param document 电视猫网站HTML文档
	 * @param visitor HTML解析规则
	 */
	public EventTraversor(Document document, TVMaoNodeVisitor visitor) {
		super(document, visitor);
	}

	@Override
	public TVMaoObject onStart()
	{
		return new TVMaoEvent();
	}
	
	@Override
	public List<TVMaoObject> traverse()
	{
		Element element = mDocument.getElementsByAttributeValue(CLASS, NAME).get(0);
		if (debug) {
			System.out.println(element.text());
		}
		traverse(element);
		return mDatas;
	}

}
