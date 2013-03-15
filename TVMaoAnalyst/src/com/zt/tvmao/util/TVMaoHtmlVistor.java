package com.zt.tvmao.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import com.zt.lib.io.StreamHelper;

/**
 * 提供使用HttpGet方法访问电视猫网站的方法
 * @author zhaotong
 */
public class TVMaoHtmlVistor {
	private static final int TIME_OUT = 5 * 1000;
	private HttpClient httpClient;
	private String url;

	/**
	 * 构造器
	 * <p>初始化一个默认超时时间为5秒的{@link HttpClient}
	 */
	public TVMaoHtmlVistor()
	{
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, TIME_OUT);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, TIME_OUT);
		url = "";
	}
	
	/**
	 * 获取指定网络地址的HTML页面内容
	 * @param url 网址
	 * @return HTML页面内容
	 */
	public String getHtml(String url)
	{
		StringBuffer sb = new StringBuffer();
		System.out.println(url);
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				InputStream inputStream = response.getEntity().getContent();
				sb.append(StreamHelper.toString(inputStream));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return sb.toString();
	}
	
	/**
	 * 返回电视猫中查询指定关键字所得的页面内容
	 * @param keyWord 关键字
	 * @return HTML页面内容
	 */
	public String query(String keyWord)
	{
		return getHtml(TVMaoUrlBuilder.query(keyWord));
	}
	
	public boolean isSameUrl(String url)
	{
		return this.url.equals(url);
	}
	
}
