package com.zt.tvmao.core.visitors;

import org.jsoup.nodes.Node;

import com.zt.tvmao.vo.TVMaoChannel;

/**
 * 电视猫搜索结果中频道信息的解析规则类
 * @author zhaotong
 */
public class ChannelVisitor extends TVMaoNodeVisitor {
	private boolean inTVTable = false;
	private boolean inTVNodes = false;
	private boolean inTVNode = false;

	@Override
	public void head(Node node, int depth)
	{
		if (!inTVTable && "table".equals(node.nodeName())) {
			inTVTable = true;
		}
		if (inTVTable && "tr".equals(node.nodeName())) {
			inTVNodes = true;
		}
		if (inTVNodes && "td".equals(node.nodeName())) {
			inTVNode = true;
			if (!isListenerNull()) {
				mData = mListenerRef.get().onStart();
			}
		}
		if (inTVNode && !isDataNull()) {
			if ("a".equals(node.nodeName())) {
				((TVMaoChannel) mData).setUrl(node.absUrl("href"));
			} else if ("img".equals(node.nodeName())) {
				((TVMaoChannel) mData).setImgSrc(node.attr("src"));
				((TVMaoChannel) mData).setName(node.attr("alt"));
			}
			
		}
	}

	@Override
	public void tail(Node node, int depth)
	{
		if (inTVNodes && "td".equals(node.nodeName())) {
			inTVNode = false;
			if (!isDataNull() && !isListenerNull()) {
				mListenerRef.get().onEnd(mData);
			}
		}
		if (inTVTable && "tr".equals(node.nodeName())) {
			inTVNodes = false;
		}
		if (inTVTable && "table".equals(node.nodeName())) {
			inTVTable = false;
		}
	}

}
