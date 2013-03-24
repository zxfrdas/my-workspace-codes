package com.zt.tvmao.core.travers;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.NodeTraversor;

import com.zt.tvmao.core.visitors.IVisitorCallback;
import com.zt.tvmao.core.visitors.TVMaoNodeVisitor;
import com.zt.tvmao.vo.TVMaoObject;

/**
 * 电视猫HTML节点解析基类。
 * @author zhaotong
 */
public abstract class TVMaoNodeTraversor extends NodeTraversor implements
		IVisitorCallback {
	protected final boolean debug = true;
	protected List<TVMaoObject> mDatas;
	protected Document mDocument;
	
	public TVMaoNodeTraversor(TVMaoNodeVisitor visitor) {
		super(visitor);
		visitor.setParserListener(this);
		mDatas = new ArrayList<TVMaoObject>();
	}
	
	public void setDocument(Document document)
	{
		mDocument = document;
	}
	
	/**
	 * 开始解析HTML文档
	 * @return 解析完毕后生成的电视猫数据列表
	 */
	public abstract List<TVMaoObject> traverse();

	@Override
	public void onEnd(TVMaoObject data)
	{
		if (!data.isEmpty()) {
			mDatas.add(data);
		}
	}

}
