package com.zt.tvmao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zt.tvmao.core.travers.ChannelTraversor;
import com.zt.tvmao.core.travers.EventTraversor;
import com.zt.tvmao.core.travers.TVMaoNodeTraversor;
import com.zt.tvmao.core.visitors.ChannelVisitor;
import com.zt.tvmao.core.visitors.EventVisitor;
import com.zt.tvmao.util.TVMaoHtmlVistor;

public class Main {
	static boolean inTVTable = false;
	static boolean inTVNodes = false;
	static boolean inTVNode = false;
	

	public static void main(String[] args)
	{
		TVMaoHtmlVistor vistor = new TVMaoHtmlVistor();
		String content = vistor.query("深圳");
		Document doc = Jsoup.parseBodyFragment(content, "http://www.tvmao.com");
		TVMaoNodeTraversor traversor = new ChannelTraversor(doc, new ChannelVisitor());
		traversor.traverse();
		traversor = new EventTraversor(doc, new EventVisitor());
		traversor.traverse();
	}
	
}
