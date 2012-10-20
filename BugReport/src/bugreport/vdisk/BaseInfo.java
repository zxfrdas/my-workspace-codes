package bugreport.vdisk;


import com.google.gson.annotations.SerializedName;

public class BaseInfo
{
	@SerializedName(value = "err_code")
	public int errCode;
	@SerializedName(value = "err_msg")
	public String errMsg;
	@SerializedName(value = "data")
	public Data data;
	@SerializedName(value = "dologid")
	public int dologId;
	@SerializedName(value = "dologdir")
	public int[] dologDir;

	public BaseInfo()
	{
		errCode = 0;
		errMsg = "";
		data = new Data();
		dologId = 0;
		dologDir = new int[1];
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

	class Data
	{
		@SerializedName(value = "token")
		public String token = "";
		
		@SerializedName(value = "id")
		public String id = "";
		
		@SerializedName(value = "dir_id")
		public String dir_id = "";
		
		@SerializedName(value = "time")
		public int time = 0;
		
		@SerializedName(value = "is_active")
		public String isActive = "";
		
		@SerializedName(value = "appkey")
		public String appKey = "";

		public Data()
		{
			token = "";
			id = "";
			dir_id = "";
			time = 0;
			isActive = "";
			appKey = "";
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

}
