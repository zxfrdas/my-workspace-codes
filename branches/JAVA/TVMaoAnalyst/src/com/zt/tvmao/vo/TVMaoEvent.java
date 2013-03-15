package com.zt.tvmao.vo;

/**
 * 电视猫节目数据类
 * @author zhaotong
 */
public class TVMaoEvent extends TVMaoObject {
	private static final long serialVersionUID = 3233611126228570691L;
	
	private String name;
	private String imgSrc;
	private String url;
	private String date;
	private String time;
	private String desc;
	private String channel;
	private boolean hasName;
	private boolean hasImgSrc;
	private boolean hasUrl;
	private boolean hasDate;
	private boolean hasTime;
	private boolean hasDesc;
	private boolean hasChannel;
	
	public TVMaoEvent() {
		name = "";
		imgSrc = "";
		url = "";
		date = "";
		time = "";
		desc = "";
		channel = "";
		hasName = false;
		hasImgSrc = false;
		hasUrl = false;
		hasDate = false;
		hasTime = false;
		hasDesc = false;
		hasChannel = false;
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
		if (!hasImgSrc && isStringNull(imgSrc)) {
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
		if (!hasUrl && isStringNull(url)) {
			this.url = url;
			hasUrl = true;
		}
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		if (!hasDate && !isStringNull(date)) {
			this.date = date;
			hasDate = true;
		}
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		if (!hasTime && !isStringNull(time)) {
			this.time = time;
			hasTime = true;
		}
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		if (!hasDesc && !isStringNull(desc)) {
			this.desc = desc;
			hasDesc = true;
		}
	}

	public String getChannel()
	{
		return channel;
	}

	public void setChannel(String channel)
	{
		if (!hasChannel && !isStringNull(channel)) {
			this.channel = channel;
			hasChannel = true;
		}
	}

	@Override
	public boolean isEmpty()
	{
		return !hasDate && !hasDesc && !hasImgSrc && !hasName && !hasTime && !hasUrl
				&& !hasChannel;
	}

}
