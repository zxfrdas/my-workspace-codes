package com.zt.taobao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zt.taobao.SimpleFactory.Type;
import com.zt.taobao.output.IOutput;
import com.zt.taobao.parser.ITaoBaoParser;
import com.zt.taobao.util.HttpVisitor;

public class Main {
	private static final int DEEPTH = 12;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Type mType = Type.BATTARY;
		ITaoBaoParser parser = SimpleFactory.getParser(mType);
		HttpVisitor visitor = new HttpVisitor();
		String nextPage = null;
		IOutput output = SimpleFactory.getOutput(mType);
		String resultPage = visitor.getHtml(SimpleFactory.getParams(mType));
		for (int i = 0; i < DEEPTH; i ++) {
			List<Item> items = new ArrayList<Item>();
			String firstUrl = parser.getRecordUrlFromShop(visitor.getHtml(parser
					.getShopUrlFromResult(resultPage, i)));
			System.out.println(firstUrl);
			String content = null;
			while (null != (nextPage = parser.getNextRecordPageUrl(content, firstUrl))) {
				content = visitor.getHtml(nextPage);
				items.addAll(parser.parserRecordPage(content));
			}
			output.output(items);
		}
		File total = new File("D:\\手机充电宝.txt");
		try {
			FileWriter fileWriter = new FileWriter(total);
			fileWriter.write(parser.getOutput());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
