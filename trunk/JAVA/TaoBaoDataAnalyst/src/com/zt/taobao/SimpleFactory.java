package com.zt.taobao;

import com.zt.taobao.output.ExcelOutput;
import com.zt.taobao.output.IOutput;
import com.zt.taobao.parser.BattaryParser;
import com.zt.taobao.parser.HDMIParser;
import com.zt.taobao.parser.ITaoBaoParser;
import com.zt.taobao.util.Params;

public class SimpleFactory {

	public enum Type {
		HDMI,
		BATTARY,
	}
	
	public static IOutput getOutput(Type type)
	{
		switch (type)
		{
		case BATTARY:
			return new ExcelOutput("D:\\手机充电宝.xlsx");
			
		case HDMI:
			return new ExcelOutput("D:\\HDMI连接线.xlsx");

		default:
			break;
		}
		return null;
	}
	
	public static ITaoBaoParser getParser(Type type)
	{
		switch (type)
		{
		case BATTARY:
			return new BattaryParser();
			
		case HDMI:
			return new HDMIParser();
			
		default:
			break;
		}
		return null;
	}
	
	public static String getParams(Type type)
	{
		switch (type)
		{
		case BATTARY:
			return Params.BATTARY;
			
		case HDMI:
			return Params.HDMI;

		default:
			break;
		}
		return null;
	}
	
}
