package com.zt.tvmao.vo;


/**
 * 电视猫频道数据类
 * @author zhaotong
 */
public class TVMaoChannel extends TVMaoObject {
	private static final long serialVersionUID = -3077377842419681436L;
	
	private String name;
	private String imgSrc;
	private String url;
	private boolean hasName;
	private boolean hasImgSrc;
	private boolean hasUrl;
	
	public TVMaoChannel() {
		name = "";
		imgSrc = "";
		url = "";
		hasName = false;
		hasImgSrc = false;
		hasUrl = false;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		if (!hasName && !isStringNull(name)) {
			this.name = name;
			hasName = true;
		}
	}

	public String getImgSrc()
	{
		return imgSrc;
	}

	public void setImgSrc(String imgSrc)
	{
		if (!hasImgSrc && !isStringNull(imgSrc)) {
			this.imgSrc = imgSrc;
			hasImgSrc = true;
		}
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		if (!hasUrl && !isStringNull(url)) {
			this.url = url;
			hasUrl = true;
		}
	}

	@Override
	public boolean isEmpty()
	{
		return !hasName && !hasImgSrc && !hasUrl;
	}
	
}
