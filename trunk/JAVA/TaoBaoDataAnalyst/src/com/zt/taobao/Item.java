package com.zt.taobao;

public class Item {
	public Float length;
	public Float price;
	public Integer number;
	public String color;
	
	public Item() {
		length = 0f;
		price = 0f;
		number = 0;
		color = "";
	}

	@Override
	public String toString()
	{
		String sep = System.getProperty("line.separator");
		return new StringBuilder().append("长度 = ").append(length).append(sep)
				.append("单价 = ").append(price).append(sep).append("购买数量 = ")
				.append(number).append(sep).append("颜色 = ").append(color).toString();
	}
	
}
