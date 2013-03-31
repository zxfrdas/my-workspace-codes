package com.zt.taobao;

import com.zt.lib.annotations.TargetName;

public class Item {
	@TargetName("类别")
	public String type;
	@TargetName("单价")
	public String price;
	@TargetName("购买数量")
	public String number;
	@TargetName("颜色方案")
	public String color;
	
	public Item() {
		type = "";
		price = "";
		number = "";
		color = "";
	}

	@Override
	public String toString()
	{
		String sep = System.getProperty("line.separator");
		return new StringBuilder().append("长度 = ").append(type).append(sep)
				.append("单价 = ").append(price).append(sep).append("购买数量 = ")
				.append(number).append(sep).append("颜色 = ").append(color).toString();
	}
	
}
