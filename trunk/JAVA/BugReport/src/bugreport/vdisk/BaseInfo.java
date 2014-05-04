package bugreport.vdisk;


import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

public class BaseInfo {
	
	public static class Data {
		public String token = "";
		public String id = "";
		public String dir_id = "";
		public int time = 0;
		public String isActive = "";
		public String appKey = "";

		@JSONCreator
		public Data(@JSONField(name = "token")String token, @JSONField(name = "id")String id, 
				@JSONField(name = "dir_id")String dir_id, @JSONField(name = "time")int time,
				@JSONField(name = "is_active")String isActive, @JSONField(name = "appkey")String appkey)
		{
			this.token = token;
			this.id = id;
			this.dir_id = dir_id;
			this.time = time;
			this.isActive = isActive;
			this.appKey = appkey;
		}

		@Override
		public String toString()
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("[token = " + token + "\n");
			buffer.append("id = " + id + "\n");
			buffer.append("dir_id = " + dir_id + "\n");
			buffer.append("time = " + time + "\n");
			buffer.append("isActive = " + isActive + "\n");
			buffer.append("appKey = " + appKey + "]\n");
			return buffer.toString();
		}

	}
	
	public int errCode;
	public String errMsg;
	public Data data;
	public int dologId;
	public int[] dologDir;
	
	public BaseInfo() {
		
	}

	@JSONCreator
	public BaseInfo(@JSONField(name = "err_code")int errCode, @JSONField(name = "err_msg")String errMsg,
			@JSONField(name = "data")Data data, @JSONField(name = "dologid")int dologId, 
			@JSONField(name = "dologdir")int[] dologDir)
	{
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.data = data;
		this.dologId = dologId;
		this.dologDir = dologDir;
	}

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("err_code = " + errCode + "\n");
		buffer.append("err_msg = " + errMsg + "\n");
		buffer.append("data" + data + "\n");
		buffer.append("dologid = " + dologId + "\n");
		for (int i = 0; i < dologDir.length; i ++)
		{
			buffer.append("dologdir = " + dologDir[i] + "\n");
		}
		return buffer.toString();
	}

}
