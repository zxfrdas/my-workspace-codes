package bugreport.vdisk;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.text.format.Time;
import bugreport.HMACSHA1;

import com.alibaba.fastjson.JSON;

public class VDisk
{
	private static final String URL = "http://openapi.vdisk.me/?";
	private static final String ACCOUNT = "zhaotong315@gmail.com";
	private static final String PASSWORD = "123456";
	private static final String APPKEY = "2279435590";
	private static final String APPSECRET = "8a85f706dbfb01ff08f0e53193f6b535";
	private static VDisk instance = null;
	private String TOKEN = "";
	private int iDologId = 0;
	private HttpClient client = null;
	private HttpPost post = null;
	public BaseInfo mBaseInfo = null;
	
	public static VDisk getInstance()
	{
		if (null == instance)
		{
			instance = new VDisk();
		}
		return instance;
	}
	
	private VDisk()
	{
		client = new DefaultHttpClient();
		post = new HttpPost(URL);
		mBaseInfo = new BaseInfo();
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
	}
	
	public BaseInfo getBaseInfo()
	{
		return mBaseInfo;
	}
	
	/**
	 * 初次启动，获取Token。
	 * @return true:成功,false:失败
	 */
	public boolean getToken()
	{
    	try
		{
    		post.setEntity(new UrlEncodedFormEntity(getTokenParams()));
			HttpResponse httpResponse = client.execute(post);
			InputStream iss = httpResponse.getEntity().getContent();
			mBaseInfo = JSON.parseObject(readStream(iss), BaseInfo.class);
			TOKEN = mBaseInfo.data.token;
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    	if (0 == mBaseInfo.errCode)
    	{
    		System.out.println(mBaseInfo.toString());
    		return true;
    	}
    	else
    	{
    		System.out.println("errMsg = " + mBaseInfo.errMsg);
    		return false;
    	}
	}
	
	/**
	 * 每隔10-15分钟运行一次，以保持Token的有效。
	 * @return true:成功,false:失败
	 */
	public boolean keepToken()
	{
		try
		{
			post.setEntity(new UrlEncodedFormEntity(keepTokenParams()));
			HttpResponse httpResponse = client.execute(post);
			mBaseInfo = JSON.parseObject(readStream(httpResponse.getEntity().getContent()), BaseInfo.class);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (0 == mBaseInfo.errCode)
		{
			iDologId = mBaseInfo.dologId;
			System.out.println(mBaseInfo.toString());
			return true;
		}
		else
		{
			System.out.println("errMsg = " + mBaseInfo.errMsg);
			return false;
		}
	}
	
	/**
	 * 获取该目录ID
	 * @param dir,目录路径
	 * @return dirId
	 */
	public String getDirId(String dir)
	{
		try
		{
			post.setEntity(new UrlEncodedFormEntity(getDirIdParams(dir)));
			HttpResponse httpResponse = client.execute(post);
			mBaseInfo = JSON.parseObject(readStream(httpResponse.getEntity().getContent()), BaseInfo.class);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (0 == mBaseInfo.errCode)
		{
			iDologId = mBaseInfo.dologId;
			System.out.println(mBaseInfo.toString());
			return mBaseInfo.data.id;
		}
		else
		{
			System.out.println("errMsg = " + mBaseInfo.errMsg);
			return null;
		}
	}
	
	/**
	 * 在根目录下创建目录
	 * @param dir 目录名
	 * @return 目录id
	 */
	public String createDir(String dir)
	{
		try
		{
			post.setEntity(new UrlEncodedFormEntity(createDirParams(dir)));
			HttpResponse httpResponse = client.execute(post);
			mBaseInfo = JSON.parseObject(readStream(httpResponse.getEntity().getContent()), BaseInfo.class);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (0 == mBaseInfo.errCode)
		{
			iDologId = mBaseInfo.dologId;
			System.out.println(mBaseInfo.toString());
			return mBaseInfo.data.dir_id;
		}
		else
		{
			System.out.println("errMsg = " + mBaseInfo.errMsg);
			return null;
		}
	}
	
	/**
	 * 上传错误日志到指定目录
	 * @param file 错误日志
	 * @return true:成功 false:失败
	 */
	public boolean uploadErrLog(File file, String dirId)
	{
		try {
			post.setEntity(uploadEntity(file, dirId));
			HttpResponse httpResponse = client.execute(post);
			mBaseInfo = JSON.parseObject(readStream(httpResponse.getEntity().getContent()), BaseInfo.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		if (0 == mBaseInfo.errCode)
		{
			iDologId = mBaseInfo.dologId;
			System.out.println(mBaseInfo.toString());
			return true;
		}
		else
		{
			System.out.println("errMsg = " + mBaseInfo.errMsg);
			return false;
		}
	}
	
    /**
     * 构造访问keep token api所需的http参数
     * @return List<{@link NameValuePair}>
     */
    private List<NameValuePair> keepTokenParams()
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("m", "user"));
    	params.add(new BasicNameValuePair("a", "keep_token"));
    	params.add(new BasicNameValuePair("token", TOKEN));
    	params.add(new BasicNameValuePair("dologid", iDologId + ""));
    	return params;
    }
    
    /**
     * 构造访问get token api所需的http参数
     * @return List<{@link NameValuePair}>
     */
    private List<NameValuePair> getTokenParams()
    {
		try
		{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("m", "auth"));
			params.add(new BasicNameValuePair("a", "get_token"));
			params.add(new BasicNameValuePair("account", ACCOUNT));
			params.add(new BasicNameValuePair("password", PASSWORD));
			params.add(new BasicNameValuePair("appkey", APPKEY));
			Time time = new Time();
			String timeNow = time.toMillis(false) / 1000 + "";
			System.out.println(timeNow);
			params.add(new BasicNameValuePair("time", timeNow));
        	params.add(new BasicNameValuePair("signature", HMACSHA1.getSignature("account=" + ACCOUNT 
        			+ "&appkey=" + APPKEY + "&password=" + PASSWORD +"&time=" + timeNow, APPSECRET)));
        	params.add(new BasicNameValuePair("app_type", "local"));
        	return params;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 构造Multipart/form-data方式的Http体
     * @return MultipartEntity
     */
    private MultipartEntity uploadEntity(File file, String dirId)
    {
		try
		{
			ContentBody mBody = new StringBody("file");
			ContentBody aBody = new StringBody("upload_share_file");
			ContentBody tokenBody = new StringBody(TOKEN);
			if (null == dirId)
			{
				dirId = "0";
			}
			ContentBody dirBody = new StringBody(dirId);
			ContentBody coverBody = new StringBody("yes");
			ContentBody fileBody = new FileBody(file);
			ContentBody idBody = new StringBody(iDologId + "");
			MultipartEntity mpEntity  = new MultipartEntity();
			mpEntity = new MultipartEntity();
			mpEntity.addPart("m", mBody);
			mpEntity.addPart("a", aBody);
			mpEntity.addPart("token", tokenBody);
			mpEntity.addPart("dir_id", dirBody);
			mpEntity.addPart("cover", coverBody);
			mpEntity.addPart("file", fileBody);
			mpEntity.addPart("dologid", idBody);
			return mpEntity;
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 构造访问get dirid api所需的http参数
     * @param dir 目录名
     * @return List<{@link NameValuePair}>
     */
    private List<NameValuePair> getDirIdParams(String dir)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("m", "dir"));
    	params.add(new BasicNameValuePair("a", "get_dirid_with_path"));
    	params.add(new BasicNameValuePair("token", TOKEN));
    	params.add(new BasicNameValuePair("path", "/" + dir));
    	params.add(new BasicNameValuePair("dologid", mBaseInfo.dologId + ""));
    	return params;
    }
    
    /**
     * 构造访问create dir api所需的http参数
     * @param dir 目录名
     * @return List<{@link NameValuePair}>
     */
    private List<NameValuePair> createDirParams(String dir)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("m", "dir"));
    	params.add(new BasicNameValuePair("a", "create_dir"));
    	params.add(new BasicNameValuePair("token", TOKEN));
    	params.add(new BasicNameValuePair("create_name", dir));
    	params.add(new BasicNameValuePair("parent_id", "0"));
    	params.add(new BasicNameValuePair("dologid", mBaseInfo.dologId + ""));
    	return params;
    }
    
    /**
     * Read http requests result from inputstream .
     * 
     * @param inputstream
     *            : http inputstream from HttpConnection
     * 
     * @return String : http response content
     */
    private static String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }
	
}
